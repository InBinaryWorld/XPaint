package pl.ToolPart;

import pl.MainPanel;

import javax.swing.*;
import java.awt.*;

//Inicjalizacja ColorPanel
class ColorPanel extends JPanel {
    private ToolPanel toolPanel;
    JPanel colorLook;
    JButton color;


    ColorPanel(ToolPanel toolPanel, MainPanel mainPanel) {
        super();
        this.toolPanel = toolPanel;
        GridLayout layout = new GridLayout(1, 2);
        layout.setHgap(5);
        layout.setVgap(5);
        setLayout(layout);
        setPreferredSize(new Dimension(toolPanel.width - 10, 34));

        colorLook = new JPanel();
        colorLook.setBackground(mainPanel.currentColor);
        color = new JButton("Kolor");
        color.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
        color.addActionListener(new ColorButtonAdapter(mainPanel));
        add(colorLook);
        add(color);

    }


}
