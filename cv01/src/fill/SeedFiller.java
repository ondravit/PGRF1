package fill;

import raster.Raster;

public class SeedFiller implements Filler {

    Raster raster;
    int fillColor = 0x00ff00;
    int backgroundColor;
    int x;
    int y;

    public SeedFiller(int y, int x, int fillColor, Raster raster) {
        this.y = y;
        this.x = x;
        this.fillColor = fillColor;
        this.raster = raster;
        this.backgroundColor = raster.getPixel(x, y);
    }

    @Override
    public void fill() {

    }

    public void seedFill(int x, int y) {
        int pixel = raster.getPixel(x, y);
        if (pixel == backgroundColor) {
            raster.setPixel(x, y, fillColor);
            seedFill(x+1, y);
            seedFill(x-1, y);
            seedFill(x, y+1);
            seedFill(x, y-1);
        }

    }
}

