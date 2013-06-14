/*
 * Project:     Rover-Navigator
 * Description: Science Olympiad Rover Navigator Scoring Program
 * 
 * File:        PanelControl.java
 * 
 * Created:     June 2013
 * 
 * Repository:  https://github.com/brianh9640/rover-navigator
 * 
 */
package rovernavigator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import rovernavigator.map.MapDef;
import rovernavigator.motion.CommandList;

/**
 *
 * @author Brian
 */
public class PanelControl extends javax.swing.JPanel {

    RoverNavigator main;

    private String fileLocPath = null;

    /**
     * Creates new form PanelControl
     */
    public PanelControl() {
        initComponents();
        main = null;
    }

    public void setMain(RoverNavigator main) {
        this.main = main;
        checkboxRover.setSelected(main.showRover);
        checkboxRoverPath.setSelected(main.showRoverPath);
        checkboxRoverStart.setSelected(main.showRoverStart);
    }

    public void updateStatus() {
        labelDistance.setText(new Double(main.motionPath.getTravelDistance()).toString());
        labelDegrees.setText(new Double(main.motionPath.getTravelDegrees()).toString());
        labelExperimentError.setText(new Double(main.motionPath.getExperimentError()).toString());
    }

    public void commandListSave() {
        JFileChooser fc = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Rover Programs", CommandList.PROGRAM_EXTENSION);
        fc.setFileFilter(filter);
        if (fileLocPath != null) {
            fc.setCurrentDirectory(new File(fileLocPath));
        }
        int returnVal = fc.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fc.getSelectedFile();
                fileLocPath = fc.getSelectedFile().getPath();
//                if (!file.canWrite()) {
//                    // TODO add cannot write error message
//                    return;
//                }
                String filename = file.getAbsoluteFile().toString();
                String extension = "." + CommandList.PROGRAM_EXTENSION;
                if (filename.indexOf(extension) < 0) filename += extension;
                System.out.println(filename);
                FileWriter outputStream;
                outputStream = new FileWriter(filename);
                outputStream.write(main.panelCommands.getCommandText());
                outputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(PanelControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void commandListLoad() {
        JFileChooser fc = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Rover Programs", CommandList.PROGRAM_EXTENSION);
        fc.setFileFilter(filter);
        if (fileLocPath != null) {
            fc.setCurrentDirectory(new File(fileLocPath));
        }
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fc.getSelectedFile();
                fileLocPath = fc.getSelectedFile().getPath();
//                if (!file.canWrite()) {
//                    // TODO add cannot write error message
//                    return;
//                }
                FileReader inputStream = new FileReader(file.getAbsoluteFile());
                BufferedReader reader = new BufferedReader(inputStream);
                StringBuilder  stringBuilder = new StringBuilder();
                String line;
                while( ( line = reader.readLine() ) != null ) {
                    stringBuilder.append( line );
                    stringBuilder.append("\n");
                }

                inputStream.close();
                main.panelCommands.setCommandText(stringBuilder.toString());
            } catch (IOException ex) {
                Logger.getLogger(PanelControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void mapLoad() {
        JFileChooser fc = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Rover Maps", MapDef.MAP_FILE_EXTENSION);
        fc.setFileFilter(filter);
        if (fileLocPath != null) {
            fc.setCurrentDirectory(new File(fileLocPath));
        }
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            main.map.readMap(file.getAbsoluteFile().toString());
            main.panelMap.repaint();
        }

    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator2 = new javax.swing.JSeparator();
        checkboxRoverPath = new javax.swing.JCheckBox();
        checkboxRover = new javax.swing.JCheckBox();
        labelDistance = new javax.swing.JLabel();
        labelDegrees = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        buttonCmdNew = new javax.swing.JButton();
        buttonCmdLoad = new javax.swing.JButton();
        buttonCmdSave = new javax.swing.JButton();
        buttonMapLoad = new javax.swing.JButton();
        buttonRuleLoad = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        buttonUpdatePath = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        buttonMapZoomIn = new javax.swing.JButton();
        buttonMapZoomOut = new javax.swing.JButton();
        buttonMapZoomReset = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        buttonProgExit = new javax.swing.JButton();
        labelExperimentError = new javax.swing.JLabel();
        checkboxRoverStart = new javax.swing.JCheckBox();

        checkboxRoverPath.setText("Show Rover Path");
        checkboxRoverPath.setName("checkboxRoverPath"); // NOI18N
        checkboxRoverPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxRoverPathActionPerformed(evt);
            }
        });

        checkboxRover.setText("Show Rover");
        checkboxRover.setName("checkboxRover"); // NOI18N
        checkboxRover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxRoverActionPerformed(evt);
            }
        });

        labelDistance.setText("Distance");

        labelDegrees.setText("Degrees");

        jToolBar1.setFloatable(false);
        jToolBar1.setMinimumSize(new java.awt.Dimension(200, 48));
        jToolBar1.setPreferredSize(new java.awt.Dimension(200, 48));

        buttonCmdNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rovernavigator/images/file_yellow_empty.png"))); // NOI18N
        buttonCmdNew.setToolTipText("Clear Command List");
        buttonCmdNew.setMargin(new java.awt.Insets(4, 4, 4, 4));
        buttonCmdNew.setPreferredSize(new java.awt.Dimension(40, 40));
        jToolBar1.add(buttonCmdNew);

        buttonCmdLoad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rovernavigator/images/file_yellow_open_hdd.png"))); // NOI18N
        buttonCmdLoad.setToolTipText("Load Command List from Drive");
        buttonCmdLoad.setMargin(new java.awt.Insets(4, 4, 4, 4));
        buttonCmdLoad.setPreferredSize(new java.awt.Dimension(40, 40));
        buttonCmdLoad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonCmdLoadMouseClicked(evt);
            }
        });
        jToolBar1.add(buttonCmdLoad);

        buttonCmdSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rovernavigator/images/file_yellow_save_hdd.png"))); // NOI18N
        buttonCmdSave.setToolTipText("Save Command List to Drive");
        buttonCmdSave.setMargin(new java.awt.Insets(4, 4, 4, 4));
        buttonCmdSave.setPreferredSize(new java.awt.Dimension(40, 40));
        buttonCmdSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonCmdSaveMouseClicked(evt);
            }
        });
        jToolBar1.add(buttonCmdSave);

        buttonMapLoad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rovernavigator/images/map_open_hdd.png"))); // NOI18N
        buttonMapLoad.setToolTipText("Load Map from Drive");
        buttonMapLoad.setFocusable(false);
        buttonMapLoad.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonMapLoad.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonMapLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMapLoadActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonMapLoad);

        buttonRuleLoad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rovernavigator/images/rule_open_hdd.png"))); // NOI18N
        buttonRuleLoad.setToolTipText("Load Rules from Drive");
        buttonRuleLoad.setFocusable(false);
        buttonRuleLoad.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonRuleLoad.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(buttonRuleLoad);
        jToolBar1.add(jSeparator1);

        buttonUpdatePath.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rovernavigator/images/arrow_plain_green_E.png"))); // NOI18N
        buttonUpdatePath.setToolTipText("Execute Rover Path");
        buttonUpdatePath.setFocusable(false);
        buttonUpdatePath.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonUpdatePath.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonUpdatePath.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonUpdatePathMouseClicked(evt);
            }
        });
        jToolBar1.add(buttonUpdatePath);
        jToolBar1.add(jSeparator3);

        buttonMapZoomIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rovernavigator/images/magnifier_plus_blue.png"))); // NOI18N
        buttonMapZoomIn.setFocusable(false);
        buttonMapZoomIn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonMapZoomIn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonMapZoomIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMapZoomInActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonMapZoomIn);

        buttonMapZoomOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rovernavigator/images/magnifier_minus_blue.png"))); // NOI18N
        buttonMapZoomOut.setFocusable(false);
        buttonMapZoomOut.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonMapZoomOut.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonMapZoomOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMapZoomOutActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonMapZoomOut);

        buttonMapZoomReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rovernavigator/images/arrow_plain_blue_NE_SE_SW_NW.png"))); // NOI18N
        buttonMapZoomReset.setFocusable(false);
        buttonMapZoomReset.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonMapZoomReset.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonMapZoomReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMapZoomResetActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonMapZoomReset);
        jToolBar1.add(jSeparator4);

        buttonProgExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rovernavigator/images/exit.png"))); // NOI18N
        buttonProgExit.setToolTipText("Exit Program");
        buttonProgExit.setPreferredSize(new java.awt.Dimension(40, 40));
        buttonProgExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonProgExitMouseClicked(evt);
            }
        });
        jToolBar1.add(buttonProgExit);

        labelExperimentError.setText("Error");

        checkboxRoverStart.setText("Rover Start");
        checkboxRoverStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxRoverStartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(checkboxRoverStart)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkboxRoverPath)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkboxRover)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelDistance, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelDegrees, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelExperimentError, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkboxRoverPath)
                    .addComponent(checkboxRover)
                    .addComponent(checkboxRoverStart))
                .addGap(63, 63, 63))
            .addGroup(layout.createSequentialGroup()
                .addComponent(labelDistance)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelDegrees)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelExperimentError)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonUpdatePathMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonUpdatePathMouseClicked
        main.updateMotionPath();

    }//GEN-LAST:event_buttonUpdatePathMouseClicked

    private void buttonCmdSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonCmdSaveMouseClicked
        commandListSave();
    }//GEN-LAST:event_buttonCmdSaveMouseClicked

    private void buttonProgExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonProgExitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_buttonProgExitMouseClicked

    private void buttonCmdLoadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonCmdLoadMouseClicked
        commandListLoad();

    }//GEN-LAST:event_buttonCmdLoadMouseClicked

    private void checkboxRoverPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxRoverPathActionPerformed
        //System.out.println(evt.toString());
        main.showRoverPath = checkboxRoverPath.isSelected();
        if (main.panelMap != null) main.panelMap.repaint();        
    }//GEN-LAST:event_checkboxRoverPathActionPerformed

    private void checkboxRoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxRoverActionPerformed
        main.showRover = checkboxRover.isSelected();
        if (main.panelMap != null) main.panelMap.repaint();
        
    }//GEN-LAST:event_checkboxRoverActionPerformed

    private void buttonMapZoomInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMapZoomInActionPerformed
        main.panelMap.zoomIn();

    }//GEN-LAST:event_buttonMapZoomInActionPerformed

    private void buttonMapZoomOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMapZoomOutActionPerformed
        main.panelMap.zoomOut();

    }//GEN-LAST:event_buttonMapZoomOutActionPerformed

    private void buttonMapZoomResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMapZoomResetActionPerformed
        main.panelMap.zoomReset();
        
    }//GEN-LAST:event_buttonMapZoomResetActionPerformed

    private void checkboxRoverStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxRoverStartActionPerformed
        main.showRoverStart = checkboxRoverStart.isSelected();
        if (main.panelMap != null) main.panelMap.repaint();
        
    }//GEN-LAST:event_checkboxRoverStartActionPerformed

    private void buttonMapLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMapLoadActionPerformed
        mapLoad();
        
    }//GEN-LAST:event_buttonMapLoadActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCmdLoad;
    private javax.swing.JButton buttonCmdNew;
    private javax.swing.JButton buttonCmdSave;
    private javax.swing.JButton buttonMapLoad;
    private javax.swing.JButton buttonMapZoomIn;
    private javax.swing.JButton buttonMapZoomOut;
    private javax.swing.JButton buttonMapZoomReset;
    private javax.swing.JButton buttonProgExit;
    private javax.swing.JButton buttonRuleLoad;
    private javax.swing.JButton buttonUpdatePath;
    private javax.swing.JCheckBox checkboxRover;
    private javax.swing.JCheckBox checkboxRoverPath;
    private javax.swing.JCheckBox checkboxRoverStart;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar jToolBar1;
    public javax.swing.JLabel labelDegrees;
    public javax.swing.JLabel labelDistance;
    private javax.swing.JLabel labelExperimentError;
    // End of variables declaration//GEN-END:variables
}
