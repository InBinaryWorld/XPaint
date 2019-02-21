package pl;

import java.awt.*;

public class Figures {
    public Shape shape;
    public Color color;
    public Boolean isFill;

    public Figures(Shape shape, Color color, Boolean isFill) {
        this.shape = shape;
        this.isFill = isFill;
        this.color = color;
    }
}
