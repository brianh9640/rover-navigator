/*
 * Project:     Rover-Navigator
 * Description: Science Olympiad Rover Navigator Scoring Program
 * 
 * File:        map/MapDef.java
 * 
 * Created:     June 2013
 * 
 * Repository:  https://github.com/brianh9640/rover-navigator
 * 
 */
package rovernavigator.map;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author Brian
 */
public class MapDef {

    public static final int MAX_EXPERIMENTS     = 100;
    public static final int MAX_HAZARDS         = 100;
    
    public static final String MAP_FILE_EXTENSION    = "xml";
    
    public String  name;
    public String  units;
    public double  width;
    public double  height;

    public double  dpmWidth;
    public double  dpmHeight;
    
    public MapRover roverStart;
    
    public int experiments;
    public MapExperiment experiment[];
    
    public int hazards;
    public MapHazard hazard[];

    public MapDef() {
        clear();
    }
    
    public void clear() {
        name = "";
        units = "meters";
        width = 100.0;
        height = 100.0;

        dpmWidth = 30.0;
        dpmHeight = 30.0;
        
        roverStart = new MapRover();
        
        experiments = 0;
        experiment = new MapExperiment[MAX_EXPERIMENTS + 1];
        
        hazards = 0;
        hazard = new MapHazard[MAX_HAZARDS + 1];
    }

    public MapExperiment getExperiment(String id) {
        int e = 0;
        while (e < experiments) {
            e++;
            if (experiment[e].id.equalsIgnoreCase(id)) {
                return experiment[e];
            }
        }        
        return null;
    }
    
    public double getMaxDimension() {
        if (width > height) return width;
        return height;
    }
    
    public void readMap(String filename) {
        boolean sectionDescription = false;
        boolean sectionRover = false;
        boolean sectionExperiments = false;
        boolean sectionExperiment = false;
        boolean sectionHazards = false;
        boolean sectionHazard = false;
        boolean sectionPoint = false;

        clear();

        File xmlFile = new File(filename);
        InputStream is;
        try {
            is = new FileInputStream(xmlFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MapDef.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader;
        try {
            reader = factory.createXMLStreamReader(is);
        } catch (XMLStreamException ex) {
            Logger.getLogger(MapDef.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        String name = "";
        try {
            while(reader.hasNext()) {
                if (reader.hasName()) name = reader.getLocalName();

                if (reader.isStartElement()) {
                    if (name.equalsIgnoreCase("description")) sectionDescription = true;
                    if (name.equalsIgnoreCase("rover")) sectionRover = true;
                    if (name.equalsIgnoreCase("experiments")) sectionExperiments = true;
                    if (name.equalsIgnoreCase("experiment") && sectionExperiments) {
                        if (experiments < MAX_EXPERIMENTS) experiments++;
                        experiment[experiments] = new MapExperiment();
                        sectionExperiment = true;
                    }
                    if (name.equalsIgnoreCase("hazards")) sectionHazards = true;
                    if (name.equalsIgnoreCase("hazard") && sectionHazards) {
                        if (hazards < MAX_HAZARDS) hazards++;
                        hazard[hazards] = new MapHazard();
                        sectionHazard = true;
                    }
                    if (name.equalsIgnoreCase("point") && sectionExperiment) {
                        experiment[experiments].point = new Point2D.Double();
                        sectionPoint = true;
                    }
                    if (name.equalsIgnoreCase("point") && sectionHazard) {
                        hazard[hazards].points++;
                        hazard[hazards].point[hazard[hazards].points] = new Point2D.Double();
                        sectionPoint = true;
                    }
                }
                
                if (reader.hasText()) {
                        if (sectionDescription) {
                            if (name.equalsIgnoreCase("name")) this.name = reader.getText();
                            if (name.equalsIgnoreCase("units")) this.units = reader.getText();
                            if (name.equalsIgnoreCase("width")) this.width = Double.parseDouble(reader.getText());
                            if (name.equalsIgnoreCase("height")) this.height = Double.parseDouble(reader.getText());
                        }
                        if (sectionRover) {
                            if (name.equalsIgnoreCase("x")) roverStart.point.x = Double.parseDouble(reader.getText());
                            if (name.equalsIgnoreCase("y")) roverStart.point.y = Double.parseDouble(reader.getText());
                            if (name.equalsIgnoreCase("heading")) roverStart.heading = Double.parseDouble(reader.getText());
                            
                        }
                        if (sectionExperiments) {
                            if (sectionExperiment) {
                                if (name.equalsIgnoreCase("id")) experiment[experiments].id = reader.getText();
                                if (name.equalsIgnoreCase("name")) experiment[experiments].name = reader.getText();
                                if (name.equalsIgnoreCase("score")) experiment[experiments].score = Double.parseDouble(reader.getText());
                                if (sectionPoint) {
                                    if (name.equalsIgnoreCase("x")) experiment[experiments].point.x = Double.parseDouble(reader.getText());
                                    if (name.equalsIgnoreCase("y")) experiment[experiments].point.y = Double.parseDouble(reader.getText());
                                }                                
                            }
                        }
                        if (sectionHazards) {
                            if (sectionHazard) {
                                if (name.equalsIgnoreCase("id")) hazard[hazards].id = reader.getText();
                                if (name.equalsIgnoreCase("type")) {
                                    if (reader.getText().equalsIgnoreCase("avoid")) hazard[hazards].type = MapHazard.TYPE_AVOID;
                                }
                                if (name.equalsIgnoreCase("shape")) {
                                    if (reader.getText().equalsIgnoreCase("rectangle")) hazard[hazards].shape = MapHazard.SHAPE_RECTANGLE;
                                    if (reader.getText().equalsIgnoreCase("circle")) hazard[hazards].shape = MapHazard.SHAPE_CIRCLE;
                                }
                                if (sectionPoint) {
                                    if (name.equalsIgnoreCase("x")) hazard[hazards].point[hazard[hazards].points].x = Double.parseDouble(reader.getText());
                                    if (name.equalsIgnoreCase("y")) hazard[hazards].point[hazard[hazards].points].y = Double.parseDouble(reader.getText());
                                }
                                if (name.equalsIgnoreCase("radius")) {
                                    hazard[hazards].radius = Double.parseDouble(reader.getText());
                                }
                            }
                        }
                }
                if (reader.isEndElement()) {
                    if (name.equalsIgnoreCase("description")) sectionDescription = false;
                    if (name.equalsIgnoreCase("rover")) sectionRover = false;
                    if (name.equalsIgnoreCase("experiments")) sectionExperiments = false;
                    if (name.equalsIgnoreCase("experiment")) sectionExperiment = false;
                    if (name.equalsIgnoreCase("hazards")) sectionHazards = false;
                    if (name.equalsIgnoreCase("hazard")) sectionHazard = false;
                    if (name.equalsIgnoreCase("point")) sectionPoint = false;
                    name = "";
                }

                reader.next();
            }
        } catch (XMLStreamException ex) {
            Logger.getLogger(MapDef.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
