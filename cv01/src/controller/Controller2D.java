package controller;

import model.Line;
import model.Point;
import model.Polygon;
import rasterize.LineRasterizer;
import rasterize.LineRasterizerBold;
import rasterize.LineRasterizerTrivial;
import rasterize.PolygonRasterizer;
import view.Panel;

import java.awt.event.*;
import java.util.ArrayList;

public class Controller2D {
    private final Panel panel;
    private LineRasterizer lineRasterizer;
    private final LineRasterizer lineRasterizer1;
    private final LineRasterizer lineRasterizer2;
    private final PolygonRasterizer polygonRasterizer;
    private final ArrayList<Line> lineList = new ArrayList<>();
    private boolean snap = false;
    private Line line;
    private final Polygon polygon = new Polygon();
    int mode = 1;
    int lineWidth = 1;
    int mouseX = 0;
    int mouseY = 0;
    boolean mousePressed = false;

    public Controller2D(Panel panel) {
        this.panel = panel;
        panel.startMenu();

        //Initialize rasterizers
        lineRasterizer1 = new LineRasterizerTrivial(panel.getRaster());
        lineRasterizer2 = new LineRasterizerBold(panel.getRaster());
        polygonRasterizer = new PolygonRasterizer(lineRasterizer1);

        lineRasterizer = lineRasterizer1;

        initListeners();
        panel.repaint();
    }

    private void initListeners(){

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePressed = true;
                panel.clear();
                if (mode == 2) {
                    polygon.addPoint(new Point(e.getX(), e.getY()));
                    if (polygon.size() <= 3) line = new Line(e.getX(), e.getY(), e.getX(), e.getY(), lineWidth);
                } else line = new Line(e.getX(), e.getY(), e.getX(), e.getY(), lineWidth);
                repaint();
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                panel.clear();
                mouseX = e.getX();
                mouseY = e.getY();

                switch (mode){
                    case 1,3:
                        int x2 = e.getX();
                        int y2 = e.getY();

                        snapToGrid(x2, y2);

                        break;
                    case 2:
                        polygon.setLastPoint(new Point(e.getX(), e.getY()));
                        polygonRasterizer.rasterize(polygon);
                        break;
                }
                lineRasterizer.drawLine(line);
                repaint();
            }
        });

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mousePressed = false;
                switch (mode){
                    case 1,3:
                        lineList.add(line);
                        break;
                    case 2:
                        lineList.add(line);
                        polygonRasterizer.rasterize(polygon);
                        break;
                }
                repaint();
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
                        repaint();
                    } else if(panel.getMenu()==1){
                        panel.setMenu(0);
                        panel.clear();
                        repaint();
                    }
                }

                //Clear button
                if (e.getKeyCode() == KeyEvent.VK_C) {
                    clear();
                    repaint();
                }

                //Ctrl snap
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    snap = true;
                    if (mousePressed) {
                        panel.clear();
                        snapToGrid(line.getX2(), line.getY2());
                        lineRasterizer.drawLine(line);
                        repaint();
                    }
                }


                //Red color
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    setColor(0xff0000);
                    lineRasterizer.drawLine(line);
                }
                //Green color
                if (e.getKeyCode() == KeyEvent.VK_G) {
                    setColor(0x00ff00);
                    lineRasterizer.drawLine(line);
                }
                //Blue color
                if (e.getKeyCode() == KeyEvent.VK_B) {
                    setColor(0x0000ff);
                    lineRasterizer.drawLine(line);
                }
                //Line mode
                if (e.getKeyCode() == KeyEvent.VK_1) {
                    if (mode != 1) {
                        clear();
                        lineRasterizer = lineRasterizer1;
                        mode = 1;
                        repaint();
                    }
                }

                //Polygon mode
                if (e.getKeyCode() == KeyEvent.VK_2) {
                    if (mode != 2) {
                        clear();
                        lineRasterizer = lineRasterizer1;
                        polygonRasterizer.setLineRasterizer(lineRasterizer1);
                        mode = 2;
                        repaint();
                    }
                }

                //Bold mode
                if (e.getKeyCode() == KeyEvent.VK_3) {
                    if (mode != 3) {
                        clear();
                        lineRasterizer = lineRasterizer2;
                        lineWidth = 6;
                        mode = 3;
                        repaint();
                    }
                }

                //increase bold line width
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    lineWidth += 2;
                    line.setWidth(lineWidth);
                    lineRasterizer.drawLine(line);
                    repaint();
                }

                //decrease bold line width
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (lineWidth > 2) lineWidth -= 2;
                    panel.clear();
                    line.setWidth(lineWidth);
                    lineRasterizer.drawLine(line);
                    repaint();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    snap = false;
                    if (mousePressed) {
                        panel.clear();
                        line.setX2(mouseX);
                        line.setY2(mouseY);
                        lineRasterizer.drawLine(line);
                        repaint();
                    }
                }
            }
        });
    }

    //Clear everything
    private void clear() {
        polygon.clear();
        lineList.clear();
        panel.clear();
    }

    private void snapToGrid(int x2, int y2) {
        //Snap to grid
        if (snap) {
            double deltaX = x2 - line.getX1();
            double deltaY = y2 - line.getY1();

            double slope = deltaY / deltaX;
            double minSlope = Math.tan(Math.toRadians(30));
            double maxSlope = Math.tan(Math.toRadians(60));

            //Horizontal or vertical
            if (Math.abs(deltaX) > Math.abs(deltaY) && Math.abs(slope) < minSlope) {
                y2 = line.getY1();
            } else if (Math.abs(deltaY) > Math.abs(deltaX) && Math.abs(slope) > maxSlope) {
                x2 = line.getX1();
            }
            //Diagonal
            else if (Math.abs(slope) >= minSlope && Math.abs(slope) <= maxSlope) {
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    y2 = line.getY1() + (int) (Math.signum(deltaY) * Math.abs(deltaX));
                } else {
                    x2 = line.getX1() + (int) (Math.signum(deltaX) * Math.abs(deltaY));
                }
            }
        }
        line.setX2(x2);
        line.setY2(y2);
    }

    //Set color
    private void setColor(int color) {
        lineRasterizer.setColor(color);
        lineRasterizer1.setColor(color);
        lineRasterizer2.setColor(color);
        if (mode == 2) polygonRasterizer.rasterize(polygon);
        repaint();
    }

    //repaint panel
    private void repaint() {
        for (Line line : lineList) {
            line.setWidth(lineWidth);
            lineRasterizer.drawLine(line);
        }
        panel.startMenu();
        panel.repaint();
    }
}
