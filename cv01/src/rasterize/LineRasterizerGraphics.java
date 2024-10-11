package rasterize;

import model.Line;

import java.awt.*;
import raster.Raster;
import raster.RasterBufferedImage;

public class LineRasterizerGraphics extends LineRasterizer {
    public LineRasterizerGraphics(Raster raster) {
        super(raster);
    }
    public LineRasterizerGraphics(Raster raster, int color){
        super(raster, color);
    }

    @Override
    public void drawLine(Line line) {
        Graphics g = ((RasterBufferedImage)raster).getGraphics();
        g.setColor(new Color(color));
        g.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
    }
}
