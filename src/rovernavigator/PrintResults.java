/*
 * Project:     Rover-Navigator
 * Description: Science Olympiad Rover Navigator Scoring Program
 * 
 * File:        PrintResults.java
 * 
 * Created:     January 2014
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


public class PrintResults extends PrintOut implements Printable {
    
    RoverNavigator main;
   
    private static Font fnt = new Font("Helvetica",Font.PLAIN,24);     
    
    public PrintResults(RoverNavigator main) {
        this.main = main;
    }
    
    public int print(Graphics _g, PageFormat pf, int pageIndex) 
      throws PrinterException  
      {      
        // pageIndex 0 to 4 corresponds to page numbers 1 to 5. 
        if (pageIndex != 0) return Printable.NO_SUCH_PAGE; 

        MapDraw mapDraw = new MapDraw();
        mapDraw.setMain(main);

        _g.setColor(Color.black);

        Graphics2D g = (Graphics2D) _g;
        
        double width = pf.getWidth();
        printText(g,"Rover Navigator Results",32, (int) (width / 2.0),20,TEXT_CENTER,TEXT_TOP);
        
        Graphics2D gmap = (Graphics2D) g.create();
        gmap.translate(0.5 * DPI, 100.0);
        gmap.scale(0.4,0.4);
        mapDraw.printable();
        mapDraw.redrawMap(gmap, 800, 800);
       
        DecimalFormat formatDistance = new DecimalFormat("0.000");
        DecimalFormat formatDegrees = new DecimalFormat("0.0");
        DecimalFormat formatInteger = new DecimalFormat("0");

        g.setColor(Color.black);
        double x = 5.5 * DPI;
        double col2 = 1.85 * DPI;
        double y = 2.0 * DPI;
        double linespacing = 0.2 * DPI;
        
        int fontSize = 10;
        
        printText(g,"Total Distance (m) =",fontSize,x,y);
        printText(g,formatDistance.format(main.motionPath.getTravelDistance()),fontSize,x+col2,y);
        y += linespacing;
        printText(g,"Total Degrees =",fontSize,x,y);
        printText(g,formatDegrees.format(main.motionPath.getTravelDegrees()),fontSize,x+col2,y);
        y += linespacing;
        printText(g,"Total Error (m) =",fontSize,x,y);
        printText(g,formatDistance.format(main.motionPath.getExperimentError()),fontSize,x+col2,y);
        y += linespacing;
        printText(g,"Average Error (m) =",fontSize,x,y);
        printText(g,formatDistance.format(main.motionPath.getExperimentAvgError()),fontSize,x+col2,y);

        y += linespacing * 1.5;
        printText(g,"Path Score =",fontSize,x,y);
        printText(g,formatDistance.format(main.motionPath.getPathScore()),fontSize,x+col2,y);

        y += linespacing * 1.5;
        printText(g,"# of Commands =",fontSize,x,y);
        printText(g,formatInteger.format(main.commands.getCount()),fontSize,x+col2,y);

        y += linespacing;
        printText(g,"# of Hazard Hits =",fontSize,x,y);
        if (main.motionPath.hazardIntersects > 0) {
            g.setColor(Color.red);
            printText(g,formatInteger.format(main.motionPath.hazardIntersects),fontSize,x+col2,y);
        } else {
            g.setColor(new Color(0x00,0xb0,0x00));
            printText(g,"None",fontSize,x+col2,y);
        }
        g.setColor(Color.black);

        y += linespacing;
        printText(g,"Exit Map =",fontSize,x,y);
        if (main.motionPath.exitMapArea > 0) {
            g.setColor(Color.red);
            String exitMap = "YES (" + formatInteger.format(main.motionPath.exitMapArea) + ")";
            printText(g,exitMap,fontSize,x+col2,y);
        } else {
            g.setColor(new Color(0x00,0xb0,0x00));
            printText(g,"No",fontSize,x+col2,y);
        } 
        
        g.setColor(Color.black);
        double colA = 0.625 * DPI;
        double colB = 1.6 * DPI;
        
        y += linespacing * 2;
        printText(g,"ID",fontSize + 2,x,y);
        printText(g,"Completed",fontSize + 2,x + colA,y);
        printText(g,"Error (m)",fontSize + 2,x + colB,y);

        y += linespacing * 1.1;
        
        int e = 0;
        while (e < main.motionPath.experiments) {
            e++;
            printText(g,main.motionPath.testResult[e].id,fontSize,x,y);
            String comp = "No";
            if (main.motionPath.testResult[e].completed) comp = "Yes";
            printText(g,comp,fontSize,x + colA,y);
            printText(g,formatDistance.format(main.motionPath.testResult[e].distError),fontSize,x + colB,y);
            y += linespacing;
        }
        if (e == 0) {
            printText(g,"No",fontSize + 2,x,y);
            printText(g,"Experiments",fontSize + 2,x + colA,y);
            printText(g,"Found",fontSize + 2,x + colB,y);
        }
        
        return Printable.PAGE_EXISTS;    
      }
}
