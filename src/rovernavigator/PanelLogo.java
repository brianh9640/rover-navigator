/*
 * Project:     Rover-Navigator
 * Description: Science Olympiad Rover Navigator Scoring Program
 * 
 * File:        PanelLogo.java
 * 
 * Created:     June 2013
 * 
 * Repository:  https://github.com/brianh9640/rover-navigator
 * 
 */
package rovernavigator;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 *
 * @author Brian
 */
public class PanelLogo extends JPanel {
    
    Image   imageLogo;
    
    public PanelLogo() {
        this.setLayout(null);
        javax.swing.ImageIcon imageTmp = new javax.swing.ImageIcon(getClass().getResource("images/so-logo.jpg"));
        imageLogo = imageTmp.getImage();
    }
    
    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imageLogo, 0, 0, null); // see javadoc for more info on the parameters            
    }    
    
}
