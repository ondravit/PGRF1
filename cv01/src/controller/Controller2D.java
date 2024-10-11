package controller;

import model.Line;
import model.Point;
import rasterize.LineRasterizer;
import rasterize.LineRasterizerGraphics;
import rasterize.LineRasterizerTrivial;
import view.Panel;

import java.awt.*;
import java.awt.event.*;

public class Controller2D {
    private final Panel panel;
    private LineRasterizer lineRasterizer;
    Line line;
    Point point;
    Polygon polygon;

    public Controller2D(Panel panel) {
        this.panel = panel;

        //lineRasterizer = new LineRasterizerGraphics(panel.getRaster());
        lineRasterizer = new LineRasterizerTrivial(panel.getRaster());

        //Line line = new Line(panel.getHeight()/2, panel.getWidth()/2, 100, 100);
        //lineRasterizer.drawLine(line);


        initListeners();

    }

    private void initListeners(){
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                polygon = new Polygon();
                polygon.addPoint(e.getX(), e.getY());

                line = new Line(e.getX(), e.getY(), e.getX(), e.getY());
                line.setX1(e.getX());
                line.setY1(e.getY());
            }
        });


        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                panel.clear();

                line.setX2(e.getX());
                line.setY2(e.getY());

                lineRasterizer.drawLine(line);
                panel.repaint();
            }
        });
    }
}
