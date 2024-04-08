package mouse.project.algorithm;

import mouse.project.algorithm.builder.Initializer;
import mouse.project.algorithm.common.CPoint;
import mouse.project.algorithm.common.CSet;
import mouse.project.algorithm.descriptor.SegmentTreeDescriptor;
import mouse.project.algorithm.gfx.CircleGFX;
import mouse.project.algorithm.mapper.Mapper;
import mouse.project.algorithm.search.RegionSearch;
import mouse.project.algorithm.search.RegionSearchImpl;
import mouse.project.algorithm.tree.SegmentTree;
import mouse.project.graphics.GraphicsChangeListener;
import mouse.project.state.ConstUtils;
import mouse.project.ui.components.point.PointSet;
import mouse.project.ui.components.point.WrapBox;

import java.util.List;

public class RegionAlgImpl implements RegionAlg {

    private SegmentTree segmentTree = null;
    private final SegmentTreeDescriptor descriptor;
    private final Initializer initializer;
    private final RegionSearch regionSearch;
    public RegionAlgImpl() {
        descriptor = new SegmentTreeDescriptor();
        initializer = new Initializer();
        regionSearch = new RegionSearchImpl();
    }

    @Override
    public void build(PointSet pointSet, GraphicsChangeListener graphicsChangeListener) {
        CSet cSet = Mapper.toCSet(pointSet);
        segmentTree = initializer.createTreeFor(cSet);
        descriptor.describe(segmentTree);
    }

    @Override
    public void find(WrapBox target, GraphicsChangeListener graphicsChangeListener) {
        List<CPoint> points = regionSearch.find(segmentTree, Mapper.toArea(target));
        highlightPoints(points, graphicsChangeListener);
    }

    private void highlightPoints(List<CPoint> points, GraphicsChangeListener graphicsChangeListener) {
        points.forEach(
                point -> graphicsChangeListener.add(new CircleGFX(point.position(), ConstUtils.SELECT_NODE_DIAMETER))
        );
    }

    @Override
    public void clear() {
        segmentTree = null;
    }
}
