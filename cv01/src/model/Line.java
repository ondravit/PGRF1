package model;

public class Line {
    private final int  x1;
    private int x2;
    private final int y1;
    private int y2;
    private int width;

    public Line(int x1, int y1, int x2, int y2, int width) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.width = width;
    }

    public Line(Point p1, Point p2) {
        x1 = p1.x();
        x2 = p2.x();
        y1 = p1.y();
        y2 = p2.y();
    }

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getY1() {
        return y1;
    }

    public int getY2() {
        return y2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
