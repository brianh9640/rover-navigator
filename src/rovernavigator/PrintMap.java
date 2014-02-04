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
        printText(g,"Map - " + main.map.name,24, (int) (width / 2.0),20,TEXT_CENTER,TEXT_TOP);
        
        int mapWidth = 1200;
        double marginLeft = 0.25 * DPI;
        double pageWidth = pf.getImageableWidth() - marginLeft;
        double scale = pageWidth / mapWidth;
        
        Graphics2D gmap = (Graphics2D) g.create();
        gmap.translate(pf.getImageableX() + marginLeft, 0.75 * DPI);
        gmap.scale(scale,scale);
        mapDraw.printable();
        mapDraw.redrawMap(gmap, mapWidth, mapWidth);
        
        return Printable.PAGE_EXISTS;    
      }
    
}
