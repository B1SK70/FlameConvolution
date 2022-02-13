package foc;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;

public class ControlPanel extends JPanel{

    GridBagConstraints c;
    
    MyFlame programa;
    FlamePalete FP;
    
    JButton pauseBtn;
    JButton restartBtn;
    
    public ControlPanel(int w, int h, MyFlame programa, FlamePalete FP) {
        super(new GridBagLayout());
        
        this.programa = programa;
        this.FP = FP;
        
        this.setPreferredSize(new Dimension(w, h));
        this.setBackground(Color.gray);
        
                
        // PAUSE BUTTON
        pauseBtn = new JButton(); 
        pauseBtn.setPreferredSize(new Dimension(80, 40)); 
        pauseBtn.setFont(new Font("SHEIFF SANS", Font.BOLD, 9));
        pauseBtn.setText("PAUSE");
        pauseBtn.setVisible(true);
        
        pauseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                programa.switchPauseState();
                
                if ( pauseBtn.getText().equals("PAUSE") ) {
                    pauseBtn.setText("PLAY");
                } else if ( pauseBtn.getText().equals("PLAY") ) {
                    pauseBtn.setText("PAUSE");
                }
                
            }
        });
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 1;
         
        this.add(pauseBtn,c);
        
        // RESTART BUTTON
        restartBtn = new JButton(); 
        restartBtn.setPreferredSize(new Dimension(80, 40)); 
        restartBtn.setFont(new Font("SHEIFF SANS", Font.BOLD, 9));
        restartBtn.setText("RESTART");
        restartBtn.setVisible(true);
        restartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                programa.restartFlame();
                
            }
        });
        
        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 0;
        c.gridheight = 1;
         
        this.add(restartBtn,c);
        
        // ---------------
        
        //SPARKS
        JLabel sparksLb = new JLabel("Sparks  (rec: 65)");
        sparksLb.setPreferredSize(new Dimension(160, 50));
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 2;
        
        this.add(sparksLb,c);
        
        JSlider sparks = new JSlider(JSlider.HORIZONTAL, 1, 100, 65);
        sparks.setPreferredSize(new Dimension(160, 40));
        sparks.setMajorTickSpacing(20);
        sparks.setMinorTickSpacing(10);
        sparks.setPaintTicks(true);
        sparks.setPaintLabels(true);
        sparks.addChangeListener( new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                programa.setSparks(sparks.getValue());
            }
        });
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 2;
        c.gridheight = 2;
        c.gridwidth = 2;
        
        this.add(sparks,c);
     
        
        ///////////////////////
        
        //HEATLOSS
        JLabel heatLossLb = new JLabel("Flame heat loss  (rec: 50)");
        heatLossLb.setPreferredSize(new Dimension(160, 50));
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 4;
        c.gridheight = 1;
        c.gridwidth = 2;
        
        this.add(heatLossLb,c);
        
        JSlider heatLoss = new JSlider(JSlider.HORIZONTAL, 1, 100, 50);
        heatLoss.setPreferredSize(new Dimension(160, 40));
        heatLoss.setMajorTickSpacing(20);
        heatLoss.setMinorTickSpacing(10);
        heatLoss.setPaintTicks(true);
        heatLoss.setPaintLabels(true);
        heatLoss.addChangeListener( new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                programa.setHeatLoss(heatLoss.getValue());
            }
        });
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 5;
        c.gridheight = 2;
        c.gridwidth = 2;
        
        this.add(heatLoss,c);
        
        
        //SPEED
        JLabel msSpedLb = new JLabel("MS entre frames (rec: 20)");
        msSpedLb.setPreferredSize(new Dimension(160, 50));
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 7;
        c.gridheight = 1;
        c.gridwidth = 2;
        
        this.add(msSpedLb,c);
        
        JSlider framesPerSecond = new JSlider(JSlider.HORIZONTAL, 5, 40, 25);        
        framesPerSecond.setPreferredSize(new Dimension(160, 40));
        framesPerSecond.setMajorTickSpacing(10);
        framesPerSecond.setMinorTickSpacing(1);
        framesPerSecond.setPaintTicks(true);
        framesPerSecond.setPaintLabels(true);
        framesPerSecond.addChangeListener( new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                programa.setFireMsSpeed(framesPerSecond.getValue());
            }
        });
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 8;
        c.gridheight = 2;
        c.gridwidth = 2;
        
        this.add(framesPerSecond,c);
        

        //COLOR PALETE
        
        
        JLabel colorPalete = new JLabel("Color Palete");
        msSpedLb.setPreferredSize(new Dimension(160, 50));
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 10;
        c.gridheight = 1;
        c.gridwidth = 2;
        
        this.add(colorPalete,c);
        
        FlamePaleteDisplayer fpd = new FlamePaleteDisplayer( this, FP );
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 11;
        c.gridheight = 2;
        c.gridwidth = 2;
        
        this.add(fpd,c);
        
        
        //Convolution
        JLabel convolutionSensivility = new JLabel("Convolution edge sensility");
        convolutionSensivility.setPreferredSize(new Dimension(160, 50));
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 13;
        c.gridheight = 1;
        c.gridwidth = 2;
        
        this.add(convolutionSensivility,c);
        
        JSlider convolutionSensitivity = new JSlider(JSlider.HORIZONTAL, 15, 240, 100);
        convolutionSensitivity.setPreferredSize(new Dimension(160, 40));
        convolutionSensitivity.setMajorTickSpacing(50);
        convolutionSensitivity.setPaintTicks(true);
        convolutionSensitivity.setPaintLabels(true);
        convolutionSensitivity.addChangeListener( new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                programa.reConvolutionate(convolutionSensitivity.getValue());
            }
        });
        
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 14;
        c.gridheight = 2;
        c.gridwidth = 2;
        
        this.add(convolutionSensitivity,c);
        
        
    }
    
}
