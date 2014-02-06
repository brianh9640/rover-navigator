/*
 * Project:     Rover-Navigator
 * Description: Science Olympiad Rover Navigator Scoring Program
 * 
 * File:        MapDraw.java
 * 
 * Created:     February 2014
 * 
 * Repository:  https://github.com/brianh9640/rover-navigator
 * 
 */
package rovernavigator;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.text.DecimalFormat;
import static rovernavigator.PrintOut.DPI;
import static rovernavigator.PrintOut.TEXT_CENTER;
import static rovernavigator.PrintOut.TEXT_TOP;

public class PrintMap extends PrintOut implements Printable {

    RoverNavigator main;
   
    private static Font fnt = new Font("Helvetica",Font.PLAIN,24);     
    
    public PrintMap(RoverNavigator main) {
        this.main = main;
    }
    
    public int print(Graphics _g, PageFormat pf, int pageIndex) 
      throws PrinterException  
      {      
        if (pageIndex != 0) return Printable.NO_SUCH_PAGE; 

        MapDraw mapDraw = new MapDraw();
        mapDraw.setMain(main);
        mapDraw.setShowRover(false);
        mapDraw.setShowRoverLocator(true);
        mapDraw.setShowRoverPath(false);
        mapDraw.setShowRoverStart(true);
        mapDraw.setShowHazardHits(false);
        
        mapDraw.setShowGridTicks(true);
        
        _g.setColor(Color.black);

        Graphics2D g = (Graphics2D) _g;
        
        double width = pf.getWidth();
        printText(g,"Rover Navigator - " + main.map.name,20, (int) (width / 2.0),20,TEXT_CENTER,TEXT_TOP);
        
        int mapWidth = 1800;
        double marginLeft = 0.25 * DPI;
        double pageWidth = pf.getImageableWidth() - marginLeft*2;
        double scale = pageWidth / mapWidth;
        
        Graphics2D gmap = (Graphics2D) g.create();
        gmap.translate(pf.getImageableX() + marginLeft, 0.75 * DPI);
        gmap.scale(scale,scale);
        mapDraw.printableMap();
        mapDraw.redrawMap(gmap, mapWidth, mapWidth);
        
        printExperiments(g,pf);
        
        return Printable.PAGE_EXISTS;    
      }
    
    private void printExperiments(Graphics2D g, PageFormat pf) {
        String txt;
        DecimalFormat formatPoint = new DecimalFormat("0.0");
        
        double colW = pf.getWidth() / 7.0;
        double spacing = 18;
        double startY = 9.0 * DPI;
        
        int maxCols = 4;
        double x = colW * ((double) maxCols / 2.0 + 0.5);
        double y;
        printText(g,"EXPERIMENTS",14, x ,startY,TEXT_CENTER,TEXT_TOP);
        
        int e = 0;
        while (e < main.map.experiments) {
            e++;
      
            x = colW * (1 + (e - 1) % maxCols);
            y = startY + spacing * (1 + (e-1) / maxCols);
            txt = main.map.experiment[e].id + " (" 
                    + formatPoint.format(main.map.experiment[e].point.x)
                    + ","
                    + formatPoint.format(main.map.experiment[e].point.y)
                    + ")";
            printText(g,txt,12,x,y,TEXT_CENTER,TEXT_TOP);        
        }
        
        x = colW * 6;
        y = startY;
        printText(g,"ROVER START",14, x ,y,TEXT_CENTER,TEXT_TOP);
        y += spacing;
        txt = "X : " + formatPoint.format(main.map.roverStart.point.x);
        printText(g,txt,12,x,y,TEXT_CENTER,TEXT_TOP);
        
        y += spacing;
        txt = "Y : " + formatPoint.format(main.map.roverStart.point.y);
        printText(g,txt,12,x,y,TEXT_CENTER,TEXT_TOP);
        
        y += spacing;
        txt = "Heading: " 
                + formatPoint.format(main.map.roverStart.heading);
        printText(g,txt,12,x,y,TEXT_CENTER,TEXT_TOP);
    }
    
}
