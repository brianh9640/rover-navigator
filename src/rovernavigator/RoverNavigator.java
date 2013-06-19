/*
 * Project:     Rover-Navigator
 * Description: Science Olympiad Rover Navigator Scoring Program
 * 
 * File:        RoverNavigator.java
 * 
 * Created:     June 2013
 * 
 * Repository:  https://github.com/brianh9640/rover-navigator
 * 
 */
package rovernavigator;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import rovernavigator.motion.CommandList;
import rovernavigator.motion.MotionPath;
import rovernavigator.map.MapDef;

/**
 *
 * @author Brian
 */
public class RoverNavigator extends JPanel implements ActionListener {

    public static final int BORDER_PANEL                = 2;
    public static final int PANEL_LOGO_HEIGHT           = 100;
    public static final int PANEL_COMMANDS_WIDTH        = 210;
    public static final int PANEL_CONTROL_HEIGHT        = 80;
    public static final int PANEL_RESULTS_WIDTH         = 166;
    
    JFrame              frameMain;
    PanelLogo           panelLogo;
    PanelMap            panelMap;
    PanelCommands       panelCommands;
    PanelControl        panelControl;
    PanelResults        panelResults;
    
    public MapDef map;
    public CommandList commands;
    public MotionPath motionPath;
    
    public boolean showResultsPanel = false;
    public boolean showRoverPath = true;
    public boolean showRover = true;
    public boolean showRoverStart = true;
        
    public RoverNavigator(JFrame frame) {    
        frameMain = frame;
        this.setLayout(null);
        
        panelLogo = new PanelLogo();
        this.add(panelLogo);
        
        //Old = new PanelCommandsOld();
//        this.add(Old);

        panelCommands = new PanelCommands();
        this.add(panelCommands);
        
        panelControl = new PanelControl();
        panelControl.setBorder(new javax.swing.border.LineBorder(java.awt.Color.BLACK));
        panelControl.setMain(this);
        this.add(panelControl);
        
        panelMap = new PanelMap();
        panelMap.setLayout(null);
        panelMap.setBorder(new javax.swing.border.LineBorder(java.awt.Color.BLACK));
        panelMap.setMain(this);
        this.add(panelMap);
        
        panelResults = new PanelResults();
        this.add(panelResults);
        
        layoutUpdate();
        
        frameMain.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                // This is only called when the user releases the mouse button.                
                layoutUpdate();
            }
        });
        
        map = new MapDef();
        //map.readMap("mapA.xml");
        
        commands = new CommandList();
        panelCommands.setMain(this);
        
        motionPath = new MotionPath();
        motionPath.setMain(this);

        panelResults.setMain(this);
        
        //loadFakeCommands();
        updateMotionPath();
        
    }
    
    private void loadFakeCommands() {
        commands.add(CommandList.COMMAND_RIGHT, 76.0);
        commands.add(CommandList.COMMAND_FORWARD, 30.9);
        commands.add(CommandList.COMMAND_LEFT, 90.0);
        commands.add(CommandList.COMMAND_FORWARD, 40.0);
        commands.add(CommandList.COMMAND_LEFT, 90.0);
        commands.add(CommandList.COMMAND_FORWARD, 20.0);
        commands.add(CommandList.COMMAND_LEFT,170.0);
        commands.add(CommandList.COMMAND_FORWARD, 10.0);
        commands.add(CommandList.COMMAND_LEFT, 90.0);
        commands.add(CommandList.COMMAND_FORWARD, 20.0);
        commands.add(CommandList.COMMAND_RIGHT, 45.0);
        commands.add(CommandList.COMMAND_FORWARD, 27.5);
        commands.add(CommandList.COMMAND_RIGHT, 65.0);
        commands.add(CommandList.COMMAND_FORWARD, 17.5);
    }
    
    public void updateMotionPath() {
        
        panelCommands.updateCommands();
        
        motionPath.updatePath();
        panelResults.updateStatus();
        panelMap.repaint();
        panelControl.repaint();
    }
    
    public int getWidth() {
        Dimension size = frameMain.getContentPane().getSize();
        return size.width;
    }
    
    public int getHeight() {
        Dimension size = frameMain.getContentPane().getSize();
        return size.height;
    }

    public final void layoutUpdate() {
        int x = RoverNavigator.BORDER_PANEL;
        int y = RoverNavigator.BORDER_PANEL;
        int width = RoverNavigator.PANEL_COMMANDS_WIDTH;
        int height = this.getHeight() - RoverNavigator.BORDER_PANEL * 2;
        
        panelLogo.setBounds(x, y, width, RoverNavigator.PANEL_LOGO_HEIGHT);
        
        y += RoverNavigator.PANEL_LOGO_HEIGHT;
        height -= RoverNavigator.PANEL_LOGO_HEIGHT;
        panelCommands.setBounds(x,y,width,height);
        panelCommands.updateLayout(height);
        
        y = RoverNavigator.BORDER_PANEL;
        x += width + RoverNavigator.BORDER_PANEL;
        width = this.getWidth() - x - RoverNavigator.BORDER_PANEL;
        height = RoverNavigator.PANEL_CONTROL_HEIGHT;
        panelControl.setBounds(x,y,width,height);
        
        y += RoverNavigator.PANEL_CONTROL_HEIGHT + RoverNavigator.BORDER_PANEL;
        height = this.getHeight() - y - RoverNavigator.BORDER_PANEL;
        if (showResultsPanel) {
            panelResults.setVisible(true);
            int x1 = this.getWidth() - RoverNavigator.PANEL_RESULTS_WIDTH;
            panelResults.setBounds(x1, y, RoverNavigator.PANEL_RESULTS_WIDTH, height);
            width -= RoverNavigator.PANEL_RESULTS_WIDTH;
        } else {
            panelResults.setVisible(false);
        }
        
        panelMap.setBounds(x,y,width,height);
   
    }
    
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

    }
    
    private static void createAndShowGUI() {

        JFrame frame = new JFrame("Rover Navigator Scoring Program");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //GraphicsConfiguration gc;
        //Rectangle bounds = gc.getBounds();
        frame.setLocation(50,50);
        Dimension sizeMin = new Dimension(800,600);
        frame.setMinimumSize(sizeMin);

        //Add contents to the window.
        frame.add(new RoverNavigator(frame));

        //Display the window.
        frame.pack();
        frame.setVisible(true);

    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } 
        catch (Exception e) {
           // handle exception
        }        
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
