/*
 * Project:     Rover-Navigator
 * Description: Science Olympiad Rover Navigator Scoring Program
 * 
 * File:        motion/TestResult.java
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
public class TestResult {
    public String id;
    public boolean completed;
    public double score;
    public double distError;
    
    public Point2D.Double point;
    
    public TestResult() {
        clear();
    }
    
    public void clear() {
        id = "";
        completed = false;
        score = 0.0;
        distError = 0.0;
        point = new Point2D.Double();
    }
}
