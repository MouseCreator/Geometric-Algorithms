package mouse.project.ui.components.point;

import mouse.project.saver.Savable;
import mouse.project.saver.SavableHolder;
import mouse.project.state.ConstUtils;
import mouse.project.ui.components.draw.DrawManager;
import mouse.project.utils.math.Position;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class PointSet implements SavableHolder {
    private final List<TPoint> TPoints;
    private final DrawManager drawManager;

    private final PointIdGenerator idGenerator;
    public PointSet(DrawManager drawManager) {
        this.drawManager = drawManager;
        TPoints = new ArrayList<>();
        idGenerator = new PointIdGeneratorImpl();
    }
    public List<TPoint> getPoints() {
        return new ArrayList<>(TPoints);
    }
    public void clear() {
        TPoints.forEach(drawManager::onRemove);
        TPoints.clear();
    }
    public void add(TPoint tPoint) {
        drawManager.onAdd(tPoint);
        if (tPoint.hasId()) {
            idGenerator.put(tPoint.key());
        } else {
            String id = idGenerator.generateAndPut();
            tPoint.setId(id);
        }
        this.TPoints.add(tPoint);
    }

    @Override
    public void refresh() {
        TPoints.forEach(tPoint -> idGenerator.free(tPoint.getId()));
        TPoints.clear();
    }

    @Override
    public Collection<Savable> getSavables() {
        return new ArrayList<>(TPoints);
    }

    @Override
    public Map<String, Supplier<Savable>> getKeyMap() {
        HashMap<String, Supplier<Savable>> map = new HashMap<>();
        map.put(TPoint.KEY, ()->new TPoint(Position.of(0,0)));
        return map;
    }

    @Override
    public void addAll(Collection<Savable> savables) {
        for (Savable savable : savables) {
            addSavable(savable);
        }
    }

    @Override
    public void addSavable(Savable savable) {
        if (savable instanceof TPoint) {
            add((TPoint) savable);
        }
    }

    public void removePointAt(Position position) {
        List<TPoint> list = TPoints.stream().filter(getPointByPosition(position)).toList();
        list.forEach(drawManager::onRemove);
        list.forEach(t -> idGenerator.free(t.getId()));
        TPoints.removeAll(list);
    }

    private static Predicate<TPoint> getPointByPosition(Position position) {
        int nodeRadius = ConstUtils.NODE_DIAMETER >>> 1;
        return TPoint -> position.distanceTo(TPoint.position()) < nodeRadius;
    }

    public Optional<TPoint> getPointAt(Position position) {
        return TPoints.stream().filter(getPointByPosition(position)).findFirst();
    }

}
