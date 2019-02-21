package pl.ToolPart;

import pl.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//BIERZACE uSTAWIENIA KOLORU
class ColorButtonAdapter implements ActionListener {
    MainPanel mainPanel;

    ColorButtonAdapter(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Color c = JColorChooser.showDialog(mainPanel, "Kolor Figur", Color.WHITE);
        if (c != null)
            mainPanel.currentColor = c;
        mainPanel.toolPanel.newFigureToolPanel.colorLook.setBackground(mainPanel.currentColor);
    }
}
