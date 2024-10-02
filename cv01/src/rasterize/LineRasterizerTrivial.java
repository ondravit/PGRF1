package rasterize;

import model.Line;

import java.awt.image.BufferedImage;

public class LineRasterizerTrivial extends LineRasterizer {
    public LineRasterizerTrivial(BufferedImage raster) {
        super(raster);
    }
    public LineRasterizerTrivial(BufferedImage raster, int color){
        super(raster, color);
    }

    @Override
    public void drawLine(Line line) {
        int x1 = line.getX1();
        int y1 = line.getY1();
        int x2 = line.getX2();
        int y2 = line.getY2();

        float k = (y2 - y1) / (float)(x2 - x1);
        float q = y1 - (k * x1);

        if (x2 < x1) {
            int i = x1;
            x1 = x2;
            x2 = i;
        }

        for (int x = x1; x <= x2; x++) {
            float y = (k*x + q);
            raster.setRGB(x, Math.round(y), color);
        }
    }
}
