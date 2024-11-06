package model;

public class Line {
    private final int  x1;
    private int x2;
    private final int y1;
    private int y2;
    private int width;
    private double invSlope;
    private double currentX;

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

    // Constructor for scan-line filling with starting x, ending y, and inverse slope
    public Line(int startX, int endY, double invSlope) {
        this.x1 = startX;
        this.y1 = endY; // maxY for edge tracking in scan-line algorithm
        this.invSlope = invSlope;
        this.currentX = startX;
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

    public double getInvSlope() {
        return invSlope;
    }

    public double getCurrentX() {
        return currentX;
    }

    public void updateCurrentX() {
        currentX += invSlope;
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

    public void setInvSlope(double invSlope) {
        this.invSlope = invSlope;
    }

    public int maxY() {
        if (y1 > y2) {
            return y1;
        } else {
            return y2;
        }
    }
}
