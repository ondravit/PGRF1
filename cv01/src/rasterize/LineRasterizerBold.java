package rasterize;

import model.Line;
import raster.Raster;

public class LineRasterizerBold extends LineRasterizer {

    public LineRasterizerBold(Raster raster) {
        super(raster);
    }


    @Override
    public void drawLine(Line line) {
        int x1 = line.getX1();
        int y1 = line.getY1();
        int x2 = line.getX2();
        int y2 = line.getY2();
        int width = line.getWidth();


        if (x1 == x2 && y1 == y2) {
            for (int i = x1-(width/2); i < x2+(width/2); i++) {
                for (int j = y1-(width/2); j < y2+(width/2); j++) {
                    raster.setPixel(i, j, color);
                }
            }
        }

        if (Math.abs(y2 - y1) < Math.abs(x2 - x1)) {
            if (x2 < x1) {
                int i = x1;
                x1 = x2;
                x2 = i;
                i = y1;
                y1 = y2;
                y2 = i;
            }

            float k = (y2 - y1) / (float)(x2 - x1);
            float q = y1 - (k * x1);

            for (int x = x1; x <= x2; x++) {
                float y = (k*x + q);
                if (Math.abs(k) < 1) {
                    for (int i = Math.round(y)-(width/2); i < Math.round(y)+((width/2)); i++) {
                        raster.setPixel(x, i, color);
                    }
                } else if (Math.abs(k) >= 1) {
                    for (int i = x-(width/2); i < x+(width/2); i++) {
                        raster.setPixel(i, Math.round(y), color);
                    }
                }


            }
        } else {
            if (y2 < y1) {
                int i = x1;
                x1 = x2;
                x2 = i;
                i = y1;
                y1 = y2;
                y2 = i;
            }
            float k = (x2 - x1) / (float)(y2 - y1);
            float q = x1 - (k * y1);
            for (int y = y1; y <= y2; y++) {
                float x = (k*y + q);
                if (Math.abs(k) < 1) {
                    for (int i = Math.round(x)-(width/2); i < Math.round(x)+(width/2); i++) {
                        raster.setPixel(i, y, color);
                    }
                } else if (Math.abs(k) >= 1) {
                    for (int i = y-(width/2); i < y+(width/2); i++) {
                        raster.setPixel(Math.round(x), i, color);
                    }
                }
            }
        }


    }
}
