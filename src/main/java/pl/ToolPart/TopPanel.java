package pl.ToolPart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Inicjalizacja TopPanelu
class TopPanel extends JPanel {
    private ToolPanel toolPanel;

    TopPanel(ToolPanel toolPanel) {
        super();
        this.toolPanel = toolPanel;

        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(toolPanel.width, 120));

        JComboBox shapeChoose = new JComboBox(toolPanel.shapes);
        shapeChoose.setPreferredSize(new Dimension(toolPanel.width - 10, 34));
        shapeChoose.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
        shapeChoose.addActionListener(new ShapeChooseAdapter(toolPanel.mainPanel));
        add(shapeChoose);
        toolPanel.newFigureToolPanel = new ColorPanel(toolPanel, toolPanel.mainPanel);
        add(toolPanel.newFigureToolPanel);
        toolPanel.fillCheckBox = new JCheckBox("Wypelnij Kolorem", toolPanel.mainPanel.currentFill);
        toolPanel.fillCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolPanel.mainPanel.currentFill = toolPanel.fillCheckBox.isSelected();
            }
        });
        toolPanel.fillCheckBox.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        add(toolPanel.fillCheckBox);
    }
}
