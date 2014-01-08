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

        _g.setColor(Color.black);
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

//        labelPathScore.setText("Path Score = " + formatDistance.format(main.motionPath.getPathScore()));
//
//        labelCommandCount.setText(formatInteger.format(main.commands.getCount()));
//        
//        if (main.motionPath.hazardIntersects > 0) {
//            labelHazardCount.setForeground(Color.red);
//            labelHazardCount.setText(formatInteger.format(main.motionPath.hazardIntersects));
//        } else {
//            labelHazardCount.setForeground(new Color(0x00,0xb0,0x00));
//            labelHazardCount.setText("None");
//        }
//            
//        String msg = "";
//        msg += "<html>";
//        msg += "<table>";
//        
//        msg += "<tr>";
//        msg += "<td>";
//        msg += "ID";
//        msg += "</td>";
//        msg += "<td>";
//        msg += "Completed";
//        msg += "</td>";
//        msg += "<td>";
//        msg += "Error (m)";
//        msg += "</td>";
//        msg += "</tr>";
//        
//        int e = 0;
//        while (e < main.motionPath.experiments) {
//            e++;
//            msg += "<tr>";
//            msg += "<td>";
//            msg += main.motionPath.testResult[e].id;
//            msg += "</td>";
//            msg += "<td>";
//            if (main.motionPath.testResult[e].completed) msg += "Yes";
//            else msg += "No";
//            msg += "</td>";
//            msg += "<td>";
//            msg += formatDistance.format(main.motionPath.testResult[e].distError);
//            msg += "</td>";
//            msg += "</tr>";
//        }
//        if (e == 0) {
//            msg += "<tr>";
//            msg += "<td>";
//            msg += "No";
//            msg += "</td>";
//            msg += "<td>";
//            msg += "Experiments";
//            msg += "</td>";
//            msg += "<td>";
//            msg += "Found";
//            msg += "</td>";
//            msg += "</tr>";            
//        }
//        
//        msg += "</table>";
//        msg += "</html>";
//        labelTestResults.setText(msg);
        
        
        
        
        
        return Printable.PAGE_EXISTS;    
      }
}
