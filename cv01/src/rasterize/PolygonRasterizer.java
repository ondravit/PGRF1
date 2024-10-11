package rasterize;

import model.Line;
import model.Polygon;

public class PolygonRasterizer {
    private LineRasterizer lineRasterizer;

    public PolygonRasterizer(LineRasterizer lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
    }

    public PolygonRasterizer() {

    }

    public void rasterize(Polygon polygon) {
        if (polygon.size() < 3) {
            return;
        } else {
            for (int i = 0; i < polygon.size(); i++) {
                if (i != polygon.size() - 1) {
                    lineRasterizer.drawLine(new Line(polygon.getPoint(i), polygon.getPoint(i + 1)));
                } else {
                    lineRasterizer.drawLine(new Line(polygon.getPoint(i), polygon.getPoint(0)));
                }

            }
        }
    }

    public void setLineRasterizer(LineRasterizer lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
    }
}
