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

import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrintQuality;
import javax.print.attribute.standard.PrinterResolution;
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

    private String fileLocPathCommands = null;
    private String fileLocPathMaps = null;

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
        checkboxShowResults.setSelected(main.showResultsPanel);
        checkboxShowHazardHits.setSelected(main.showHazardHits);
        
        labelVersion.setText("Version " + main.version);        
    }

    public void commandListSave() {
        JFileChooser fc = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Rover Programs", CommandList.PROGRAM_EXTENSION);
        fc.setFileFilter(filter);
        if (fileLocPathCommands != null) {
            fc.setCurrentDirectory(new File(fileLocPathCommands));
        } else {
            fc.setCurrentDirectory(new File("."));
        }
            
        int returnVal = fc.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fc.getSelectedFile();
                fileLocPathCommands = fc.getSelectedFile().getPath();
//                if (!file.canWrite()) {
//                    // TODO add cannot write error message
//                    return;
//                }
                String filename = file.getAbsoluteFile().toString();
                String extension = "." + CommandList.PROGRAM_EXTENSION;
                if (filename.indexOf(extension) < 0) filename += extension;
                FileWriter outputStream;
                outputStream = new FileWriter(filename);
                outputStream.write(main.panelCommands.getCommandText());
                outputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(PanelControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else { main.panelMap.repaint(); }

    }

    public void commandListLoad() {
        JFileChooser fc = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Rover Programs", CommandList.PROGRAM_EXTENSION);
        fc.setFileFilter(filter);
        if (fileLocPathCommands != null) {
            fc.setCurrentDirectory(new File(fileLocPathCommands));
        } else {
            fc.setCurrentDirectory(new File("."));
        }
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fc.getSelectedFile();
                fileLocPathCommands = fc.getSelectedFile().getPath();
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
                main.updateMotionPath();
            } catch (IOException ex) {
                Logger.getLogger(PanelControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else { main.panelMap.repaint(); }

    }

    public void mapLoad() {
        JFileChooser fc = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Rover Maps", MapDef.MAP_FILE_EXTENSION);
        fc.setFileFilter(filter);
        if (fileLocPathMaps != null) {
            fc.setCurrentDirectory(new File(fileLocPathMaps));
        } else {
            fc.setCurrentDirectory(new File("."));
        }
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            fileLocPathMaps = fc.getSelectedFile().getPath();
            main.map.readMap(file.getAbsoluteFile().toString());
            main.updateMotionPath();
        }
        main.panelMap.repaint();

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
        jToolBar1 = new javax.swing.JToolBar();
        buttonMapLoad = new javax.swing.JButton();
        buttonRuleLoad = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        buttonCmdNew = new javax.swing.JButton();
        buttonCmdLoad = new javax.swing.JButton();
        buttonCmdSave = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        buttonUpdatePath = new javax.swing.JButton();
        buttonPrint = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        buttonMapZoomIn = new javax.swing.JButton();
        buttonMapZoomOut = new javax.swing.JButton();
        buttonMapZoomReset = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        buttonProgExit = new javax.swing.JButton();
        checkboxRoverStart = new javax.swing.JCheckBox();
        checkboxShowResults = new javax.swing.JCheckBox();
        labelVersion = new javax.swing.JLabel();
        checkboxShowHazardHits = new javax.swing.JCheckBox();

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

        jToolBar1.setFloatable(false);
        jToolBar1.setMinimumSize(new java.awt.Dimension(200, 48));
        jToolBar1.setPreferredSize(new java.awt.Dimension(200, 48));

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
        buttonRuleLoad.setEnabled(false);
        buttonRuleLoad.setFocusable(false);
        buttonRuleLoad.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonRuleLoad.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(buttonRuleLoad);
        jToolBar1.add(jSeparator5);

        buttonCmdNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rovernavigator/images/file_yellow_empty.png"))); // NOI18N
        buttonCmdNew.setToolTipText("Clear Command List");
        buttonCmdNew.setMargin(new java.awt.Insets(4, 4, 4, 4));
        buttonCmdNew.setPreferredSize(new java.awt.Dimension(40, 40));
        buttonCmdNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCmdNewActionPerformed(evt);
            }
        });
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

        buttonPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rovernavigator/images/printer.png"))); // NOI18N
        buttonPrint.setText("");
        buttonPrint.setFocusable(false);
        buttonPrint.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonPrint.setMaximumSize(new java.awt.Dimension(39, 39));
        buttonPrint.setMinimumSize(new java.awt.Dimension(39, 39));
        buttonPrint.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPrintActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonPrint);
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

        checkboxRoverStart.setText("Rover Start");
        checkboxRoverStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxRoverStartActionPerformed(evt);
            }
        });

        checkboxShowResults.setText("Show Results");
        checkboxShowResults.setName("checkboxRover"); // NOI18N
        checkboxShowResults.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxShowResultsActionPerformed(evt);
            }
        });

        labelVersion.setText("Version 0.1.1");

        checkboxShowHazardHits.setText("Show Hazard Hits");
        checkboxShowHazardHits.setName("checkboxRover"); // NOI18N
        checkboxShowHazardHits.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxShowHazardHitsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(checkboxRoverStart)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkboxRoverPath)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkboxRover)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkboxShowResults)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkboxShowHazardHits)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelVersion))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelVersion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkboxRoverPath)
                    .addComponent(checkboxRover)
                    .addComponent(checkboxRoverStart)
                    .addComponent(checkboxShowResults)
                    .addComponent(checkboxShowHazardHits))
                .addContainerGap(24, Short.MAX_VALUE))
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

    private void buttonCmdNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCmdNewActionPerformed
        main.panelCommands.setCommandText("");
        main.updateMotionPath();
        
    }//GEN-LAST:event_buttonCmdNewActionPerformed

    private void checkboxShowResultsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxShowResultsActionPerformed
        main.showResultsPanel = checkboxShowResults.isSelected();
        main.layoutUpdate();
    }//GEN-LAST:event_checkboxShowResultsActionPerformed

    private void checkboxShowHazardHitsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxShowHazardHitsActionPerformed
        main.showHazardHits = checkboxShowHazardHits.isSelected();
        if (main.panelMap != null) main.panelMap.repaint();
        
    }//GEN-LAST:event_checkboxShowHazardHitsActionPerformed

    private void buttonPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPrintActionPerformed
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(new PrintResults(main));
        
//        PrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
//        attr.add(new PrinterResolution(300, 300, PrinterResolution.DPI));
//        attr.add(new MediaPrintableArea((float) 0.5,(float) 0.5,(float) 7.5,(float) 10.0,MediaPrintableArea.INCH));
//        attr.add(MediaSizeName.NA_LETTER);
//        attr.add(new Copies(1));
//        attr.add(OrientationRequested.PORTRAIT);
//        attr.add(PrintQuality.HIGH);        
//        
        
        if (job.printDialog()) {
            try { job.print(); }
            catch (Exception e) { }
        }
    }//GEN-LAST:event_buttonPrintActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCmdLoad;
    private javax.swing.JButton buttonCmdNew;
    private javax.swing.JButton buttonCmdSave;
    private javax.swing.JButton buttonMapLoad;
    private javax.swing.JButton buttonMapZoomIn;
    private javax.swing.JButton buttonMapZoomOut;
    private javax.swing.JButton buttonMapZoomReset;
    private javax.swing.JButton buttonPrint;
    private javax.swing.JButton buttonProgExit;
    private javax.swing.JButton buttonRuleLoad;
    private javax.swing.JButton buttonUpdatePath;
    private javax.swing.JCheckBox checkboxRover;
    private javax.swing.JCheckBox checkboxRoverPath;
    private javax.swing.JCheckBox checkboxRoverStart;
    private javax.swing.JCheckBox checkboxShowHazardHits;
    private javax.swing.JCheckBox checkboxShowResults;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel labelVersion;
    // End of variables declaration//GEN-END:variables
}
