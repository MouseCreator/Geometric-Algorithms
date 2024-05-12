package mouse.project.algorithm.sweep.struct;

import lombok.Data;
import mouse.project.math.FPosition;

@Data
public class Site {
    private FPosition position;
    private int id = -1;
    private String letter = "";
    public Site(FPosition fPosition, int id) {
        this.position = fPosition;
        this.id = id;
    }
    public Site(FPosition fPosition, String idStr, int id) {
        this.position = fPosition;
        this.id = id;
        this.letter = idStr;
    }
    public Site(FPosition fPosition) {
        this.position = fPosition;
    }

    @Override
    public String toString() {
        return "Site{" + letter + "_" + id + "->" + position + "}";
    }
}
