package model;

import java.util.ArrayList;

public class Polygon {
    private final ArrayList<Point> points;
    Point center;

    public Polygon() {
        points = new ArrayList<>();
    }

    public Polygon(Point center) {
        this.center = center;
        points = new ArrayList<>();
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public Point getPoint(int index) {
        return points.get(index);
    }

    public ArrayList<Point> getPoints() {
        return points;
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

    public void setPentagon(Point point) {

        addPoint(point);
        double radius = Math.sqrt(Math.pow(center.x()-point.x(), 2) + Math.abs(Math.pow(center.y()-point.y(), 2)));
        double initialAngle = Math.atan2(point.y() - center.y(), point.x() - center.x());
        for (int i = 1; i < 5; i++) {
            double angle = initialAngle + i * (2 * Math.PI / 5);
            int x = (int) (center.x() + radius * Math.cos(angle));
            int y = (int) (center.y() + radius * Math.sin(angle));
            addPoint(new Point(x, y));
        }
    }

}
