package model;

import java.util.ArrayList;

public class Polygon {
    private final ArrayList<Point> points;

    public Polygon() {
        points = new ArrayList<>();
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public Point getPoint(int index) {
        return points.get(index);
    }

    public int size() {
        return points.size();
    }

    public void clear() {
        points.clear();
    }

    public void setLastPoint(Point point) {
        points.set(points.size() - 1, point);
    }


}
