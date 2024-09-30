package controller;

import view.Panel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Controller2D {
    private final Panel panel;

    public Controller2D(Panel panel) {
        this.panel = panel;

//        vykreslí červený pixel na pozicic 50,50
//        panel.getRaster().setRGB(50, 50, 0xff0000);

//        vykreslí čáru
/*
        for (int i = 0; i < 350; i++) {
            if (i % 2 == 0) {
                panel.getRaster().setRGB(i, 50, 0xff0000);
            } else {
                panel.getRaster().setRGB(i, 50, 0xffffff);
            }
            panel.repaint();

        }
*/

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1)
                    panel.getRaster().setRGB(e.getX(), e.getY(), 0xffff00);
                if (e.getButton() == MouseEvent.BUTTON2)
                    panel.getRaster().setRGB(e.getX(), e.getY(), 0xff00ff);
                if (e.getButton() == MouseEvent.BUTTON3)
                    panel.getRaster().setRGB(e.getX(), e.getY(), 0xffffff);
                panel.repaint();
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1)
                    panel.getRaster().setRGB(e.getX(), e.getY(), 0xffff00);
                if (e.getButton() == MouseEvent.BUTTON2)
                    panel.getRaster().setRGB(e.getX(), e.getY(), 0xff00ff);
                if (e.getButton() == MouseEvent.BUTTON3)
                    panel.getRaster().setRGB(e.getX(), e.getY(), 0xffffff);
                panel.repaint();
            }
        });
    }
}
