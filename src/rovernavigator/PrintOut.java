/*
 * Project:     Rover-Navigator
 * Description: Science Olympiad Rover Navigator Scoring Program
 * 
 * File:        PrintOut.java
 * 
 * Created:     January 2014
 * 
 * Repository:  https://github.com/brianh9640/rover-navigator
 * 
 */
package rovernavigator;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class PrintOut {

    public static final double DPI                   = 72.0;
    
    public static final int TEXT_LEFT                   = 0;
    public static final int TEXT_CENTER                 = 1;
    public static final int TEXT_RIGHT                  = 2;
    
    public static final int TEXT_TOP                    = 0;
    public static final int TEXT_MIDDLE                 = 1;
    public static final int TEXT_BOTTOM                 = 2;
    

    public void printText(Graphics2D g,String text,int size,double x,double y) {
        printText(g,text,size,x,y,TEXT_LEFT,TEXT_TOP);
    }

    public void printText(Graphics2D g,String text,int size,double x,double y,int hpos,int vpos) {
        Font font = new Font("Arial",Font.PLAIN, size);
        g.setFont(font);

        AffineTransform affineTransform = new AffineTransform();     
        FontRenderContext frc = new FontRenderContext(affineTransform,true,true);     
        int textWidth = (int)(font.getStringBounds(text, frc).getWidth());
        int textHeight = (int)(font.getStringBounds(text, frc).getHeight());        

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
        
        g.drawString(text, (float) x, (float) y);
    }
    
}
