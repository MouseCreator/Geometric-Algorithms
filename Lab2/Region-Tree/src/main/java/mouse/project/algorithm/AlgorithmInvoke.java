package mouse.project.algorithm;

import mouse.project.graphics.GraphicsChangeListener;
import mouse.project.ui.components.point.PointSet;
import mouse.project.ui.components.point.WrapBox;

public class AlgorithmInvoke implements Algorithm {

    private final GraphicsChangeListener changeListener;
    private final RegionAlg regionAlg;

    public AlgorithmInvoke(GraphicsChangeListener changeListener) {
        this.changeListener = changeListener;
        regionAlg = new RegionAlgImpl();
    }

    @Override
    public void build(PointSet set) {
        regionAlg.build(set, changeListener);
    }

    @Override
    public void find(WrapBox box) {
        regionAlg.find(box, changeListener);
    }

    @Override
    public void clear() {
        changeListener.clear();
        regionAlg.clear();
    }
}
