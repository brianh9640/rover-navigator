/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rovernavigator.motion;

import rovernavigator.RoverNavigator;
import rovernavigator.map.MapDef;
import rovernavigator.map.MapExperiment;
import rovernavigator.map.MapRover;

/**
 *
 * @author Brian
 */
public class MotionPath {

    public static final boolean DEBUG = false;
    
    public static final int MAX_PATH_POINTS         = 500;
    
    RoverNavigator main;
    
    public int points;
    public MapRover point[];
    MapRover rover;
    
    public int experiments;
    public TestResult testResult[];
    
    protected double travelDistance;
    protected double travelDegrees;
    protected double experimentError;

    public MotionPath() {
        clear();
    }

    public final void clear() {
        clearPath();
        clearExperiments();
    }

    public void setMain(RoverNavigator main) { this.main = main; }
    
    public double getTravelDistance() { return travelDistance; }
    public double getTravelDegrees() { return travelDegrees; }
    public double getExperimentError() { return experimentError; }
    
    public void clearPath() {
        points = 0;
        point = new MapRover[MAX_PATH_POINTS+1];
        rover = new MapRover();
        
        travelDistance = 0.0;
        travelDegrees  = 0.0;
    }

    public void clearExperiments() {
        experiments = 0;
        testResult = new TestResult[MapDef.MAX_EXPERIMENTS + 1];        
        if (main != null) {
            if (main.map != null) {
                experiments = main.map.experiments;
                int e = 0;
                while (e < experiments) {
                    e++;
                    testResult[e] = new TestResult();
                    testResult[e].distError = main.map.getMaxDimension();
                }
            }
        }
        experimentError = 0.0;
    }
    
    public void updatePath() {
        calcPath();
        
        hazardCheck();
    }
    
    public void calcPath() {
        clearPath();

        if (main.map == null) return;
        if (main.commands == null) return;

        if (main.commands.count == 0) return;

        rover = new MapRover();
        rover.point.x = main.map.roverStart.point.x;
        rover.point.y = main.map.roverStart.point.y;
        rover.heading = main.map.roverStart.heading;

        points = 0;
        roverNewPoint();

        int c = 0;
        while (c < main.commands.count) {
            c++;

            switch (main.commands.type[c]) {
                case CommandList.COMMAND_LEFT :
                    roverLeft(main.commands.value[c]);
                    travelDegrees += main.commands.value[c];
                    roverNewPoint();
                    break;
                case CommandList.COMMAND_RIGHT :
                    roverRight(main.commands.value[c]);
                    travelDegrees += main.commands.value[c];
                    roverNewPoint();
                    break;
                case CommandList.COMMAND_FORWARD :
                    roverForward(main.commands.value[c]);
                    travelDistance += main.commands.value[c];
                    roverNewPoint();
                    break;
                case CommandList.COMMAND_TEST :
                    MapExperiment experiment = main.map.getExperiment(main.commands.note[c]);
                    if (experiment != null) {
                        point[points].testID = experiment.id;
                        point[points].testError = experiment.point.distance(rover.point.x, rover.point.y);
                        System.out.println(experiment.id + " Error = " + point[points].testError);
                        experimentError += point[points].testError;
                    }
                    break;
            }
        }
        //System.out.println("motion path calced points = " + points);

    }
    
    private void roverLeft(double value) {
        if (value > 360.0) value = value % 360.0;
        rover.heading -= value;
        if (rover.heading < 0.0) rover.heading += 360.0;
    }
    
    private void roverRight(double value) {
        if (value > 360.0) value = value % 360.0;
        rover.heading += value;
        if (rover.heading >= 360.0) rover.heading -= 360.0;
    }
    
    private void roverForward(double distance) {
        roverDrive(distance);
    }
    
    private void roverDrive(double distance) {
        
        
        //         0
        //     IV  |  I
        //         |
        // 270-----|-----90
        //         |
        //    III  |  II
        //        180
        //
        //  I   : dx = sin(A) * distance;
        //        dy = cos(A) * distance;
        //
        //  II  : dx = cos(A-90) * distance;
        //        dy = sin(A-90) * distance * -1;
        //
        //  III : dx = sin(A-180) * distance * -1;
        //        dy = cos(A-180) * distance * -1;
        //
        //  IV  : dx = cos(A-270) * distance * -1;
        //        dy = sin(A-270) * distance;
        //
        
        double dx = 0.0;
        double dy = 0.0;
        
        int quad = 0;
        double angle = rover.heading;
        if ( 0.0 <= angle && angle <= 90.0) quad = 1;
        if (90.0  < angle && angle <= 180.0) quad = 2;
        if (180.0 < angle && angle <= 270.0) quad = 3;
        if (270.0 < angle && angle <= 360.0) quad = 4;
        
        switch (quad) {
            case 1:
                angle = Math.toRadians(angle);
                dx = Math.sin(angle) * distance;
                dy = Math.cos(angle) * distance;
                break;
            case 2:
                angle = Math.toRadians(angle-90.0);
                dx = Math.cos(angle) * distance;
                dy = Math.sin(angle) * distance * -1.0;
                break;
            case 3:
                angle = Math.toRadians(angle-180.0);
                dx = Math.sin(angle) * distance * -1.0;
                dy = Math.cos(angle) * distance * -1.0;
                break;
            case 4:
                angle = Math.toRadians(angle-270.0);
                dx = Math.cos(angle) * distance * -1.0;
                dy = Math.sin(angle) * distance;
                break;
        }
        
        rover.point.x += dx;
        rover.point.y += dy;
        
    }
    
    private void roverNewPoint() {
        points++;
        point[points] = new MapRover();
        point[points].point.x = rover.point.x;
        point[points].point.y = rover.point.y;
        point[points].heading = rover.heading; 
        
        if (DEBUG) {
            System.out.print("Path [" + points + "]:");
            System.out.print(" Heading = " + rover.heading);
            System.out.print(" : X = " + rover.point.x);
            System.out.print(" : Y = " + rover.point.y);
            System.out.println();
        }
    }
    
    private void hazardCheck() {
        
        
//        if (points < 2) return;
//        
//        int p = 1;
//        while (p < points) {
//            p++;
//        }        
        
    }
    
    
}
