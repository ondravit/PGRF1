package controller;

import view.Panel;

import java.awt.*;
import java.awt.event.*;

public class Controller2D {
    private final Panel panel;
    private int x, y;

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
                if (e.getButton() == MouseEvent.BUTTON1) {
                    x = e.getX();
                    y = e.getY();
                    draw();
                }
                if (e.getButton() == MouseEvent.BUTTON2) {
                    x = e.getX();
                    y = e.getY();
                    draw();
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    x = e.getX();
                    y = e.getY();
                    draw();
                }
                panel.repaint();
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    x = e.getX();
                    y = e.getY();
                    draw();
                }
                if (e.getButton() == MouseEvent.BUTTON2) {
                    x = e.getX();
                    y = e.getY();
                    draw();
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    x = e.getX();
                    y = e.getY();
                    draw();
                }
                panel.repaint();
            }
        });

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        x--;
                        break;
                    case KeyEvent.VK_RIGHT:
                        x++;
                        break;
                    case KeyEvent.VK_UP:
                        y--;
                        break;
                    case KeyEvent.VK_DOWN:
                        y++;
                        break;
                }
                draw();
                panel.repaint();
            }
        });
    }

    private void draw() {
        clear();
        panel.getRaster().setRGB(x, y, 0xff0000);
    }

    public void start() {
        x = panel.getWidth()/2;
        y = panel.getHeight()/2;
        draw();
        panel.repaint();
    }
    public void clear() {
        Graphics g = panel.getGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, panel.getWidth(), panel.getHeight());
    }
}
