package mouse.project.algorithm.impl;

import mouse.project.algorithm.sorted.SortedList;
import mouse.project.algorithm.sorted.SortedListImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SNodes {
    private final SortedList<SNode> nodeSet;
    public SNodes() {
        Comparator<SNode> comparator = (s1, s2)->{
            int y = s1.getPosition().y() - s2.getPosition().y();
            if (y==0) {
                return s1.getPosition().x() - s2.getPosition().x();
            }
            return y;
        };
        nodeSet = new SortedListImpl<>(comparator);
    }


    public void add(SNode node) {
        nodeSet.add(node);
    }

    public SNode median() {
        return nodeSet.median();
    }

    public List<SNode> getInSortedOrder() {
        return new ArrayList<>(nodeSet);
    }


}
