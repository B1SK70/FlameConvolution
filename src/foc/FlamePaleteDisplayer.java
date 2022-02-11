package foc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.*;
import javax.swing.*;

public class FlamePaleteDisplayer extends JPanel {

    private GridBagConstraints c;
    private FlamePalete FP;
    private ArrayList<TargetColor> targetColors;

    public FlamePaleteDisplayer(ControlPanel CP, FlamePalete FP) {
        super(new GridBagLayout());

        targetColors = FP.getTargets();

        this.FP = FP;

        this.setPreferredSize(new Dimension(160, 160));
        this.setBackground(new Color(211, 211, 211));

        drawTCs();

    }

    private void drawTCs() {
        
        targetColors = FP.getTargets();

        for (int i = 0; i < 4; i++) {

            TargetColor tc = targetColors.get(i);
            int actualTG = targetColors.indexOf(tc);
            Color actualColor = targetColors.get(i).getColor();
            
            //COLOR
            JButton colorBox = new JButton();
            colorBox.repaint();
            colorBox.setEnabled(false);
            colorBox.setBackground(new Color(
                    actualColor.getRed(),
                    actualColor.getGreen(),
                    actualColor.getBlue()
            ));
            colorBox.setPreferredSize(new Dimension(35, 35));
            colorBox.repaint();

            c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = i;
            c.gridheight = 1;
            c.gridwidth = 1;

            this.add(colorBox, c);

            //TEMP
            final int temp = targetColors.get(i).getTemperature();
            JLabel colorTemp = new JLabel("  " + String.valueOf(temp));
            colorTemp.setPreferredSize(new Dimension(40, 35));
            colorTemp.setForeground(Color.black);

            c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = i;
            c.gridheight = 1;
            c.gridwidth = 1;

            this.add(colorTemp, c);

            //BUTTON
            JButton deleteColor = new JButton();
            deleteColor.setPreferredSize(new Dimension(35, 35));
            deleteColor.setText("X");
            deleteColor.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 5));
            deleteColor.setVisible(true);
            deleteColor.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    Color color = new Color((new JColorChooser().showDialog(null, "Seleccione un Color", new Color(tc.getColor().getRGB()))).getRGB());

                    Color newColor = new Color(
                            color.getRed(),
                            color.getGreen(),
                            color.getBlue(),
                            tc.getColor().getAlpha());

                    FP.removeTargetColor(actualTG);
                    FP.addTargetColor(new TargetColor(temp, newColor));

                    FP.reload();
                    drawTCs();

                }
            });

            c = new GridBagConstraints();
            c.gridx = 2;
            c.gridy = i;
            c.gridheight = 1;
            c.gridwidth = 1;

            this.add(deleteColor, c);

        }
    }
}
