package mouse.project.saver;

import java.util.*;
import java.util.function.Supplier;

public class GenericSaver {
    public String toSaveString(SavableHolder savableHolder) {
        StringBuilder builder = new StringBuilder();
        Collection<Savable> savables = savableHolder.getSavables();
        savables.forEach(n -> {
            Optional<String> s = saveInstance(n);
            s.ifPresent(builder::append);
            builder.append("\n");
        });
        return builder.toString();
    }

    private Optional<String> saveInstance(Savable savable) {
        if (savable.dontSaveMe()) {
            return Optional.empty();
        }
        String key = savable.key();
        StringBuilder builder = new StringBuilder(key);
        for (Object s : savable.supply()) {
            builder.append(" ").append(s);
        }
        return Optional.of(builder.toString());
    }

    private record RecordValue(String header, List<String> values) {
    }

    private record Lines(List<RecordValue> lines) {
        private Lines(List<RecordValue> lines) {
            this.lines = new ArrayList<>(lines);
        }
        public RecordValue next() {
            if (isEmpty()) {
                throw new LoadingException("Cannot get next line!");
            }
            return lines.remove(0);
        }
        public boolean isEmpty() {
            return lines.isEmpty();
        }
        public boolean hasNext() {
            return !lines.isEmpty();
        }
    }
    public void fromSaveString(SavableHolder set, String input) {
        set.refresh();

        Lines lineStream = toLines(input);
        Map<String, Supplier<Savable>> keyMap = set.getKeyMap();
        while (lineStream.hasNext()) {
            RecordValue next = lineStream.next();
            Supplier<Savable> savableSupplier = keyMap.get(next.header());
            if (savableSupplier == null) {
                throw new LoadingException("Unexpected token: " + next);
            }
            Savable savableInstance = savableSupplier.get();
            List<String> list = new ArrayList<>(next.values());
            savableInstance.consume(list);
            set.addSavable(savableInstance);
        }
    }


    private Lines toLines(String input) {
        String[] strings = getLines(input);
        List<String> list = new ArrayList<>(Arrays.asList(strings));
        removeEmpty(list);
        List<String> noComments = removeComments(list);
        List<RecordValue> recordValues = toRecords(noComments);
        return new Lines(recordValues);
    }

    private List<RecordValue> toRecords(List<String> lines) {
        List<RecordValue> recordValues = new ArrayList<>();
        for (String line : lines) {
            recordValues.add(toRecord(line));
        }
        return recordValues;
    }

    private RecordValue toRecord(String line) {
        line = removeWhitespaces(line);
        String[] rv = line.split(" ", 2);
        String r = rv[0];
        if (rv.length==1) {
            return new RecordValue(r, new ArrayList<>());
        }
        String v = rv[1];
        String[] params = v.split(" ");
        return new RecordValue(r, Arrays.asList(params));
    }

    private void removeEmpty(List<String> list) {
        list.removeIf(s -> s.trim().isEmpty());
    }

    private List<String> removeComments(List<String> list) {
        List<String> result = new ArrayList<>();
        for (String s : list) {
            if (s.startsWith("#")) {
                continue;
            }
            int index = s.indexOf("#");
            if (index < 0) {
                result.add(s);
                continue;
            }
            int prev = index - 1;
            char c = s.charAt(prev);
            if (c=='\\') {
                String subs = s.substring(0, prev);
                result.add(subs);
            }
        }
        return result;
    }

    private static String[] getLines(String input) {
        return input.split("\n");
    }
    private static String removeWhitespaces(String line) {
        String normalizedSpaces = line.replaceAll("\\s", " ");
        return normalizedSpaces.replaceAll(" +", " ");
    }

}
