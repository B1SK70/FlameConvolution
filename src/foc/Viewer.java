package foc;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import static java.lang.Thread.sleep;

public class Viewer extends Canvas implements Runnable {

    BufferStrategy bs;
    Graphics g;

    private int w;
    private int h;
    private BufferedImage normalImage;
    private BufferedImage convolutedImage;
    private Flame normalFire;
    private Flame convolutedFlame;

    public Viewer(int w, int h, BufferedImage normalImage, BufferedImage convolutedImage, Flame normalFire, Flame convolutedFlame) {
        this.w = w;
        this.h = h;
        this.normalImage = normalImage;
        this.convolutedImage = convolutedImage;
        this.normalFire = normalFire;
        this.convolutedFlame = convolutedFlame;
        
        setBackground(Color.black);
        setSize(w, h);
    }

    public void paint() {

        bs = this.getBufferStrategy();
        g = bs.getDrawGraphics();

        if (bs == null || g == null) {
            return;
        }

        g.drawImage(normalImage, 0, 0, null);
        g.drawImage(convolutedImage, 300, 0, null);
        g.drawImage(normalFire.getFlameImage(), 600, 0, null);
        g.drawImage(convolutedFlame.getFlameImage(), 0, 200, null);

        bs.show();
        g.dispose();

    }

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
        }
        createBufferStrategy(2);

        while (true) {
            paint();
            try {
                sleep(33);
            } catch (Exception e) {
            }

        }
    }

}
