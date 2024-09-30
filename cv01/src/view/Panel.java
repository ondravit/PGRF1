package view;

import controller.Controller2D;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Panel extends JPanel {

    private final BufferedImage raster;


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

    public BufferedImage getRaster() {
        return raster;
    }
}
