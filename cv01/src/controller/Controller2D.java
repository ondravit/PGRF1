package controller;

import fill.ScanLine;
import fill.SeedFiller;
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
    private final ScanLine scanLine;
    private final ArrayList<Line> lineList = new ArrayList<>();
    private boolean snap = false;
    private Line line;
    private Polygon polygon = new Polygon();
    int mode = 1;
    int lineWidth = 1;
    int mouseX = 0;
    int mouseY = 0;
    boolean mousePressed = false;
    boolean filledPolygon = false;

    public Controller2D(Panel panel) {
        this.panel = panel;
        panel.startMenu();

        //Initialize rasterizers
        lineRasterizer1 = new LineRasterizerTrivial(panel.getRaster());
        lineRasterizer2 = new LineRasterizerBold(panel.getRaster());
        polygonRasterizer = new PolygonRasterizer(lineRasterizer1);

        lineRasterizer = lineRasterizer1;
        scanLine = new ScanLine(panel.getRaster());

        initListeners();
        panel.repaint();
    }

    private void initListeners(){

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    mousePressed = true;
                    panel.clear();
                    if (mode == 2) {
                        polygon.addPoint(new Point(e.getX(), e.getY()));
                        if (polygon.size() <= 3){
                            line = new Line(e.getX(), e.getY(), e.getX(), e.getY(), lineWidth);
                            fillPolygon();
                        }

                    } else if (mode == 4) {
                        clear();
                        polygon = new Polygon(new Point(e.getX(), e.getY()));
                    } else {
                        line = new Line(e.getX(), e.getY(), e.getX(), e.getY(), lineWidth);
                    }
                    repaint();
                }

                //Fill polygon when right click
                if ((mode == 2 || mode == 4) && e.getButton() == MouseEvent.BUTTON3) {
                    SeedFiller seedFiller = new SeedFiller(e.getX(), e.getY(), lineRasterizer.getColor(), panel.getRaster());
                    seedFiller.seedFill(e.getX(), e.getY());
                    repaint();
                }

            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    panel.clear();
                    mouseX = e.getX();
                    mouseY = e.getY();

                    switch (mode){
                        case 1,3: //line or bold line
                            int x2 = e.getX();
                            int y2 = e.getY();

                            snapToGrid(x2, y2);

                            lineRasterizer.drawLine(line);
                            break;
                        case 2: //polygon
                            polygon.setLastPoint(new Point(mouseX, mouseY));
                            polygonRasterizer.rasterize(polygon);
                            lineRasterizer.drawLine(line);
                            fillPolygon();
                            break;
                        case 4: //pentagon
                            clear();
                            polygon.setPentagon(new Point(mouseX, mouseY));
                            polygonRasterizer.rasterize(polygon);
                            fillPolygon();
                            break;
                    }

                    repaint();
                }

            }
        });

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    mousePressed = false;
                    switch (mode){
                        case 1,3:
                            lineList.add(line);
                            break;
                        case 2:
                            lineList.add(line);
                            polygonRasterizer.rasterize(polygon);
                            fillPolygon();
                            break;
                        case 4:
                            fillPolygon();
                            break;
                    }
                    repaint();
                }
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
                    fillPolygon();
                }
                //Green color
                if (e.getKeyCode() == KeyEvent.VK_G) {
                    setColor(0x00ff00);
                    lineRasterizer.drawLine(line);
                    fillPolygon();
                }
                //Blue color
                if (e.getKeyCode() == KeyEvent.VK_B) {
                    setColor(0x0000ff);
                    lineRasterizer.drawLine(line);
                    fillPolygon();
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

                //Fill polygon mode
                if (e.getKeyCode() == KeyEvent.VK_F) {
                    if ((mode == 2 || mode == 4) && !filledPolygon) {
                        filledPolygon = true;
                        scanLine.fillPolygon(polygon);
                        repaint();
                    } else if ((mode == 2 || mode == 4) && filledPolygon) {
                        filledPolygon = false;
                        panel.clear();
                        polygonRasterizer.rasterize(polygon);
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

                //Pentagon mode
                if (e.getKeyCode() == KeyEvent.VK_4) {
                    if (mode != 4) {
                        clear();
                        lineRasterizer = lineRasterizer1;
                        polygonRasterizer.setLineRasterizer(lineRasterizer1);
                        mode = 4;
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

    private void fillPolygon() {
        if (filledPolygon) scanLine.fillPolygon(polygon);
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

    //Clear everything
    private void clear() {
        polygon.clear();
        lineList.clear();
        panel.clear();
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
