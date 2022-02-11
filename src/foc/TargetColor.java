package foc;

import java.awt.Color;

public class TargetColor {
    private int temperature;
    private Color color = new Color(255,255,255,255);
    
    public TargetColor(int temperature, Color color){
        this.temperature = temperature;
        this.color = color;
    }
    
    public int getTemperature() {
        return temperature;
    }
    
    public Color getColor() {
        return color;
    }
    
    @Override
    public String toString() {
    
        return temperature + ": " + color;
    }
}
