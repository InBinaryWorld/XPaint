package pl;

import javax.swing.*;
import java.awt.*;

public class XPaint extends JFrame {
    MainPanel mainPanel;

    private XPaint() {              //Tworzenie Elementów JFrame
        this.setTitle("XPaint");
        setLocation(150, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1200, 675));
        this.add(new MainPanel(this));
        pack();

    }

    public static void main(String[] args) {
        XPaint xPaintFrame = new XPaint();
        xPaintFrame.setVisible(true);

    }
}