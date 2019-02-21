package pl.ToolPart;

import javax.swing.*;
import java.awt.*;

//Tworzenie Informacji o połozeniu myszki
class DownPanel extends JPanel {
    private ToolPanel toolPanel;

    DownPanel(ToolPanel toolPanel) {
        super();
        this.toolPanel = toolPanel;
        setLayout(new GridLayout(1, 2));
        toolPanel.xLabel = new JLabel("x: 0");
        toolPanel.xLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 13));
        toolPanel.yLabel = new JLabel("y: 0");
        toolPanel.yLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 13));
        add(toolPanel.xLabel);
        add(toolPanel.yLabel);
    }
}
