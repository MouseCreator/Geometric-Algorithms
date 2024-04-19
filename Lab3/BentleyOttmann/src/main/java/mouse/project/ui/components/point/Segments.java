package mouse.project.ui.components.point;

import mouse.project.saver.Savable;
import mouse.project.saver.SavableHolder;
import mouse.project.ui.components.draw.DrawManager;
import mouse.project.utils.math.Position;

import java.util.*;
import java.util.function.Supplier;

public class Segments implements SavableHolder {

    private final List<Segment> segmentList;
    private final DrawManager drawManager;
    public Segments(DrawManager drawManager) {
        segmentList = new ArrayList<>();
        this.drawManager = drawManager;
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
            segmentList.add((Segment) savable);
        }
    }

    public void deleteSegmentsAt(Position position) {

    }

    public void clear() {
        segmentList.forEach(c->drawManager.onRemove(c));
        segmentList.clear();
    }
}
