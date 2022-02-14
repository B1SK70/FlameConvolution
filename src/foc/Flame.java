package foc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import static java.lang.Thread.sleep;
import java.util.ArrayList;

public class Flame extends BufferedImage implements Runnable {

    private int w;
    private int h;
    private int[][] heatMap;
    private FlamePalete flamePalete;
    private BufferedImage background;
    private BufferedImage unfinishedFlame;
    private BufferedImage rawFlames;
    private ArrayList<String> flamables;
    private boolean backgroundShapeDetected = false;

    
    boolean paused = false;

    //Parametrizables
    private int sparks = 65;
    private double heatLoss = 1.5;
    private int fireMsSpeed = 25;

    //Convolution parameters
    private int convolutionSensivility = 100;

    public Flame(int w, int h, BufferedImage background) {
        super(w, h, BufferedImage.TYPE_INT_ARGB);
        this.w = w;
        this.h = h;
        if (background != null) {
            this.background = resize(background, w, h);
            flamables = new Convolution(this.background).getFlamables(convolutionSensivility);
            if (flamables.size() > 0) {
                backgroundShapeDetected = true;
            }
        }

        unfinishedFlame = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        heatMap = new int[w][h];

    }

    public BufferedImage resize(BufferedImage img, int newW, int newH) {
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH), 0, 0, null);
        g2d.dispose();
        return dimg;
    }

    public void setFlamePalete(FlamePalete flamePalete) {
        this.flamePalete = flamePalete;
    }

    public BufferedImage getFlameImage() {
        return this;
    }

    public void setFireMsSpeed(int ms) {
        fireMsSpeed = ms;
    }

    public void setHeatLoss(int hl) {
        heatLoss = hl * 0.025;
    }

    public void setSparks(int sprks) {
        sparks = sprks;
    }

    public void switchPauseState() {
        paused = !paused;
    }

    public void restart() {
        heatMap = new int[w][h];
        updateImage();
    }

    public void reConvolutionate(int sensivility) {
        flamables = new Convolution(this.background).getFlamables(sensivility);
    }

    private void sparks() {
        if (backgroundShapeDetected) {
            for (String flamable : flamables) {
                if (((int) (Math.random() * 100 + 1)) < sparks) {
                    String coords[] = flamable.split("_");

                    heatMap[Integer.parseInt(coords[0])][Integer.parseInt(coords[1])] = 255;

                }
            }
        } else {
            for (int x = 0; x < w; x++) {
                if (((int) (Math.random() * 100 + 1)) < sparks) {
                    heatMap[x][h - 1] = 255;

                }
            }
        }
    }

    private void heatDispersion() {
        int[][] newHeatMapStatus = new int[w][h];

        for (int x = 1; x < w - 1; x++) {
            for (int y = 1; y < h - 1; y++) {

                //IF ( HEAT > 0 )
                if ((int) ((heatMap[x - 1][y + 1] + heatMap[x][y + 1] + heatMap[x + 1][y + 1] + (heatMap[x][y] * 0.3)) / (3 + 0.3) - 1) > 0) {
                    newHeatMapStatus[x][y] = (int) ((heatMap[x - 1][y + 1] + heatMap[x][y + 1] + heatMap[x + 1][y + 1] + (heatMap[x][y] * 0.3)) / (3 + 0.3) - heatLoss);
                }

            }
        }
        heatMap = newHeatMapStatus;
    }

    private void updateImage() {
        rawFlames = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        
        if (background != null) {
            unfinishedFlame.getGraphics().drawImage(background, 0, 0, null);
        } else {
            unfinishedFlame = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        }

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                if (heatMap[x][y] != 0) {
                    rawFlames.setRGB(x, y, flamePalete.getColor(heatMap[x][y]));
                }
            }
        }

        unfinishedFlame.getGraphics().drawImage(rawFlames, 0, 0, null);
        this.getGraphics().drawImage(unfinishedFlame, 0, 0, null);
    }

    private void fireTick() {
        sparks();
        heatDispersion();
        updateImage();
    }

    @Override
    public void run() {
        while (true) {

            if (!paused) {
                fireTick();
            }

            try {
                sleep(fireMsSpeed);
            } catch (Exception e) {
                System.out.println("Flame class" + e);
            }
        }
    }
}
