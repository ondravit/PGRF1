package fill;

import model.Line;
import model.Point;
import model.Polygon;
import raster.Raster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScanLine implements Filler {

    Raster raster;
    Polygon polygon;

    public ScanLine(Raster raster) {
        this.raster = raster;
    }

    @Override
    public void fill() {

    }

    public void fillPolygon(Polygon polygon) {
        this.polygon = polygon;

        // Find the minimum and maximum y-values
        int minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;
        for (Point p : polygon.getPoints()) {
            if (p.y() < minY) minY = p.y();
            if (p.y() > maxY) maxY = p.y();
        }

        // Build edge table
        ArrayList<Line>[] lineTable = new ArrayList[maxY - minY + 1];
        for (int i = 0; i < lineTable.length; i++) {
            lineTable[i] = new ArrayList<>();
        }

        int n = polygon.size();
        for (int i = 0; i < n; i++) {
            Point p1 = polygon.getPoint(i);
            Point p2 = polygon.getPoint((i + 1) % n);

            if (p1.y() == p2.y()) continue; // Skip horizontal edges

            // Swap to ensure p1.y < p2.y
            if (p1.y() > p2.y()) {
                Point temp = p1;
                p1 = p2;
                p2 = temp;
            }

            double invSlope = (double)(p2.x() - p1.x()) / (p2.y() - p1.y()); // Inverse slope
            lineTable[p1.y() - minY].add(new Line(p1.x(), p2.y(), invSlope));
        }

        // Active edge table (AET)
        ArrayList<Line> activeEdgeTable = new ArrayList<>();

        // Process each scan line from minY to maxY
        for (int y = minY; y <= maxY; y++) {
            // Add edges from edge table to active edge table
            activeEdgeTable.addAll(lineTable[y - minY]);

            // Remove edges where the maximum y has been reached
            int finalY = y;
            activeEdgeTable.removeIf(e -> e.maxY() <= finalY);

            // Sort the AET by x-coordinate
            Collections.sort(activeEdgeTable, Comparator.comparingDouble(e -> e.getCurrentX()));

            // Fill between pairs of intersections
            for (int i = 0; i < activeEdgeTable.size(); i += 2) {
                Line e1 = activeEdgeTable.get(i);
                Line e2 = activeEdgeTable.get(i + 1);

                for (int x = (int)Math.ceil(e1.getCurrentX()); x < e2.getCurrentX(); x++) {
                    raster.setPixel(x, y, raster.getPixel(polygon.getPoint(0).x(), polygon.getPoint(0).y()));
                }
            }

            // Update x values in the AET for the next scan line
            for (Line line : activeEdgeTable) {
                line.setInvSlope(line.getInvSlope());
                line.updateCurrentX();
            }
        }
    }
}
