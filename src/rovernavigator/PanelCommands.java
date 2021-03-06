/*
 * Project:     Rover-Navigator
 * Description: Science Olympiad Rover Navigator Scoring Program
 * 
 * File:        PanelCommands.java
 * 
 * Created:     June 2013
 * 
 * Repository:  https://github.com/brianh9640/rover-navigator
 * 
 */
package rovernavigator;

import java.awt.Rectangle;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import rovernavigator.motion.CommandList;

/**
 *
 * @author Brian
 */
public class PanelCommands extends javax.swing.JPanel {

    RoverNavigator main;
    
    /**
     * Creates new form PanelCommands
     */
    public PanelCommands() {
        initComponents();
        main = null;
        
        setInstructions();
    }

    public void setMain(RoverNavigator main) { this.main = main; }
    
    private void setInstructions() {
        String msg = "";
        
        msg += "<html>";
        msg += "<table>";
        
        msg += "<tr><td>";
        msg += "Left X";
        msg += "</td><td>";
        msg += "Turn left for X degrees";
        msg += "</tr></td>";
        
        msg += "<tr><td>";
        msg += "Right X";
        msg += "</td><td>";
        msg += "Turn right for X degrees";
        msg += "</tr></td>";
        
        msg += "<tr><td>";
        msg += "Forward X.X";
        msg += "</td><td>";
        msg += "Drive forward for X.X meters";
        msg += "</tr></td>";
        
        msg += "<tr><td>";
        msg += "Test _";
        msg += "</td><td>";
        msg += "Execute Experiment";
        msg += "</tr></td>";
        
        msg += "</table>";
        msg += "</html>";
        
        this.labelCmdInstructions.setText(msg);
    }
   
    public String getCommandText() { return editorCommands.getText(); }
    public void setCommandText(String txt) { editorCommands.setText(txt); } 
    
    public void updateCommands() {
        
        String line[] = editorCommands.getText().split("\n");
        String parse;
        int cmdType;
        double cmdValue = 0.0;
        String cmdNote;
        boolean validCmd;
        boolean outputInvalidLine;
        boolean validCmdList = true;
        int index;
        
        main.commands.clear();

        //System.out.println("Lines = " + line.length);
        
        int l = 0;
        while (l < line.length) {
            validCmd = true;
            outputInvalidLine = true;
            parse = line[l].trim().toUpperCase();
            
            if (parse.length() > 2) {
                if (parse.substring(0,1).equalsIgnoreCase("L")) {
                    outputInvalidLine = false;
                    cmdType = CommandList.COMMAND_LEFT;
                    index = parse.indexOf(" ");
                    if (index >= 0) {
                        try {
                            cmdValue = Double.parseDouble(parse.substring(index));
                        } catch (NumberFormatException e) {
                            commandLineError(cmdType,"Invalid Left Angle",parse);
                            validCmd = false;
                            validCmdList = false;
                        }
                    } else {
                        commandLineError(cmdType,"Left Angle Missing",parse);
                        validCmd = false;
                        validCmdList = false;
                    }
                    if (validCmd) main.commands.add(cmdType, cmdValue);
                }
 
                if (parse.substring(0,1).equalsIgnoreCase("R")) {
                    outputInvalidLine = false;
                    cmdType = CommandList.COMMAND_RIGHT;
                    index = parse.indexOf(" ");
                    if (index >= 0) {
                        try {
                            cmdValue = Double.parseDouble(parse.substring(index));
                        } catch (NumberFormatException e) {
                            commandLineError(cmdType,"Invalid Right Angle",parse);
                            validCmd = false;
                            validCmdList = false;
                        }
                    } else {
                        commandLineError(cmdType,"Right Angle Missing",parse);
                        validCmd = false;
                        validCmdList = false;
                    }
                    if (validCmd) main.commands.add(cmdType, cmdValue);
                }
                
                if (parse.substring(0,1).equalsIgnoreCase("F")) {
                    outputInvalidLine = false;
                    cmdType = CommandList.COMMAND_FORWARD;
                    index = parse.indexOf(" ");
                    if (index >= 0) {
                        try {
                            cmdValue = Double.parseDouble(parse.substring(index));
                        } catch (NumberFormatException e) {
                            commandLineError(cmdType,"Invalid Forward Distance",parse);
                            validCmd = false;
                            validCmdList = false;
                        }
                    } else {
                        commandLineError(cmdType,"Forward Distance Missing",parse);
                        validCmd = false;
                        validCmdList = false;
                    }
                    if (validCmd) main.commands.add(cmdType, cmdValue);
                }

                if (parse.substring(0,1).equalsIgnoreCase("T")) {
                    outputInvalidLine = false;
                    cmdType = CommandList.COMMAND_TEST;
                    cmdNote = "";
                    index = parse.indexOf(" ");
                    if (index >= 0) {
                        cmdNote = parse.substring(index).trim();
                        if (main.map.getExperiment(cmdNote) == null) {
                            commandLineError(cmdType,"Invalid Experiment ID",parse);
                            validCmd = false;
                            validCmdList = false;
                        }
                    } else {
                        commandLineError(cmdType,"Experiment ID Missing",parse);
                        validCmd = false;
                        validCmdList = false;
                    }
                    if (validCmd) main.commands.add(cmdType, cmdNote);
                }
            }
            if (outputInvalidLine) {
                if (parse.length() > 0) {
                    commandLineError(0,"Unknown Command Line",parse);
                    validCmdList = false;
                }
            }
            l++;
        }
        if (validCmdList) outputCompiledCommands();
        else {
            main.commands.clear();
        }
    }
    
    protected void commandLineError(int cmdType,String errorMsg,String line) {
        String title = "Error";
        String msg = "";
        switch (cmdType) {
            case CommandList.COMMAND_LEFT : 
                title += " - Left Command Error";
                break;
            case CommandList.COMMAND_RIGHT : 
                title += " - Right Command Error";
                break;
            case CommandList.COMMAND_FORWARD : 
                title += " - Forward Command Error";
                break;
            case CommandList.COMMAND_BACKWARD : 
                title += " - Backward Command Error";
                break;
            case CommandList.COMMAND_TEST : 
                title += " - Test Command Error";
                break;
        }
        msg += errorMsg;
        msg += "\nLine: " + line;
        JOptionPane.showMessageDialog(null, msg,title , JOptionPane.ERROR_MESSAGE);
    }

    public void outputCompiledCommands() {
        String textCmds = "";
        
        int c = 0;
        while (c < main.commands.count) {
            c++;
            textCmds += main.commands.outputCommandText(c) + "\n";
        }
        editorCommands.setText(textCmds);
    }
    
    public void updateLayout(int height) {
        Rectangle rectI = this.labelCmdInstructions.getBounds();
        Rectangle rectC = this.editorCommands.getBounds();
        
        int y = rectI.y + rectI.height;
        y += 4;
        
        rectC.y = y;
        //editorCommands.setBounds(rectC);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelCommands = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        editorCommands = new javax.swing.JEditorPane();
        labelCmdInstructions = new javax.swing.JLabel();

        labelCommands.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        labelCommands.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCommands.setText("COMMANDS");

        editorCommands.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        editorCommands.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                editorCommandsKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(editorCommands);

        labelCmdInstructions.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        labelCmdInstructions.setText("<html>Test<br>Line 2<br></html>");
        labelCmdInstructions.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCommands, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2)
            .addComponent(labelCmdInstructions)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(labelCommands)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelCmdInstructions, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void editorCommandsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_editorCommandsKeyPressed
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_editorCommandsKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane editorCommands;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelCmdInstructions;
    private javax.swing.JLabel labelCommands;
    // End of variables declaration//GEN-END:variables
}
