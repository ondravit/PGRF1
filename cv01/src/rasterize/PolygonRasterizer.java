package rasterize;

import model.Line;
import model.Polygon;

public class PolygonRasterizer {
    private LineRasterizer lineRasterizer;

    public PolygonRasterizer(LineRasterizer lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
    }


    public void rasterize(Polygon polygon) {
        if (polygon.size() < 3) {
            return;
        } else {
            for (int i = 0; i < polygon.size(); i++) {
                lineRasterizer.drawLine(new Line(polygon.getPoint(i), polygon.getPoint((i + 1)%polygon.size())));
            }
        }
    }

    public void setLineRasterizer(LineRasterizer lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
    }
}
