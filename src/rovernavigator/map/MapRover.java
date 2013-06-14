/*
 * Project:     Rover-Navigator
 * Description: Science Olympiad Rover Navigator Scoring Program
 * 
 * File:        map/MapRover.java
 * 
 * Created:     June 2013
 * 
 * Repository:  https://github.com/brianh9640/rover-navigator
 * 
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
