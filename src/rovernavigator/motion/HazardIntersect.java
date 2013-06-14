/*
 * Project:     Rover-Navigator
 * Description: Science Olympiad Rover Navigator Scoring Program
 * 
 * File:        motion/HazardIntersect.java
 * 
 * Created:     June 2013
 * 
 * Repository:  https://github.com/brianh9640/rover-navigator
 * 
 */
package rovernavigator.motion;

import java.awt.geom.Point2D;

/**
 *
 * @author Brian
 */
public class HazardIntersect {
    
    public int hazardN;
    public int pathN;
    public Point2D.Double point;

    public HazardIntersect() {
        clear();
    }
    
    public void clear() {
        hazardN = 0;
        pathN = 0;
        point = new Point2D.Double();
    }
}
