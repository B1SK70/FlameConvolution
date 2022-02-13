package foc;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MyFlame extends JFrame {

    private int width = 1100;
    private int height = 700;

    GridBagConstraints c;

    private BufferedImage normalImage;
    private BufferedImage convolutedImage;
    private BufferedImage background;
    private Flame normalFlame;
    private Flame convolutedFlame;

    private FlamePalete FP = createFlamePalete();

    Viewer viewer;
    
    public MyFlame() throws IOException {
        setWindowParams();
        
        //edificios.jpg
        //valley.jpg
        //sailorMoon.png
        //obito.jpg
        File backgroundFile = new File("src/edificios.jpg");

        // ----
        normalImage = ImageIO.read(backgroundFile);
        normalImage = resize(normalImage, 300, 200);

        // ----
        convolutedImage = new Convolution(normalImage);

        // ----
        normalFlame = new Flame(300, 200, null);
        normalFlame.setFlamePalete(FP);

        // ----
        background = ImageIO.read(backgroundFile);
        convolutedFlame = new Flame(900, 500, background);
        convolutedFlame.setFlamePalete(FP);

        ControlPanel cp1 = new ControlPanel(200, 700, this, FP);
        c = new GridBagConstraints();
        c.gridx = 0;
        this.add(cp1, c);

        viewer = new Viewer(900, 700, normalImage, convolutedImage, normalFlame, convolutedFlame);
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridwidth = 1;
        this.add(viewer, c);

        new Thread(normalFlame).start();
        new Thread(convolutedFlame).start();

        new Thread(viewer).start();

        this.pack();
    }
    
    public void reConvolutionate(int sensivility) {
        viewer.reConvolutionate(sensivility);
        convolutedFlame.reConvolutionate(sensivility);
    }

    public void setFireMsSpeed(int ms) {
        normalFlame.setFireMsSpeed(ms);
        convolutedFlame.setFireMsSpeed(ms);
    }

    public void setHeatLoss(int hl) {
        normalFlame.setHeatLoss(hl);
        convolutedFlame.setHeatLoss(hl);
    }

    public void setSparks(int sprks) {
        normalFlame.setSparks(sprks);
        convolutedFlame.setSparks(sprks);
    }

    public void switchPauseState() {
        normalFlame.switchPauseState();
        convolutedFlame.switchPauseState();
    }

    public void restartFlame() {
        normalFlame.restart();
        convolutedFlame.restart();
    }

    public FlamePalete getColorPalete() {
        return FP;
    }

    public void removeTargetColor(int i) {
        FP = getColorPalete();
        FP.removeTargetColor(i);
    }

    private FlamePalete createFlamePalete() {
        FlamePalete flamePalete = new FlamePalete();

        flamePalete.addTargetColor(new TargetColor(0, new Color(0, 0, 0, 0)));
        flamePalete.addTargetColor(new TargetColor(90, new Color(255, 0, 0, 180)));
        flamePalete.addTargetColor(new TargetColor(170, new Color(255, 165, 0, 240)));
        flamePalete.addTargetColor(new TargetColor(255, new Color(255, 255, 255, 255)));

        return flamePalete;
    }

    private void setWindowParams() {

        this.setVisible(true);
        this.setLayout(new GridBagLayout());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public BufferedImage resize(BufferedImage img, int newW, int newH) {
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH), 0, 0, null);
        g2d.dispose();
        return dimg;
    }

    public static void main(String[] args) throws IOException {
        new MyFlame();
    }

}
