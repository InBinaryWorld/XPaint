package pl.ToolPart;

import pl.DataPanel;
import pl.MainPanel;

import javax.swing.*;
import java.awt.*;

public class ToolPanel extends JPanel {
    public MainPanel mainPanel;
    public JScrollPane scroll;
    public JLabel xLabel;
    public JLabel yLabel;
    public ColorPanel newFigureToolPanel;
    public JCheckBox fillCheckBox;
    public DataPanel dataPanel;
    public JPanel panel;
    public String[] shapes = {"Prostokat", "Elipsa", "Wielokat"};
    public int width = 200;

    //Inicjalizacja ToolPanel
    public ToolPanel(MainPanel mainPanel) {
        super();
        this.mainPanel = mainPanel;
        setPreferredSize(new Dimension(width, 100));
        setLayout(new BorderLayout());

        add(new TopPanel( this), BorderLayout.NORTH);
        scroll = new JScrollPane();
        dataPanel = new DataPanel();
        panel = new JPanel(new FlowLayout());
        panel.add(dataPanel);
        scroll.add(panel);
        add(scroll);
        add(new DownPanel(this), BorderLayout.SOUTH);


    }
}
