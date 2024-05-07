package mouse.project.algorithm.sweep.struct;


import mouse.project.algorithm.sweep.parabola.Parabola;
import mouse.project.algorithm.sweep.parabola.ParabolaService;
import mouse.project.math.FPosition;
import mouse.project.math.Numbers;

import java.util.*;

public class SiteStatus {
    private final List<Site> sites;
    private final ParabolaService parabolaService;
    public SiteStatus() {
        sites = new ArrayList<>();
        parabolaService = new ParabolaService();
    }
    public SiteNode insertAndSplit(Site pI) {
        int index = findSiteAbove(pI);
        Site pJ = sites.get(index);
        if (Numbers.dEquals(pI.getPosition().x(), pJ.getPosition().x())) {
            throw new UnsupportedOperationException(pJ + " is right above " + pI);
        }
        sites.add(index+1, pI);
        sites.add(index+2, pJ);
        return new SiteNode(this, pI ,index+1);
    }

    private int findSiteAbove(Site p) {
        double x = p.getPosition().x();
        double y = p.getPosition().y();
        int low = 0;
        int high = sites.size();
        if (low == high) {
            throw new IllegalStateException("Cannot find site above if status is empty");
        }
        while (true) {
            int interval = high - low;
            if (interval < 3) {
                if (interval == 1) {
                    return low;
                }
                if (interval == 2) {
                    Site site1 = sites.get(low);
                    Site site2 = sites.get(low + 1);
                    double breakpoint = calculateBreakpoint(site1, site2, y);
                    if (breakpoint < x) {
                        return low;
                    } else {
                        return low + 1;
                    }
                }
            }
            int mid = (low + high) >>> 1;
            Site s0 = sites.get(mid - 1);
            Site s1 = sites.get(mid);
            Site s2 = sites.get(mid + 1);

            double breakPoint1 = calculateBreakpoint(s0, s1, y);
            double breakPoint2 = calculateBreakpoint(s1, s2, y);

            if (breakPoint1 < x && x < breakPoint2) {
                return mid;
            }
            if (breakPoint1 > x) {
                low = mid;
            } else {
                high = mid;
            }
        }
    }

    private double calculateBreakpoint(Site s0, Site s1, double y) {
        Parabola p0 = parabolaService.getParabolaFromSiteAndLine(s0, y);
        Parabola p1 = parabolaService.getParabolaFromSiteAndLine(s1, y);
        FPosition intersection = parabolaService.findIntersection(p0, p1);
        return intersection.x();
    }

    public Optional<SiteNode> get(int index) {
        if (index < 0 || index > sites.size()) {
            return Optional.empty();
        }
        Site site = sites.get(index);
        SiteNode node = new SiteNode(this, site, index);
        return Optional.of(node);
    }

    public boolean isEmpty() {
        return sites.isEmpty();
    }

    public void insertFirst(Site e) {
        sites.add(e);
    }

    public void remove(Site site) {
        while (sites.contains(site)) {
            sites.remove(site);
        }
        //TODO: replace with binary search;
    }
}
