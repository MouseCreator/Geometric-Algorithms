package mouse.project.ui.components.point;

import lombok.Getter;
import mouse.project.saver.Savable;
import mouse.project.saver.SavableHolder;
import mouse.project.ui.components.draw.DrawManager;
import mouse.project.math.Position;

import java.util.*;
import java.util.function.Supplier;

public class Segments implements SavableHolder {

    private final List<Segment> segmentList;
    private final DrawManager drawManager;
    @Getter
    private final IdGenerator idGenerator;
    public Segments(DrawManager drawManager) {
        segmentList = new ArrayList<>();
        this.drawManager = drawManager;
        idGenerator = new IdGeneratorImpl();
    }

    @Override
    public void refresh() {
        clear();
    }

    @Override
    public Collection<Savable> getSavables() {
        return new ArrayList<>(segmentList);
    }

    @Override
    public Map<String, Supplier<Savable>> getKeyMap() {
        Map<String, Supplier<Savable>> map = new HashMap<>();
        for (Segment segment : segmentList) {
            map.put(segment.key(), () -> segment);
        }
        return map;
    }

    @Override
    public void addAll(Collection<Savable> savables) {
        for (Savable s : savables) {
            addSavable(s);
        }
    }

    @Override
    public void addSavable(Savable savable) {
        if (savable instanceof Segment) {
            onAdd((Segment) savable, true);
        }
    }

    public void deleteSegmentsAt(Position position) {
        List<Segment> toBeRemoved = new ArrayList<>();
        for (Segment segment : segmentList) {
            if (segment.goesThrough(position)) {
                toBeRemoved.add(segment);
            }
        }
        toBeRemoved.forEach(drawManager::onRemove);
        segmentList.removeAll(toBeRemoved);
        toBeRemoved.forEach(s -> idGenerator.free(s.getId()));
    }

    public void clear() {
        segmentList.forEach(drawManager::onRemove);
        segmentList.clear();
    }

    public void addSegment(Segment segment, boolean addToGraphics) {
        onAdd(segment, addToGraphics);
    }

    private void onAdd(Segment segment, boolean addToGraphics) {
        segmentList.add(segment);
        if (segment.getId()==null) {
            segment.setId(idGenerator.generateAndPut());
        } else {
            idGenerator.put(segment.getId());
        }
        if (addToGraphics) {
            drawManager.onAdd(segment);
        }
    }

    public Optional<SegmentEndRecord> getEndAt(Position position) {
        for (Segment segment : segmentList) {
            Optional<SegmentEnd> endAt = segment.getEndAt(position);
            if (endAt.isPresent()) {
                return Optional.of(new SegmentEndRecord(segment, endAt.get()));
            }
        }
        return Optional.empty();
    }

    public List<Segment> getAll() {
        return new ArrayList<>(segmentList);
    }
}
