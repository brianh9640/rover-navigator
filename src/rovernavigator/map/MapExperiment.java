/*
 * Project:     Rover-Navigator
 * Description: Science Olympiad Rover Navigator Scoring Program
 * 
 * File:        map/MapExperiment.java
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
public class MapExperiment {
    public String id;
    public String name;
    public int type;
    public double score;
    
    public Point2D.Double point;
    
    public MapExperiment() {
        clear();
    }
    
    public void clear() {
        id = "";
        name = "";
        type = 0;
        score = 1.0;
        point = new Point2D.Double();
    }
    
}
