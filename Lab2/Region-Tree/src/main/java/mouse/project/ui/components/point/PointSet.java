package mouse.project.ui.components.point;

import mouse.project.saver.Savable;
import mouse.project.saver.SavableHolder;
import mouse.project.utils.math.Position;

import java.util.*;
import java.util.function.Supplier;

public class PointSet implements SavableHolder {
    private final List<Point> points;
    public PointSet() {
        points = new ArrayList<>();
    }
    public List<Point> getPoints() {
        return new ArrayList<>(points);
    }
    public void clear() {
        points.clear();
    }
    public void add(Point point) {
        this.points.add(point);
    }

    @Override
    public void refresh() {
        points.clear();
    }

    @Override
    public Collection<Savable> getSavables() {
        return new ArrayList<>(points);
    }

    @Override
    public Map<String, Supplier<Savable>> getKeyMap() {
        HashMap<String, Supplier<Savable>> map = new HashMap<>();
        map.put(Point.KEY, ()->new Point(Position.of(0,0)));
        return map;
    }

    @Override
    public void addAll(Collection<Savable> savables) {
        for (Savable savable : savables) {
            add(savable);
        }
    }

    @Override
    public void add(Savable savable) {
        if (savable instanceof Point) {
            points.add((Point) savable);
        }
    }
}
