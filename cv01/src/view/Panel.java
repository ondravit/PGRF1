package view;

import raster.Raster;
import raster.RasterBufferedImage;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    private final Raster raster;
    int menu = 1;


    public Panel(int width, int height) {
        setPreferredSize(new Dimension(width, height));

        raster = new RasterBufferedImage(width, height);

    }

    //Background raster
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((RasterBufferedImage)raster).paint(g);
    }

    public void clear() {
        raster.clear();
    }

    public void startMenu() {
        if (menu == 0) { ((RasterBufferedImage)raster).getGraphics().drawString("(M) Show menu", 5, 20);
       } else {
            ((RasterBufferedImage)raster).getGraphics().drawString("(M) Hide menu", 5, 20);
            ((RasterBufferedImage)raster).getGraphics().drawString("(C) Clear canvas", 5, 50);
            ((RasterBufferedImage)raster).getGraphics().drawString("(1) Draw a line (Press and hold SHIFT to snap line to grid)", 5, 70);
            ((RasterBufferedImage)raster).getGraphics().drawString("(2) Draw a polygon", 5, 90);
            ((RasterBufferedImage)raster).getGraphics().drawString("(3) Draw a bold line", 5, 110);
            ((RasterBufferedImage)raster).getGraphics().drawString("Use arrow keys to change width", 5, 130);
            ((RasterBufferedImage)raster).getGraphics().drawString("(R) Red (G) Green (B) Blue", 5, 150);
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
