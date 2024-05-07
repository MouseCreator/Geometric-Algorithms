package mouse.project.algorithm.sweep.struct;

import lombok.Getter;

import java.util.Optional;

public class SiteNode {
    private SiteStatus status;
    @Getter
    private final Site site;
    @Getter
    private final int index;

    public SiteNode(SiteStatus status, Site site, int index) {
        this.site = site;
        this.index = index;
    }

    public Optional<SiteNode> next() {
        return status.get(index+1);
    }
    public Optional<SiteNode> prev() {
        return status.get(index-1);
    }
}
