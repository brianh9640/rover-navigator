/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
