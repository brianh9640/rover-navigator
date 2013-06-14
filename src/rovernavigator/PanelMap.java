/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rovernavigator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JPanel;

/**
 *
 * @author Brian
 */
public class PanelMap extends JPanel implements MouseWheelListener,MouseMotionListener {

    MapDraw mapDraw;

    RoverNavigator main;

    protected Point pointMouseLast;
    protected boolean mouseClicked;

    public PanelMap() {
        main = null;
        pointMouseLast = new Point();
        mouseClicked = false;

        mapDraw = new MapDraw();
        this.setBackground(java.awt.Color.WHITE);
        addMouseWheelListener(this);
        addMouseMotionListener(this);
    }

    public void setMain(RoverNavigator main) { this.main = main; mapDraw.setMain(main); }

    public void zoomIn() {
        mapDraw.zoomScale(mapDraw.zoomScale() * 1.5);
        repaint();
    }

    public void zoomOut() {
        mapDraw.zoomScale(mapDraw.zoomScale() * 0.75);
        repaint();
    }

    public void zoomReset() {
        mapDraw.zoomScale(1.0);
        mapDraw.panReset();
        repaint();
    }

    public void pan(int dx,int dy) {
        mapDraw.pan(dx, dy);
        repaint();
    }
    
    public void clearPanel(Graphics g) {
        g.setColor(Color.WHITE);
        Dimension size = this.getSize();
        g.fillRect(0, 0, size.width, size.height);


    }

    @Override
    public void paintComponent(Graphics g) {
       super.paintComponent(g);

        clearPanel(g);
        if (main.map == null) return;
        Rectangle size = g.getClipBounds();
        mapDraw.redrawMap(g,size.width,size.height);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
        if (notches < 0) zoomIn();
        if (notches > 0) zoomOut();
        //System.out.print("X = " + e.getX());
        //System.out.println("   Y = " + e.getY());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (pointMouseLast == null) {
            pointMouseLast = new Point();
            pointMouseLast.x = e.getX();
            pointMouseLast.y = e.getY();
            return;
        }
        int dx = pointMouseLast.x - e.getX();
        int dy = e.getY() - pointMouseLast.y;
        //System.out.println("(dx,dy)=(" + dx + "," + dy + ")");
        
        pan(dx,dy);
        
        pointMouseLast.x = e.getX();
        pointMouseLast.y = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        pointMouseLast = null;
    }

}
