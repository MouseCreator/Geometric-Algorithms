package mouse.project.utils.math;

public record GenLine(double a, double b, double c){

    @Override
    public String toString() {
        return String.format("%.2f*x+%.2f*y+%.2f",a,b,c);
    }
}
