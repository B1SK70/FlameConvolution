package foc;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

public class FlamePalete {

    private ArrayList<TargetColor> targetColors = new ArrayList<TargetColor>();
    private int[] colors;

    public void addTargetColor(TargetColor tc) {
        targetColors.add(tc);
        createColors();
    }

    public void removeTargetColor(int index) {
        targetColors.remove(index);
        createColors();
    }

    public int getColor(int temp) {
        if (colors == null) {
            createColors();
        }
        return colors[temp];
    }

    public ArrayList<TargetColor> getTargets() {
        return targetColors;
    }

    private void createColors() {
        sortTargetColors();
        colors = new int[256];
        for (int i = 0; i < targetColors.size() - 1; i++) {
            interpolateColors(targetColors.get(i), targetColors.get(i + 1));
        }
    }

    private void interpolateColors(TargetColor from, TargetColor to) {

        Color fromColor = from.getColor();
        Color toColor = to.getColor();

        int[] fromColors = new int[]{
            fromColor.getRed(),
            fromColor.getGreen(),
            fromColor.getBlue(),
            fromColor.getAlpha()
        };

        int[] toColors = new int[]{
            toColor.getRed(),
            toColor.getGreen(),
            toColor.getBlue(),
            toColor.getAlpha()
        };

        float valuesToFill = (from.getTemperature() - to.getTemperature()) - 1;

        float redVariation = (fromColors[0] - toColors[0]) / valuesToFill;
        float greenVariation = (fromColors[1] - toColors[1]) / valuesToFill;
        float blueVariation = (fromColors[2] - toColors[2]) / valuesToFill;
        float alphaVariation = (fromColors[3] - toColors[3]) / valuesToFill;

        int i = 0;
        for (int temp = from.getTemperature(); temp < to.getTemperature(); temp++) {
            Color newColor = new Color(
                    (int) (fromColors[0] + (redVariation * i)),
                    (int) (fromColors[1] + (greenVariation * i)),
                    (int) (fromColors[2] + (blueVariation * i)),
                    (int) (fromColors[3] + (alphaVariation * i)));
            colors[temp] = newColor.getRGB();
            i++;
        }
    }

    private void sortTargetColors() {

        int temps[] = new int[targetColors.size()];
        for (int i = 0; i < targetColors.size(); i++) {
            temps[i] = targetColors.get(i).getTemperature();
        }

        Arrays.sort(temps);

        ArrayList<TargetColor> newTargetColors = new ArrayList<TargetColor>();
        for (int i = 0; i < temps.length; i++) {
            for (TargetColor tc : targetColors) {
                if (tc.getTemperature() == temps[i]) {
                    newTargetColors.add(tc);
                    break;
                }
            }
        }
        targetColors = newTargetColors;
    }
    
    public void reload() {
        createColors();
    }
}
