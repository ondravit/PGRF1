package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Panel extends JPanel {

    private final BufferedImage raster;
    int menu = 1;


    public Panel(int width, int height) {
        setPreferredSize(new Dimension(width, height));

        raster = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    }

    // vykreslí černé pozadí
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(raster, 0, 0, null);
    }

    public void clear() {
        Graphics g = raster.getGraphics();
        g.clearRect(0, 0, getWidth(), getHeight());
    }

    public void startMenu() {
        if (menu == 0) { raster.getGraphics().drawString("Show menu (M)", 5, 20);
       } else {
            raster.getGraphics().drawString("Hide menu (M)", 5, 20);
            raster.getGraphics().drawString("Press and drag mouse to draw a line", 5, 50);
            raster.getGraphics().drawString("Press and hold CTRL to snap line to grid", 5, 70);
            raster.getGraphics().drawString("Use C to clear canvas", 5, 90);
        }
    }

    public BufferedImage getRaster() {
        return raster;
    }

    public int getMenu() {
        return menu;
    }

    public void setMenu(int menu) {
        this.menu = menu;
    }
}
