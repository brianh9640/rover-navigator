/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rovernavigator.map;

import java.awt.geom.Point2D;

/**
 *
 * @author Brian
 */
public class MapRover {
    public Point2D.Double point;
    public double heading;
    public int flag;
    public String testID;
    public double testError;
    
    public MapRover() {
        clear();
    }
    
    public final void clear() {
        point = new Point2D.Double();
        heading = 0.0;
        flag = 0;
        testID = "";
        testError = 0.0;
    }
    
}
