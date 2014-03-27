/*
 * Project:     Rover-Navigator
 * Description: Science Olympiad Rover Navigator Scoring Program
 * 
 * File:        DialogRunPath.java
 * 
 * Created:     February 2014
 * 
 * Repository:  https://github.com/brianh9640/rover-navigator
 * 
 */
package rovernavigator;

public class DialogRunPath extends javax.swing.JDialog {

    public static final int MAX_FIELDS     = 15;
    
    RoverNavigator main;

    public DialogRunPath(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public void setMain(RoverNavigator main) {
        this.main = main;
        updateFields();
    }
    
    public void updateFields() {
        
        this.textTeamNo.setText(main.commands.teamNo);
        this.textSchoolName.setText(main.commands.school);
        
        int i = 1;
        while (i <= MAX_FIELDS) {
            if (i < main.map.experiments) {
                setField(i,true,main.map.experiment[i].id,0.0);
            } else {
                setField(i,false,"",0.0);
            }
            i++;
        }
        
        
    }
    
    protected void setField(int n,boolean show,String id,Double value) {
        String svalue = Double.toString(value);
        switch (n) {
            case 1:
                labelEx1.setText(id);
                textEx1.setText(svalue);
                labelEx1.setVisible(show);
                textEx1.setVisible(show);
                break;
            case 2:
                labelEx2.setText(id);
                textEx2.setText(svalue);
                labelEx2.setVisible(show);
                textEx2.setVisible(show);
                break;
            case 3:
                labelEx3.setText(id);
                textEx3.setText(svalue);
                labelEx3.setVisible(show);
                textEx3.setVisible(show);
                break;
            case 4:
                labelEx4.setText(id);
                textEx4.setText(svalue);
                labelEx4.setVisible(show);
                textEx4.setVisible(show);
                break;
            case 5:
                labelEx5.setText(id);
                textEx5.setText(svalue);
                labelEx5.setVisible(show);
                textEx5.setVisible(show);
                break;
            case 6:
                labelEx6.setText(id);
                textEx6.setText(svalue);
                labelEx6.setVisible(show);
                textEx6.setVisible(show);
                break;
            case 7:
                labelEx7.setText(id);
                textEx7.setText(svalue);
                labelEx7.setVisible(show);
                textEx7.setVisible(show);
                break;
            case 8:
                labelEx8.setText(id);
                textEx8.setText(svalue);
                labelEx8.setVisible(show);
                textEx8.setVisible(show);
                break;
            case 9:
                labelEx9.setText(id);
                textEx9.setText(svalue);
                labelEx9.setVisible(show);
                textEx9.setVisible(show);
                break;
            case 10:
                labelEx10.setText(id);
                textEx10.setText(svalue);
                labelEx10.setVisible(show);
                textEx10.setVisible(show);
                break;
            case 11:
                labelEx11.setText(id);
                textEx11.setText(svalue);
                labelEx11.setVisible(show);
                textEx11.setVisible(show);
                break;
            case 12:
                labelEx12.setText(id);
                textEx12.setText(svalue);
                labelEx12.setVisible(show);
                textEx12.setVisible(show);
                break;
            case 13:
                labelEx13.setText(id);
                textEx13.setText(svalue);
                labelEx13.setVisible(show);
                textEx13.setVisible(show);
                break;
            case 14:
                labelEx14.setText(id);
                textEx14.setText(svalue);
                labelEx14.setVisible(show);
                textEx14.setVisible(show);
                break;
            case 15:
                labelEx15.setText(id);
                textEx15.setText(svalue);
                labelEx15.setVisible(show);
                textEx15.setVisible(show);
                break;
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

        textEx6 = new javax.swing.JTextField();
        textEx14 = new javax.swing.JTextField();
        textEx5 = new javax.swing.JTextField();
        labelEx14 = new javax.swing.JLabel();
        labelEx5 = new javax.swing.JLabel();
        labelEx13 = new javax.swing.JLabel();
        textEx4 = new javax.swing.JTextField();
        textEx13 = new javax.swing.JTextField();
        labelEx4 = new javax.swing.JLabel();
        textEx3 = new javax.swing.JTextField();
        labelEx3 = new javax.swing.JLabel();
        textEx15 = new javax.swing.JTextField();
        textEx2 = new javax.swing.JTextField();
        labelEx15 = new javax.swing.JLabel();
        labelEx2 = new javax.swing.JLabel();
        textEx7 = new javax.swing.JTextField();
        labelEx6 = new javax.swing.JLabel();
        labelEx7 = new javax.swing.JLabel();
        textEx8 = new javax.swing.JTextField();
        labelEx8 = new javax.swing.JLabel();
        textEx9 = new javax.swing.JTextField();
        labelEx9 = new javax.swing.JLabel();
        textEx10 = new javax.swing.JTextField();
        labelEx10 = new javax.swing.JLabel();
        labelEx11 = new javax.swing.JLabel();
        textEx1 = new javax.swing.JTextField();
        textEx11 = new javax.swing.JTextField();
        textEx12 = new javax.swing.JTextField();
        labelEx12 = new javax.swing.JLabel();
        labelEx1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        buttonRun = new javax.swing.JButton();
        buttonCancel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        textTeamNo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        textSchoolName = new javax.swing.JTextField();

        labelEx14.setText("\"A\"");
        labelEx14.setToolTipText("");

        labelEx5.setText("\"A\"");
        labelEx5.setToolTipText("");

        labelEx13.setText("\"A\"");
        labelEx13.setToolTipText("");

        labelEx4.setText("\"A\"");
        labelEx4.setToolTipText("");

        labelEx3.setText("\"A\"");
        labelEx3.setToolTipText("");

        labelEx15.setText("\"A\"");
        labelEx15.setToolTipText("");

        labelEx2.setText("\"A\"");
        labelEx2.setToolTipText("");

        labelEx6.setText("\"A\"");
        labelEx6.setToolTipText("");

        labelEx7.setText("\"A\"");
        labelEx7.setToolTipText("");

        labelEx8.setText("\"A\"");
        labelEx8.setToolTipText("");

        labelEx9.setText("\"A\"");
        labelEx9.setToolTipText("");

        labelEx10.setText("\"A\"");
        labelEx10.setToolTipText("");

        labelEx11.setText("\"A\"");
        labelEx11.setToolTipText("");

        labelEx12.setText("\"A\"");
        labelEx12.setToolTipText("");

        labelEx1.setText("\"A\"");
        labelEx1.setToolTipText("");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel3.setText("EXPERIMENT SCORES");
        jLabel3.setToolTipText("");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        buttonRun.setText("Run/Update");
        buttonRun.setToolTipText("");
        buttonRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRunActionPerformed(evt);
            }
        });

        buttonCancel.setText("Cancel");
        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelActionPerformed(evt);
            }
        });

        jLabel1.setText("Team #");

        jLabel2.setText("School");
        jLabel2.setToolTipText("");

        textSchoolName.setToolTipText("");
        textSchoolName.setName(""); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 166, Short.MAX_VALUE)
                        .addComponent(buttonCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonRun))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textTeamNo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textSchoolName, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(textTeamNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(textSchoolName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonRun)
                    .addComponent(buttonCancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_buttonCancelActionPerformed

    private void buttonRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRunActionPerformed
        this.setVisible(false);
        
        main.commands.teamNo = textTeamNo.getText();
        main.commands.school = textSchoolName.getText();
        
        main.updateMotionPath();
    }//GEN-LAST:event_buttonRunActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(DialogRunPath.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(DialogRunPath.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(DialogRunPath.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(DialogRunPath.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                DialogRunPath dialog = new DialogRunPath(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonRun;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel labelEx1;
    private javax.swing.JLabel labelEx10;
    private javax.swing.JLabel labelEx11;
    private javax.swing.JLabel labelEx12;
    private javax.swing.JLabel labelEx13;
    private javax.swing.JLabel labelEx14;
    private javax.swing.JLabel labelEx15;
    private javax.swing.JLabel labelEx2;
    private javax.swing.JLabel labelEx3;
    private javax.swing.JLabel labelEx4;
    private javax.swing.JLabel labelEx5;
    private javax.swing.JLabel labelEx6;
    private javax.swing.JLabel labelEx7;
    private javax.swing.JLabel labelEx8;
    private javax.swing.JLabel labelEx9;
    private javax.swing.JTextField textEx1;
    private javax.swing.JTextField textEx10;
    private javax.swing.JTextField textEx11;
    private javax.swing.JTextField textEx12;
    private javax.swing.JTextField textEx13;
    private javax.swing.JTextField textEx14;
    private javax.swing.JTextField textEx15;
    private javax.swing.JTextField textEx2;
    private javax.swing.JTextField textEx3;
    private javax.swing.JTextField textEx4;
    private javax.swing.JTextField textEx5;
    private javax.swing.JTextField textEx6;
    private javax.swing.JTextField textEx7;
    private javax.swing.JTextField textEx8;
    private javax.swing.JTextField textEx9;
    private javax.swing.JTextField textSchoolName;
    private javax.swing.JTextField textTeamNo;
    // End of variables declaration//GEN-END:variables
}
