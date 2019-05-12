package sample;

public class memUnit {

    private double start;
    private double size;
    private String filled;
    private String name;


    public memUnit() {

    }


    public memUnit(double start, double size, String filled, String name) {
        this.start = start;
        this.size = size;
        this.filled = filled;
        this.name = name;
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getFilled() {
        return filled;
    }

    public void setFilled(String filled) {
        this.filled = filled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
