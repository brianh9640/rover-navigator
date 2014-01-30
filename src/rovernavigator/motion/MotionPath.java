/*
 * Project:     Rover-Navigator
 * Description: Science Olympiad Rover Navigator Scoring Program
 * 
 * File:        motion/MotionPath.java
 * 
 * Created:     June 2013
 * 
 * Repository:  https://github.com/brianh9640/rover-navigator
 * 
 */
package rovernavigator.motion;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import rovernavigator.RoverNavigator;
import rovernavigator.map.MapDef;
import rovernavigator.map.MapExperiment;
import rovernavigator.map.MapHazard;
import rovernavigator.map.MapRover;

/**
 *
 * @author Brian
 */
public class MotionPath {

    public static final boolean DEBUG = false;
    
    public static final int MAX_PATH_POINTS         = 500;
    public static final int MAX_INTERSECT_POINTS    = 40;
    
    RoverNavigator main;
    
    public int points;
    public MapRover point[];
    MapRover rover;
    
    public int experiments;
    public TestResult testResult[];
    
    public int hazardIntersects;
    public HazardIntersect  hazardIntersect[];
    public int exitMapArea;
    
    
    protected double travelDistance;
    protected double travelDegrees;
    protected double experimentError;
    protected double experimentAvgError;
    protected double pathScore;

    public MotionPath() {
        clear();
    }

    public final void clear() {
        clearPath();
        clearExperiments();
        clearHazardIntersects();
        exitMapArea = 0;
    }

    public void setMain(RoverNavigator main) { this.main = main; }
    
    public double getTravelDistance() { return travelDistance; }
    public double getTravelDegrees() { return travelDegrees; }
    public double getExperimentError() { return experimentError; }
    public double getExperimentAvgError() { return experimentAvgError; }
    public double getPathScore() { return pathScore; }
    
    public void clearPath() {
        points = 0;
        point = new MapRover[MAX_PATH_POINTS+1];
        rover = new MapRover();
        
        travelDistance = 0.0;
        travelDegrees  = 0.0;
        experimentError = 0.0;
        experimentAvgError = 0.0;
        pathScore = 0.0;
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
                    testResult[e].id = main.map.experiment[e].id;
                    testResult[e].distError = main.map.getMaxDimension();
                }
            }
        }
        experimentError = 0.0;
    }

    public TestResult getTestResult(String id) {
        int e = 0;
        while (e < experiments) {
            e++;
            if (testResult[e].id.equalsIgnoreCase(id)) return testResult[e];
        }
        
        return null;
    }
    
    public int findTestResult(String id) {
        int e = 0;
        while (e < experiments) {
            e++;
            if (testResult[e].id.equalsIgnoreCase(id)) return e;
        }
        
        return 0;
    }
    
    public void clearHazardIntersects() {
        hazardIntersects = 0;
        hazardIntersect = new HazardIntersect[MAX_INTERSECT_POINTS + 1];
    }
    
    public void updatePath() {
        clear();
        calcPath();
        
        mapAreaCheck();
        hazardCheck();
        
        calcPathScore();
    }
    
    public void calcPath() {

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
                        
                        int e = findTestResult(experiment.id);
                        testResult[e].id = experiment.id;
                        testResult[e].point.x = rover.point.x;
                        testResult[e].point.y = rover.point.y;
                        testResult[e].distError = experiment.point.distance(rover.point.x, rover.point.y);
                        testResult[e].completed = true;
                        
//                        point[points].testID = experiment.id;
//                        point[points].testError = experiment.point.distance(rover.point.x, rover.point.y);
//                        System.out.println(experiment.id + " Error = " + point[points].testError);
//                        System.out.println(experiment.point.toString());
//                        System.out.println(rover.point.toString());
                        experimentError += testResult[e].distError;
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
        
        clearHazardIntersects();
        
        if (points < 2) return;
        
        int p = 1;
        while (p < points) {
            if (point[p].point.distance(point[p+1].point) > 0.0) {
                hazardCheckLine(p,point[p].point,point[p+1].point);
            }
            
            p++;
        }
    }

    private void hazardCheckLine(int p,Point2D.Double p1,Point2D.Double p2) {   
        int h = 0;
        Point2D.Double pointIntersect = null;
        
        Line2D.Double lineR = new Line2D.Double();
        lineR.setLine(p1, p2);
        while (h < main.map.hazards) {
            h++;
            MapHazard hz = main.map.getHazard(h);
            if (hz != null) {
                if (hz.shape == MapHazard.SHAPE_RECTANGLE) {
                    Point2D.Double hp1 = new Point2D.Double();
                    Point2D.Double hp2 = new Point2D.Double();
                    hp1.x = hz.point[1].x;
                    hp1.y = hz.point[1].y;
                    hp2.x = hz.point[2].x;
                    hp2.y = hz.point[2].y;
//                    if (hp1.x > hp2.x) {
//                        double tmp = hp2.x;
//                        hp2.x = hp1.x;
//                        hp1.x = tmp;
//                    }
//                    if (hp1.y > hp2.y) {
//                        double tmp = hp2.y;
//                        hp2.y = hp1.y;
//                        hp1.y = tmp;
//                    }
                    
                    Line2D.Double lineH = new Line2D.Double();
                    lineH.x1 = hp1.x;
                    lineH.y1 = hp1.y;
                    lineH.x2 = hp1.x;
                    lineH.y2 = hp2.y;
                    pointIntersect = getIntersectionPoint(lineR,lineH);
                    if (pointIntersect != null) {
                        hazardAddIntersect(p,h,pointIntersect);
                    }
                    
                    lineH.x1 = hp1.x;
                    lineH.y1 = hp2.y;
                    lineH.x2 = hp2.x;
                    lineH.y2 = hp2.y;
                    pointIntersect = getIntersectionPoint(lineR,lineH);
                    if (pointIntersect != null) {
                        hazardAddIntersect(p,h,pointIntersect);
                    }
                                        
                    lineH.x1 = hp2.x;
                    lineH.y1 = hp2.y;
                    lineH.x2 = hp2.x;
                    lineH.y2 = hp1.y;
                    pointIntersect = getIntersectionPoint(lineR,lineH);
                    if (pointIntersect != null) {
                        hazardAddIntersect(p,h,pointIntersect);
                    }
                                        
                    lineH.x1 = hp2.x;
                    lineH.y1 = hp1.y;
                    lineH.x2 = hp1.x;
                    lineH.y2 = hp1.y;
                    pointIntersect = getIntersectionPoint(lineR,lineH);
                    if (pointIntersect != null) {
                        hazardAddIntersect(p,h,pointIntersect);
                    }
                    
                }
                if (hz.shape == MapHazard.SHAPE_CIRCLE) {
                    Circle circle = new Circle();
                    circle.x = hz.point[1].x;
                    circle.y = hz.point[1].y;
                    circle.radius = hz.radius;
                    getCircleLineIntersect(p,h,lineR,circle);
                }
            }
        }
        
    }
    
    private Point2D.Double getIntersectionPoint(Line2D.Double lineA, Line2D.Double lineB) {
        Point2D.Double p = null;
        if (!lineA.intersectsLine(lineB)) return p;
        
        double d = (lineA.x1 - lineA.x2) * (lineB.y1 - lineB.y2) - (lineA.y1 - lineA.y2) * (lineB.x1 - lineB.x2);
        if (d != 0) {
            double xi = ((lineB.x1 - lineB.x2) * (lineA.x1 * lineA.y2 - lineA.y1 * lineA.x2) - (lineA.x1 - lineA.x2) * (lineB.x1 * lineB.y2 - lineB.y1 * lineB.x2)) / d;
            double yi = ((lineB.y1 - lineB.y2) * (lineA.x1 * lineA.y2 - lineA.y1 * lineA.x2) - (lineA.y1 - lineA.y2) * (lineB.x1 * lineB.y2 - lineB.y1 * lineB.x2)) / d;

            p = new Point2D.Double(xi, yi);

        }
        return p;
    }
    
    private void getCircleLineIntersect(int p, int h,Line2D.Double line,Circle circle) {

        double baX = line.x2 - line.x1;
        double baY = line.y2 - line.y1;
        double caX = circle.x - line.x1;
        double caY = circle.y - line.y1;
        
        double a = baX * baX + baY * baY;
        double bBy2 = baX * caX + baY * caY;
        double c = caX * caX + caY * caY - circle.radius * circle.radius;
        
        double pBy2 = bBy2 / a;
        double q = c / a;
        
        double disc = pBy2 * pBy2 - q;
        if (disc < 0) return;  // no intersect
        //System.out.println("Circle p=" + p);
        //System.out.println("...disc = " + disc);
        
        double tmpSqrt = Math.sqrt(disc);
        double abScalingFactor1 = -pBy2 + tmpSqrt;
        double abScalingFactor2 = -pBy2 - tmpSqrt;
        double segDist;
        
        Point2D.Double pt = new Point2D.Double();
        pt.x = line.x1 - baX * abScalingFactor1;
        pt.y = line.y1 - baY * abScalingFactor1;
        //System.out.println("...pt1  x=" + pt.x + "  y=" + pt.y);
        segDist = line.ptSegDist(pt);
        //System.out.println("segDist = " + segDist);
        if (segDist <= 0.0001) hazardAddIntersect(p,h,pt);
        if (disc == 0) return;
        
        pt = new Point2D.Double();
        pt.x = line.x1 - baX * abScalingFactor2;
        pt.y = line.y1 - baY * abScalingFactor2;
        //System.out.println("...pt2  x=" + pt.x + "  y=" + pt.y);
        
        segDist = line.ptSegDist(pt);
        //System.out.println("segDist = " + segDist);
        if (segDist <= 0.0001) hazardAddIntersect(p,h,pt);
        
     }

    static class Circle {
        public double x, y;
        public double radius;

        public Circle(double x, double y, double radius) { this.x = x; this.y = y; this.radius = radius; }
        public Circle() { x = 0.0; y = 0.0; radius = 0.0; }

        @Override
        public String toString() {
            return "Circle [x=" + x + ", y=" + y + ", r=" +  radius + "] ";
        }
    }    
    
    private void hazardAddIntersect(int p,int h,Point2D.Double pt) {
        if (hazardIntersects < MotionPath.MAX_INTERSECT_POINTS) hazardIntersects++;
        hazardIntersect[hazardIntersects] = new HazardIntersect();
        hazardIntersect[hazardIntersects].hazardN = h;
        hazardIntersect[hazardIntersects].point = pt;
        hazardIntersect[hazardIntersects].pathN = p;
        
        //System.out.println("Hazard Hit : " + h + "  point=" + pt.toString());
    }
    
    private void mapAreaCheck() {
        
        exitMapArea = 0;
        
        if (points < 2) return;
        
        int p = 1;
        while (p < points) {
            if (point[p].point.distance(point[p+1].point) > 0.0) {
                if (pointOutOfMapArea(p,point[p].point)) exitMapArea++;
            }
            
            p++;
        }
    }
    
    private boolean pointOutOfMapArea(int p,Point2D.Double pt) {
        if (pt.x < 0.0) return true;
        if (pt.y < 0.0) return true;
        if (pt.x > main.map.width) return true;
        if (pt.y > main.map.height) return true;
        return false;
    }
    private void calcPathScore() {
        pathScore = 0.0;
        
        experimentError = 0.0;
        experimentAvgError = 0.0;
        
        if (experiments == 0) return;
        
        int e = 0;
        while (e < experiments) {
            e++;
            experimentError += testResult[e].distError;
        }
        
        experimentAvgError = experimentError / (double) experiments;
        
        pathScore = this.travelDistance
                  + this.travelDegrees / 10.0
                  + this.experimentError * 10.0;
    }
}
