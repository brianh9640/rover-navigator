/*
 * Project:     Rover-Navigator
 * Description: Science Olympiad Rover Navigator Scoring Program
 * 
 * File:        map/MapHazard.java
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
public class MapHazard {
    
    public static final int TYPE_AVOID              = 0;
    
    public static final int SHAPE_RECTANGLE         = 0;
    public static final int SHAPE_CIRCLE            = 1;
    
    public String id;
    public int type;
    public int shape;
    
    public int points;
    public Point2D.Double point[];
    public double radius;
    
    public MapHazard() {
        clear();
    }
    
    public void clear() {
        id = "";
        type = TYPE_AVOID;
        shape = SHAPE_RECTANGLE;
        
        points = 0;
        point = new Point2D.Double[10];
        radius = 0.0;
    }
}
