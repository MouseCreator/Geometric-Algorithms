package mouse.project.saver;

import mouse.project.state.ConstUtils;
import mouse.project.ui.components.point.Point;
import mouse.project.ui.components.point.PointSet;
import mouse.project.utils.math.Position;

import java.util.*;

public class GraphSaver {
    public static final String POINT = "point";
    public String toSaveString(PointSet pointSet) {
        StringBuilder builder = new StringBuilder();
        List<Point> points = pointSet.getPoints();
        points.forEach(n -> {
            Optional<String> s = savePoint(n);
            s.ifPresent(builder::append);
            builder.append("\n");
        });
        return builder.toString();
    }

    private record Tokens(List<String> values) {
            private Tokens(List<String> values) {
                this.values = new ArrayList<>(values);
            }

            public String next() {
                if (isEmpty()) {
                    throw new LoadingException("Cannot get next token.");
                }
                return values.remove(0);
            }

            public Integer nextInteger() {
                String next = next();
                try {
                    return Integer.parseInt(next);
                } catch (Exception e) {
                    throw new LoadingException("Cannot parse to integer " + next);
                }
            }

            public void expect(String pattern) {
                String[] strs = pattern.split(" ");
                if (values.size() < strs.length) {
                    throw new LoadingException("Cannot expect pattern " + pattern);
                }
                int i = 0;
                for (String s : strs) {
                    String key = values.get(i++);
                    switch (s.toUpperCase()) {
                        case "S":
                            continue;
                        case "X":
                            isPosition(key, "x");
                            break;
                        case "Y":
                            isPosition(key, "y");
                            break;
                    }
                }
            }

            private void isPosition(String s, String cord) {
                boolean matches = s.matches("^-?\\d+$");
                if (!matches) {
                    throw new LoadingException("Expected integer, found: " + s);
                }
                int i = Integer.parseInt(s);
                if (i < 0) {
                    throw new LoadingException("Cannot accept negative coordinate: " + i);
                }
                if (cord.equals("x") && i > ConstUtils.WORLD_WIDTH) {
                    throw new LoadingException("X out of bounds: " + i + " > " + ConstUtils.WORLD_WIDTH);
                } else if (cord.equals("y") && i > ConstUtils.WORLD_HEIGHT) {
                    throw new LoadingException("Y out of bounds: " + i + " > " + ConstUtils.WORLD_HEIGHT);
                }
            }

            public boolean isEmpty() {
                return values.isEmpty();
            }

            public boolean hasValues() {
                return !values.isEmpty();
            }
        }
    public void fromSaveString(PointSet set, String input) {
        set.clear();

        Tokens tokenStream = tokenize(input);

        while (tokenStream.hasValues()) {
            String next = tokenStream.next();
            if (next.equals(POINT)) {
                tokenStream.expect("X Y");
                int x = tokenStream.nextInteger();
                int y = tokenStream.nextInteger();
                set.add(new Point(Position.of(x, y)));
            } else {
                throw new LoadingException("Unexpected token: " + next);
            }
        }
    }

    private static Point getPoint(Map<String, Point> pointMap, String key1) {
        Point node1 = pointMap.get(key1);
        if (node1 == null) {
            throw new LoadingException("Unexpected node key: " + key1);
        }
        return node1;
    }

    private Optional<String> savePoint(Point point) {
        Position position = point.getPosition();
        String result = String.format("%s %d %d", POINT, position.x(), position.y());
        return Optional.of(result);
    }

    private Tokens tokenize(String input) {
        String[] strings = removeWhitespaces(input);
        List<String> list = Arrays.asList(strings);
        List<String> noComments = removeComments(list);
        return new Tokens(noComments);
    }

    private List<String> removeComments(List<String> list) {
        List<String> result = new ArrayList<>();
        boolean commentStart = false;
        for (String s : list) {
            if (s.contains("/*")) {
                commentStart = true;
            }
            if (!commentStart) {
                result.add(s);
            }
            if (s.contains("*/")) {
                commentStart = false;
            }
        }
        return result;
    }

    private static String[] removeWhitespaces(String input) {
        String normalizedSpaces = input.replaceAll("\\s", " ");
        String singleSpaced = normalizedSpaces.replaceAll(" +", " ");
        return singleSpaced.split(" ");
    }

}
