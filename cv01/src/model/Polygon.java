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

    public void addPoint(int x, int y) {
        points.add(new Point(x, y));
    }

    public Point getPoint(int index) {
        return points.get(index);
    }

    public int size() {
        return points.size();
    }


}
