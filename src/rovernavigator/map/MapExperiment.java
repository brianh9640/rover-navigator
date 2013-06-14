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
