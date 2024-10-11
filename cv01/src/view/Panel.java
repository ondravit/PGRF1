package view;

import controller.Controller2D;
import raster.Raster;
import raster.RasterBufferedImage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Panel extends JPanel {

    private final Raster raster;
    int menu = 1;


    public Panel(int width, int height) {
        setPreferredSize(new Dimension(width, height));

        raster = new RasterBufferedImage(width, height);

    }

    // vykreslí černé pozadí
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((RasterBufferedImage)raster).paint(g);
    }

    public void clear() {
        raster.clear();
    }

    public void startMenu() {
        if (menu == 0) { ((RasterBufferedImage)raster).getGraphics().drawString("Show menu (M)", 5, 20);
       } else {
            ((RasterBufferedImage)raster).getGraphics().drawString("Hide menu (M)", 5, 20);
            ((RasterBufferedImage)raster).getGraphics().drawString("Press and drag mouse to draw a line", 5, 50);
            ((RasterBufferedImage)raster).getGraphics().drawString("Press and hold SHIFT to snap line to grid", 5, 70);
            ((RasterBufferedImage)raster).getGraphics().drawString("Use C to clear canvas", 5, 90);
        }
    }

    public Raster getRaster() {
        return raster;
    }

    public int getMenu() {
        return menu;
    }

    public void setMenu(int menu) {
        this.menu = menu;
    }
}
