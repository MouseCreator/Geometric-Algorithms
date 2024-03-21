package mouse.project.algorithm.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class SNodes {
    private final TreeSet<SNode> nodeSet;
    public SNodes() {
        Comparator<SNode> comparator = (s1, s2)->{
            int y = s1.getPosition().y() - s2.getPosition().y();
            if (y==0) {
                return s1.getPosition().x() - s2.getPosition().x();
            }
            return y;
        };
        nodeSet = new TreeSet<>(comparator);
    }


    public void add(SNode node) {
        nodeSet.add(node);
    }
    public SNode min() {
        return nodeSet.first();
    }
    public SNode max() {
        return nodeSet.last();
    }
    public SNode median() {
        return (SNode) nodeSet.toArray()[nodeSet.size()>>>1];
    }

    public List<SNode> getInSortedOrder() {
        return new ArrayList<>(nodeSet);
    }


}
