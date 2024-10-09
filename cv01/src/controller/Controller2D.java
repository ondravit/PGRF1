package controller;

import model.Line;
import rasterize.LineRasterizer;
import rasterize.LineRasterizerTrivial;
import view.Panel;

import java.awt.event.*;
import java.util.ArrayList;

public class Controller2D {
    private final Panel panel;
    private LineRasterizer lineRasterizer;
    private Line line;
    private ArrayList<Line> lineList = new ArrayList<>();
    private boolean snap = false;

    public Controller2D(Panel panel) {
        this.panel = panel;
        panel.startMenu();

        //lineRasterizer = new LineRasterizerGraphics(panel.getRaster());
        lineRasterizer = new LineRasterizerTrivial(panel.getRaster());


        initListeners();
        panel.repaint();
    }

    private void initListeners(){

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                line = new Line(e.getX(), e.getY(), e.getX(), e.getY());
                line.setX1(e.getX());
                line.setY1(e.getY());
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                panel.clear();

                int x2 = e.getX();
                int y2 = e.getY();

                // Apply snapping logic if space is pressed
                if (snap) {
                    if (Math.abs(x2 - line.getX1()) > Math.abs(y2 - line.getY1())) {
                        y2 = line.getY1();  // Snap to horizontal
                    } else {
                        x2 = line.getX1();  // Snap to vertical
                    }
                }

                line.setX2(x2);
                line.setY2(y2);

                lineRasterizer.drawLine(line);

                for (Line line : lineList) {
                    lineRasterizer.drawLine(line);
                }
                panel.startMenu();
                panel.repaint();
            }
        });

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                lineList.add(line);
                panel.startMenu();
                panel.repaint();
            }
        });

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                //Menu bar show and hide
                if (e.getKeyCode() == KeyEvent.VK_M) {
                    if(panel.getMenu()==0){
                        panel.setMenu(1);
                        panel.clear();
                        for (Line line : lineList) {
                            lineRasterizer.drawLine(line);
                        }
                        panel.startMenu();
                        panel.repaint();
                    } else if(panel.getMenu()==1){
                        panel.setMenu(0);
                        panel.clear();
                        panel.startMenu();
                        for (Line line : lineList) {
                            lineRasterizer.drawLine(line);
                        }
                        panel.repaint();
                    }

                }
                //Clear button
                if (e.getKeyCode() == KeyEvent.VK_C) {
                    lineList.clear();
                    panel.clear();
                    panel.startMenu();
                    panel.repaint();
                }

                //Ctrl snap
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    snap = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    snap = false;
                }
            }
        });
    }
}
