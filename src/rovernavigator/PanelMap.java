/*
 * Project:     Rover-Navigator
 * Description: Science Olympiad Rover Navigator Scoring Program
 * 
 * File:        PanelMap.java
 * 
 * Created:     June 2013
 * 
 * Repository:  https://github.com/brianh9640/rover-navigator
 * 
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
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import javax.swing.JPanel;
import rovernavigator.map.MapExperiment;
import rovernavigator.map.MapRover;
import rovernavigator.motion.TestResult;

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
        Dimension size = this.getSize();
        mapDraw.setShowRover(main.showRover);
        mapDraw.setShowRoverPath(main.showRoverPath);
        mapDraw.setShowRoverStart(main.showRoverStart);
        mapDraw.setShowHazardHits(main.showHazardHits);
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
        
        clearToolTip();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        pointMouseLast = null;
        DecimalFormat formatDistance = new DecimalFormat("#.0");
        DecimalFormat formatError = new DecimalFormat("0.000");

        if (main == null) return;
        if (main.map == null) return;
                
        Point2D.Double pointCursor = new Point2D.Double();
        
        pointCursor.x = mapDraw.getRealX(e.getX());
        pointCursor.y = mapDraw.getRealY(e.getY());
        
        int ex = 0;
        while (ex < main.map.experiments) {
            ex++;
        
            MapExperiment test = main.map.experiment[ex];
            if (test.point.distance(pointCursor) < 2.0) {
                String msg;
                msg = "Test " + test.id + "\nExper X:" + formatError.format(test.point.x) + " Y:" + formatError.format(test.point.y);
                TestResult result = main.motionPath.getTestResult(test.id);
                if (result != null) {
                    if (result.completed) {
                        msg += "\nRover X:" + formatError.format(result.point.x) + " Y:" + formatError.format(result.point.y);
                        msg += "\nError = " + formatError.format(result.distError);
                    }
                }
                mapDraw.defineToolTip(e.getX(),e.getY(),msg);
                repaint();
                return;
            }
            
        }

        int p = 0;
        while (p < main.motionPath.points) {
            p++;
        
            MapRover rover = main.motionPath.point[p];
            if (rover.point.distance(pointCursor) < 2.0) {
                String msg;
                msg = "Rover Point " + p + "\nX:" + formatError.format(rover.point.x) + " Y:" + formatError.format(rover.point.y);
                msg += "\nHeading: " + formatDistance.format(rover.heading);
                mapDraw.defineToolTip(e.getX(),e.getY(),msg);
                repaint();
                return;
            }
            
        }
        
        clearToolTip();
    }
    
    public void defineToolTip(int x, int y, String msg) {
        mapDraw.defineToolTip(x,y,msg);
        repaint();
    }
    public void clearToolTip() {
        if (mapDraw.toolShow) {
            mapDraw.clearToolTip();
            repaint();
        }
    }
    
}
