package mouse.project.algorithm.sweep.struct;


import mouse.project.algorithm.sweep.neighbors.Neighbors;
import mouse.project.algorithm.sweep.neighbors.NeighborsImpl;
import mouse.project.algorithm.sweep.parabola.Parabola;
import mouse.project.algorithm.sweep.parabola.ParabolaService;
import mouse.project.math.FPosition;
import mouse.project.math.Numbers;

import java.util.*;

public class SiteStatus {
    private final List<Site> sites;
    private final ParabolaService parabolaService;
    private int idCount = 0;
    public SiteStatus() {
        sites = new ArrayList<>();
        parabolaService = new ParabolaService();
    }
    public Optional<SiteNode> onSameLineCase(Site pI, double y) {
        if (!sameLineCase(y)) {
            return Optional.empty();
        }
        Comparator<Site> order = (o1, o2) -> Numbers.dCompare(o1.getPosition().x(), o2.getPosition().x());
        int index = Collections.binarySearch(sites, pI, order);
        assert index < 0;
        index = - index - 1;
        sites.add(index, new Site(pI.getPosition(), pI.getLetter(), idCount++));
        return Optional.of(new SiteNode(this, pI ,index));
    }
    public SiteNode insertAndSplit(Site pI, double y) {
        if (sites.isEmpty()) {
            throw new IllegalStateException("Status is empty. Cannot do insert and split for " + pI);
        }
        int index = findSiteAbove(pI.getPosition().x(), y);
        Site pJ = sites.get(index);
        sites.add(index+1, new Site(pI.getPosition(), pI.getLetter(), idCount++));
        sites.add(index+2, new Site(pJ.getPosition(), pJ.getLetter(), idCount++));
        return new SiteNode(this, pI ,index+1);
    }


    private boolean sameLineCase(double y) {
        return sites.stream().allMatch(site -> Numbers.dEquals(site.getPosition().y(), y));
    }

    private int findSiteAbove(double x, double y) {
        int low = 0;
        int high = sites.size();
        if (low == high) {
            throw new IllegalStateException("Cannot find site above (" + x + ", " + y + ") if status is empty");
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
                    if (Numbers.dLess(x, breakpoint)) {
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
            if (Numbers.dLessOrEquals(breakPoint1 ,x) && Numbers.dLessOrEquals(x , breakPoint2)) {
                return mid;
            }
            if (Numbers.dLess(breakPoint1, x)) {
                low = mid;
            } else {
                high = mid;
            }
        }
    }

    public double calculateBreakpoint(Site left, Site right, double y) {
        Parabola p0 = parabolaService.getParabolaFromSiteAndLine(left, y);
        Parabola p1 = parabolaService.getParabolaFromSiteAndLine(right, y);
        FPosition intersection = parabolaService.findIntersection(p0, p1);
        double result = intersection.x();
        if (Double.isNaN(result)) {
            if (Numbers.dEquals(left.getPosition().y(), y) && Numbers.dEquals(right.getPosition().y(), y)) {
                return (left.getPosition().x() + right.getPosition().x()) / 2.0;
            }
            if (Numbers.dEquals(left.getPosition().y(), y)) {
                return left.getPosition().x();
            }
            if (Numbers.dEquals(right.getPosition().y(), y)) {
                return right.getPosition().x();
            }
            throw new IllegalStateException("Unexpected Nan: " + left + ", " + right + ", y: " + y);
        }
        return result;
    }

    public Optional<SiteNode> get(int index) {
        if (index < 0 || index >= sites.size()) {
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

    public Neighbors<SiteNode> remove(double x, double y) {
        int siteAbove = findSiteAbove(x, y);
        Neighbors<SiteNode> result = new NeighborsImpl<>();
        sites.remove(siteAbove);
        if (siteAbove != 0) {
            result.setLeft(new SiteNode(this, sites.get(siteAbove-1), siteAbove-1));
        }
        if (siteAbove != sites.size()) {
            result.setRight(new SiteNode(this, sites.get(siteAbove), siteAbove));
        }
        return result;
    }

    public Site generateSite(String idString, FPosition fPosition) {
        return new Site(fPosition, idString, idCount++);
    }

    public String print() {
        return sites.stream().map(Site::getLetter).toList().toString();
    }
}
