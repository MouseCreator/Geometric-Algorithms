package mouse.project.ui.components.draw;

import mouse.project.ui.components.main.Drawable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DrawManagerImpl implements DrawManager {

    private final List<Drawable> list;

    private final ReadWriteLock rwLock;

    public DrawManagerImpl() {
        list = new ArrayList<>();
        rwLock = new ReentrantReadWriteLock();
    }

    @Override
    public void onAdd(Drawable drawable) {
        try {
            rwLock.writeLock().lock();
            list.add(drawable);
            list.sort(Comparator.comparingInt(Drawable::depth));
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    @Override
    public void drawAll(Graphics2D g2d) {
        try {
            rwLock.readLock().lock();
            list.forEach(d -> d.draw(g2d));
        } finally {
            rwLock.readLock().unlock();
        }

    }

    @Override
    public void onRemove(Drawable drawable) {
        try {
            rwLock.writeLock().lock();
            list.remove(drawable);
        } finally {
            rwLock.writeLock().unlock();
        }
    }
}
