/*
 * Project:     Rover-Navigator
 * Description: Science Olympiad Rover Navigator Scoring Program
 * 
 * File:        MapDraw.java
 * 
 * Created:     June 2013
 * 
 * Repository:  https://github.com/brianh9640/rover-navigator
 * 
 */
package rovernavigator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import rovernavigator.map.MapHazard;
import rovernavigator.map.MapRover;

/**
 *
 * @author Brian
 */
public class MapDraw {
    
    public static final int BORDER_MAP                = 20;
    
    public static final int TEXT_LEFT                   = 0;
    public static final int TEXT_CENTER                 = 1;
    public static final int TEXT_RIGHT                  = 2;
    
    public static final int TEXT_TOP                    = 0;
    public static final int TEXT_MIDDLE                 = 1;
    public static final int TEXT_BOTTOM                 = 2;
    
    
    protected int mapX;
    protected int mapY;
    protected int mapWidth;
    protected int mapHeight;
    protected double mapScaleX;
    protected double mapScaleY;
    protected double mapOffsetX;
    protected double mapOffsetY;
    
    protected double zoomScale;
    
    protected Font fontGrid;
    protected Font fontTool;
    protected int  fontSizeGrid;
    protected int  fontSizeTool;
    
    protected int  offsetExp;
    
    protected int areaWidth = 10;
    protected int areaHeight = 10;
    
    protected boolean   toolShow;
    protected int       toolX;
    protected int       toolY;
    protected String    toolMsg;
    
    RoverNavigator main;
    
    public MapDraw() {
        main = null;
        
        fontSizeGrid = 12;
        fontSizeTool = 14;
        offsetExp = 1;
        
        fontGrid = new Font("Arial",Font.PLAIN, fontSizeGrid);
        fontTool = new Font("Arial",Font.PLAIN, fontSizeTool);

        mapX = 0;
        mapY = 0;
        mapWidth = 100;
        mapHeight = 100;
        
        mapScaleX = 1.0;
        mapScaleY = 1.0;
        
        mapOffsetX = 0.0;
        mapOffsetY = 0.0;
        
        zoomScale = 1.0;
        
        toolShow = false;
        
    }
    
    public void setMain(RoverNavigator main) { this.main = main; }
    
    public void printable() {
        fontSizeGrid = 24;
        fontSizeTool = 36;
        
        fontGrid = new Font("Arial",Font.PLAIN, fontSizeGrid);
        fontTool = new Font("Arial",Font.PLAIN, fontSizeTool);
        offsetExp = 4;
        
    }
    
    public double zoomScale() { return zoomScale; }
    public void zoomScale(double newScale) { 
        zoomScale = newScale; 
    }
    
    public void panReset() { mapOffsetX = 0.0; mapOffsetY = 0.0; }
    public void pan(int dx,int dy) {
        double mx = (double) dx / (mapScaleX * zoomScale);
        double my = (double) dy / (mapScaleY * zoomScale);
        
        mapOffsetX += mx;
        mapOffsetY += my;
    }
    
    public void redrawMap(Graphics g,int iw,int ih) {
        areaWidth = iw;
        areaHeight = ih;
        
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, areaWidth, areaHeight);
        
        calcMapScale(g);
        drawHazards(g);
        drawGrid(g);
        
        drawRoverStart(g);
        if (main.showRover) drawMotionRovers(g);
        if (main.showRoverPath) drawMotionPath(g);
        drawExperiments(g);
        
        if (main.showHazardHits) drawHazardIntersects(g);
        
        if (toolShow) drawToolTip(g);
    }    
    
    private void calcMapScale(Graphics g) {
        int xOffset = 0;
        int yOffset = 0;
        
        int mapSize = 0;
        if (areaWidth >= areaHeight) {
            xOffset = (areaWidth - areaHeight) / 2;
            areaWidth = areaHeight;
        }
        if (areaWidth < areaHeight) {
            yOffset = (areaHeight - areaWidth) / 2 * -1;
            areaHeight = areaWidth;
        }
        
        
        mapX = MapDraw.BORDER_MAP + xOffset;
        mapY = MapDraw.BORDER_MAP + yOffset;
        
        mapWidth = areaWidth - mapX * 2;
        mapHeight = areaHeight - mapY * 2;
        mapSize = areaWidth - MapDraw.BORDER_MAP * 2;
        
        mapScaleX = (double) mapSize / main.map.width;
        mapScaleY = (double) mapSize / main.map.height;
        
    }
    
    public void drawRoverStart(Graphics g) {
        if (main.showRoverStart) drawRover(g,main.map.roverStart);
    }
    
    public void drawRover(Graphics g,MapRover rover) {
        /*
         *
         *      4--7--1
         *      |     |
         *      |     |
         *     6|  0  |5
         *      |     |
         *      |     |
         *      3--8--2
         * 
         * 
         */
        
        Point2D.Double rp[] = new Point2D.Double[20];
        
        rp[ 0] = new Point2D.Double( 0.00, 0.00);
        rp[ 1] = new Point2D.Double( 0.75, 1.50);
        rp[ 2] = new Point2D.Double( 0.75,-1.50);
        rp[ 3] = new Point2D.Double(-0.75,-1.50);
        rp[ 4] = new Point2D.Double(-0.75, 1.50);
        rp[ 5] = new Point2D.Double( 0.75, 0.00);
        rp[ 6] = new Point2D.Double(-0.75, 0.00);
        rp[ 7] = new Point2D.Double( 0.00, 1.50);
        rp[ 8] = new Point2D.Double( 0.00,-1.50);

        int pts = 9;
        Graphics2D g2 = (Graphics2D) g;
//        Stroke strokeOrig = g2.getStroke();

//        g2.setStroke(new BasicStroke(1));
        g2.setColor(new Color(0x00,0x00,0x00));
        drawRoverTire(g2,0,rover,-0.75,-0.75);
        drawRoverTire(g2,1,rover, 0.75,-0.75);
        drawRoverTire(g2,0,rover,-0.75, 0.25);
        drawRoverTire(g2,1,rover, 0.75, 0.25);
        drawRoverTire(g2,0,rover,-0.75, 1.50);
        drawRoverTire(g2,1,rover, 0.75, 1.50);

        int p = 0;
        while (p < pts) {
            rp[p].x += rover.point.x;
            rp[p].y += rover.point.y;
            if (p > 0) rp[p] = pointTransform(rp[0],rp[p],rover.heading);
            p++;
        }
        
        g2.setColor(new Color(0xb0,0xb0,0xb0));
        drawLine2Fill(g2,rp[1],rp[4],rp[2],rp[3]);
        
//        g2.setStroke(new BasicStroke(2));
        
        g2.setColor(new Color(0x30,0x30,0x30));
        mapLine(g2,rp[1],rp[2]);
        mapLine(g2,rp[2],rp[3]);
        mapLine(g2,rp[3],rp[4]);
        mapLine(g2,rp[4],rp[1]);
        g2.setColor(new Color(0xff,0xff,0xff));
        mapLine(g2,rp[5],rp[6]);
        mapLine(g2,rp[7],rp[8]);
        mapLine(g2,rp[5],rp[7]);
        mapLine(g2,rp[6],rp[7]);
        
//        g2.setStroke(strokeOrig);
        
    }
    
    public void drawRoverTire(Graphics2D g2,int right,MapRover rover,double xs,double ys) {
        Point2D.Double p1 = new Point2D.Double();
        Point2D.Double p2 = new Point2D.Double();
        Point2D.Double p3 = new Point2D.Double();
        Point2D.Double p4 = new Point2D.Double();

        double x = rover.point.x + xs;
        double y = rover.point.y + ys;
        
        p1.x = x;
        p1.y = y;
        if (right != 0) {
            p2.x = x + 0.50;
            p2.y = y;
            p4.x = x + 0.50;
            p4.y = y - 0.75;
        }
        else {
            p2.x = x - 0.50;
            p2.y = y;
            p4.x = x - 0.50;
            p4.y = y - 0.75;            
        }
        p3.x = x;
        p3.y = y - 0.75;

        p1 = pointTransform(rover.point,p1,rover.heading);
        p2 = pointTransform(rover.point,p2,rover.heading);
        p3 = pointTransform(rover.point,p3,rover.heading);
        p4 = pointTransform(rover.point,p4,rover.heading);

        drawLine2Fill(g2,p1,p2,p3,p4);
//        mapLine(g2,p1,p2);
//        mapLine(g2,p2,p4);
//        mapLine(g2,p4,p3);
                
    }
    
    public Point2D.Double pointTransform(Point2D.Double origin,Point2D.Double oldpt,double angle) {
        Point2D.Double newpt = new Point2D.Double();
        
        double dx = oldpt.x - origin.x;
        double dy = oldpt.y - origin.y;
        
        angle = Math.toRadians(0.0 - angle);
        double rx = dx * Math.cos(angle) - dy * Math.sin(angle);
        double ry = dx * Math.sin(angle) + dy * Math.cos(angle);
        
        newpt.x = origin.x + rx;
        newpt.y = origin.y + ry;
        
        return newpt;
    }
    
    public void drawExperiments(Graphics g) {

        int e = 0;
        while (e < main.map.experiments) {
            e++;
            
            int sx = getMapX(main.map.experiment[e].point.x);
            int sy = getMapY(main.map.experiment[e].point.y);
            int rad = scaleMapX(0.5);
            if (rad < 5) rad = 5;
            
            g.setColor(new Color(0x00,0x90,0x00));
            g.drawLine(sx-rad*2, sy, sx+rad*2, sy);
            g.drawLine(sx, sy-rad*2, sx, sy+rad*2);
            g.drawArc(sx-rad, sy-rad, rad*2, rad*2, 0, 360);
            mapText(g,main.map.experiment[e].id,sx+rad+offsetExp,sy-rad-offsetExp,TEXT_LEFT,TEXT_BOTTOM);
            
        }
        
    }
    
    public void drawHazards(Graphics g) {
        Color colorBorder = Color.BLACK;
        Color colorFill = Color.BLACK;
        
        int h = 0;
        while (h < main.map.hazards) {
            h++;
            
            switch (main.map.hazard[h].type) {
                case MapHazard.TYPE_AVOID :
                    colorBorder = Color.RED;
                    colorFill = new Color(255,225,225);
                    break;
            }
            switch (main.map.hazard[h].shape) {
                case MapHazard.SHAPE_RECTANGLE :
                    if (main.map.hazard[h].points < 2) break;
                    g.setColor(colorFill);
                    mapRectFill(g,main.map.hazard[h].point[1].x,
                                  main.map.hazard[h].point[1].y,
                                  main.map.hazard[h].point[2].x,
                                  main.map.hazard[h].point[2].y);
                    g.setColor(colorBorder);
                    mapRectBox(g,main.map.hazard[h].point[1].x,
                                  main.map.hazard[h].point[1].y,
                                  main.map.hazard[h].point[2].x,
                                  main.map.hazard[h].point[2].y);
                    break;
                case MapHazard.SHAPE_CIRCLE :
                    if (main.map.hazard[h].points < 1) break;
                    g.setColor(colorFill);
                    mapCircleFill(g,main.map.hazard[h].point[1].x,
                                  main.map.hazard[h].point[1].y,
                                  main.map.hazard[h].radius);
                    g.setColor(colorBorder);
                    mapCircle(g,main.map.hazard[h].point[1].x,
                                  main.map.hazard[h].point[1].y,
                                  main.map.hazard[h].radius);
                    break;
            }
            
            
            
        }
    }
    
    public void drawMotionPath(Graphics g) {
        if (main.motionPath == null) return;
        if (main.motionPath.points < 2) return;

        //System.out.println("motion path points = " + motionPath.points);
        
        Graphics2D g2 = (Graphics2D) g;
        
        int p = 1;
        while (p < main.motionPath.points) {
            p++;
            
            if (main.motionPath.point[p].point.distance(main.motionPath.point[p-1].point) > 0) {
                g2.setColor(new Color(0xd0,0x80,0x00));
                Stroke strokeOrig = g2.getStroke();
                g2.setStroke(new BasicStroke(3));                
                mapLine(g2,main.motionPath.point[p-1].point.x,
                          main.motionPath.point[p-1].point.y,
                          main.motionPath.point[p].point.x,
                          main.motionPath.point[p].point.y);
                g2.setStroke(strokeOrig);
            }
        }
    }
    
    public void drawMotionRovers(Graphics g) {
        if (main.motionPath == null) return;
        if (main.motionPath.points < 2) return;

        //System.out.println("motion path points = " + motionPath.points);
        
        Graphics2D g2 = (Graphics2D) g;
        
        int p = 1;
        while (p < main.motionPath.points) {
            p++;
            
            drawRover(g2,main.motionPath.point[p]);
        }
        drawRover(g2,main.motionPath.point[p]);
    }
    
    public void drawHazardIntersects(Graphics g) {
        double x1;
        double y1;
        double x2;
        double y2;
        
        g.setColor(new Color(0xff,0x00,0x00));
        int h = 0;
        while (h < main.motionPath.hazardIntersects) {
            h++;
            x1 = main.motionPath.hazardIntersect[h].point.x - 1.5;
            y1 = main.motionPath.hazardIntersect[h].point.y - 1.5;
            x2 = main.motionPath.hazardIntersect[h].point.x + 1.5;
            y2 = main.motionPath.hazardIntersect[h].point.y + 1.5;

            mapLine(g,3,x1,y1,x2,y2);
            mapLine(g,3,x1,y2,x2,y1);
        }
    }
    
    public void drawGrid(Graphics g) {
        g.setColor(Color.blue);
        
        double x = 10.0;
        while (x < main.map.width) {
            mapLine(g,x,0,x,main.map.height);
            x += 10.0;
        }
        
        double y = 10.0;
        while (y < main.map.height) {
            mapLine(g,0,y,main.map.width,y);
            y += 10.0;
        }
        
        g.setColor(Color.black);
        mapRect(g,0,0,main.map.width,main.map.height);
        
        int px;
        int py;
        g.setColor(Color.black);
 
        String label;
        x = 0.0;
        while (x <= main.map.width) {
            px = getMapX(x);
            py = getMapY(0.0);
            label = new Integer((int) x).toString();
            mapText(g,label,px,py,TEXT_CENTER,TEXT_TOP);
            x += 10.0;
        }
        
        y = 0.0;
        while (y <= main.map.height) {
            px = getMapX(0.0) - 2;
            py = getMapY(y);
            label = new Integer((int) y).toString();
            mapText(g,label,px,py,TEXT_RIGHT,TEXT_MIDDLE);
            y += 10.0;
        }
        
    }
    
    public void mapText(Graphics g,String text,int x,int y) {
        mapText(g,text,x,y,TEXT_LEFT,TEXT_TOP);
    }

    public void mapText(Graphics g,String text,int x,int y,int hpos,int vpos) {
        double fontScale = zoomScale;
        if (fontScale > 1.0) fontScale /= 2.0;
        if (fontScale < 1.0) fontScale = 1.0;
        if (fontScale > 3.0) fontScale = 3.0;
        fontGrid = new Font("Arial",Font.PLAIN, (int) ((double) fontSizeGrid * fontScale));
        g.setFont(fontGrid);

        AffineTransform affineTransform = new AffineTransform();     
        FontRenderContext frc = new FontRenderContext(affineTransform,true,true);     
        int textWidth = (int)(fontGrid.getStringBounds(text, frc).getWidth());
        int textHeight = (int)(fontGrid.getStringBounds(text, frc).getHeight());        

        switch (hpos) {
            case TEXT_LEFT :
                break;
            case TEXT_CENTER :
                x = x - (textWidth/2);
                break;
            case TEXT_RIGHT :
                x = x - textWidth;
                break;
                
        }
        
        switch (vpos) {
            case TEXT_TOP :
                y = y + textHeight;
                break;
            case TEXT_MIDDLE :
                y = y + (textHeight/2);
                break;
            case TEXT_BOTTOM :
                break;
                
        }
        
        g.drawString(text, x, y);
    }
    
    public void mapLine(Graphics g,double x1,double y1,double x2,double y2) {
        int sx1 = getMapX(x1);
        int sy1 = getMapY(y1);
        int sx2 = getMapX(x2);
        int sy2 = getMapY(y2);
        
        g.drawLine(sx1, sy1, sx2, sy2);
        
    }
    
    public void mapLine(Graphics g,Point2D.Double p1,Point2D.Double p2) {
        mapLine(g,p1.x,p1.y,p2.x,p2.y);
    }
    
    public void mapLine(Graphics g,int width,double x1,double y1,double x2,double y2) {
        int sx1 = getMapX(x1);
        int sy1 = getMapY(y1);
        int sx2 = getMapX(x2);
        int sy2 = getMapY(y2);
        
        Graphics2D g2 = (Graphics2D) g;
        Stroke strokeOrig = g2.getStroke();
        g2.setStroke(new BasicStroke(width));
        g2.drawLine(sx1, sy1, sx2, sy2);
        g2.setStroke(strokeOrig);
        
    }

    public void mapRect(Graphics g,double x,double y,double width,double height) {
        int sx = getMapX(x);
        int sy = getMapY(y);
        int sw = scaleMapX(width);
        int sh = scaleMapY(height);
        sy -= sh;
        
        g.drawRect(sx, sy, sw, sh);        
    }
    
    public void mapRectFill(Graphics g,double x1,double y1,double x2,double y2) {
        
        if (x1 > x2) {
            double tmp = x1;
            x1 = x2;
            x2 = tmp;
        }
        if (y1 > y2) {
            double tmp = y1;
            y1 = y2;
            y2 = tmp;
        }
        double width = x2 - x1;
        double height = y2 - y1;
        
        int sx1 = getMapX(x1);
        int sy1 = getMapY(y1);
        int sw = scaleMapX(width);
        int sh = scaleMapY(height);
        sy1 -= sh;

        g.fillRect(sx1, sy1, sw, sh);
    }
    
    public void mapRectBox(Graphics g,double x1,double y1,double x2,double y2) {        
        if (x1 > x2) {
            double tmp = x1;
            x1 = x2;
            x2 = tmp;
        }
        if (y1 > y2) {
            double tmp = y1;
            y1 = y2;
            y2 = tmp;
        }
        double width = x2 - x1;
        double height = y2 - y1;
        
        int sx1 = getMapX(x1);
        int sy1 = getMapY(y1);
        int sw = scaleMapX(width);
        int sh = scaleMapY(height);
        sy1 -= sh;

        g.drawRect(sx1, sy1, sw, sh);
    }
    
    public void mapCircleFill(Graphics g,double x1,double y1,double radius) {
        
        double width = radius * 2.0;
        double height = radius * 2.0;
        
        int sx1 = getMapX(x1);
        int sy1 = getMapY(y1);
        int sw = scaleMapX(width);
        int sh = scaleMapY(height);

        g.fillOval(sx1-sw/2, sy1-sh/2, sw, sh);
    }
    
    public void mapCircle(Graphics g,double x1,double y1,double radius) {        
        double width = radius * 2.0;
        double height = radius * 2.0;
        
        int sx1 = getMapX(x1);
        int sy1 = getMapY(y1);
        int sw = scaleMapX(width);
        int sh = scaleMapY(height);

        g.drawOval(sx1-sw/2, sy1-sh/2, sw, sh);
    }
    
    public int scaleMapX(double x) {
        int sx = (int) (x * this.mapScaleX * zoomScale);
        return sx;
    }
    
    public int scaleMapY(double y) {
        int sy = (int) (y * this.mapScaleY * zoomScale);
        return sy;
    }
    
    public int getMapX(double x) {
        int sx = mapX + (int) ((x - mapOffsetX) * this.mapScaleX * zoomScale);
        return sx;
    }
    
    public int getMapY(double y) {
        int sy = areaHeight - mapY - (int) ((y - mapOffsetY) * this.mapScaleY * zoomScale);
        return sy;
    }
    
    public double getRealX(int x) {
        x -= mapX;
        double mx = (double) x / (mapScaleX * zoomScale);
        mx += mapOffsetX;
        return mx;
    }
    
    public double getRealY(int y) {
        y = areaHeight - mapY - y;
        double my = (double) y / (mapScaleY * zoomScale);
        my += mapOffsetY;
        return my;
    }
    
    public void defineToolTip(int x, int y, String msg) {
        toolShow = true;
        toolX = x;
        toolY = y;
        toolMsg = msg;
        
    }
    
    public void clearToolTip() {
        toolShow = false;
    }
    
    public void drawToolTip(Graphics g) {
        String line[] = toolMsg.split("\n");
        int width = 0;
        int height = 0;
        
        AffineTransform affineTransform = new AffineTransform();     
        FontRenderContext frc = new FontRenderContext(affineTransform,true,true);
        int textHeight = (int)(fontTool.getStringBounds(line[0], frc).getHeight());        
        
        g.setFont(fontTool);

        int n = 0;
        while (n < line.length) {
            int textWidth = (int)(fontTool.getStringBounds(line[n], frc).getWidth());
            if (textWidth > width) width = textWidth;
            height += textHeight;
            n++;
        }
        width += 2;
        
        int newX = toolX - width - 2;
        int newY = toolY - height - 2;
        
        g.setColor(Color.YELLOW);
        g.fillRect(newX, newY, width, height);
        g.setColor(Color.BLACK);
        
        n = 0;
        while (n < line.length) {
            g.drawString(line[n], newX, newY + (n+1)*textHeight - 2);
            n++;
        }
        
    }
    
    protected void drawLine2Fill(Graphics2D g,Point2D.Double mp1,Point2D.Double mp2,Point2D.Double mp3,Point2D.Double mp4) {
        Point p1 = new Point();
        Point p2 = new Point();
        Point p3 = new Point();
        Point p4 = new Point();
        
        p1.x = getMapX(mp1.x);
        p1.y = getMapY(mp1.y);
        p2.x = getMapX(mp2.x);
        p2.y = getMapY(mp2.y);
        double vx1 = p2.x - p1.x;
        double vy1 = p2.y - p1.y;
        double mag1 = Math.sqrt(vx1*vx1 + vy1*vy1);
        vx1 /= mag1;
        vy1 /= mag1;
        
        p3.x = getMapX(mp3.x);
        p3.y = getMapY(mp3.y);
        p4.x = getMapX(mp4.x);
        p4.y = getMapY(mp4.y);
        double vx2 = p4.x - p3.x;
        double vy2 = p4.y - p3.y;
        double mag2 = Math.sqrt(vx2*vx2 + vy2*vy2);
        vx2 /= mag2;
        vy2 /= mag2;

        Stroke strokeOrig = g.getStroke();
        g.setStroke(new BasicStroke(2));
        
        double distance = 0;
        while (distance < mag1) {
            int sx1 = (int) ((double) p1.x + vx1 * (mag1 - distance) );
            int sy1 = (int) ((double) p1.y + vy1 * (mag1 - distance) );
            int sx2 = (int) ((double) p3.x + vx2 * (mag2 - distance) );
            int sy2 = (int) ((double) p3.y + vy2 * (mag2 - distance) );
            

            g.drawLine(sx1, sy1, sx2, sy2);
            distance += 1.0;
        }
        g.setStroke(strokeOrig);
        
    }
    
}
