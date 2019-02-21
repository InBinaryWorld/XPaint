package pl;

import pl.Menu.XPaintMenuBar;
import pl.ToolPart.ToolPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainPanel extends JPanel {
    private JFrame frame;
    public Color currentColor = Color.PINK;
    public ToolPanel toolPanel;
    public DrawPanel drawPanel;
    public String currentShape = "Prostokat";
    public boolean currentFill = true;
    public ArrayList<Figures> figuresList = new ArrayList<>();


    public MainPanel(JFrame frame) {
        initGUI();
        this.frame = frame;
    }

    private void initGUI() {    //Inicjalizacja MainPanel
        this.setLayout(new BorderLayout());
        XPaintMenuBar menuBar = new XPaintMenuBar(frame, this);
        toolPanel = new ToolPanel(this);
        this.add(menuBar, BorderLayout.NORTH);
        this.add(toolPanel, BorderLayout.WEST);

        drawPanel = new DrawPanel(this);
        drawPanel.setBackground(Color.WHITE);
        this.add(drawPanel, BorderLayout.CENTER);

    }
}
