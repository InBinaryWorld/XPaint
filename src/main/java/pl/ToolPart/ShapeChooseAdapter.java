package pl.ToolPart;

import pl.MainPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// WYBÓR BIERZACEGO KSZTALTU
class ShapeChooseAdapter implements ActionListener {
    MainPanel mainPanel;

    ShapeChooseAdapter(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox) e.getSource();
        mainPanel.currentShape = (String) cb.getSelectedItem();
        mainPanel.drawPanel.buildPoints.clear();                    //CZYSZCZENIE EWENTUALNYCH AKCJI
        mainPanel.drawPanel.squares.clear();
        mainPanel.drawPanel.adapter.isMoved = false;
        mainPanel.drawPanel.adapter.isFigureBuilding = false;
        mainPanel.drawPanel.adapter.isResizeing = false;
        mainPanel.drawPanel.isPick = false;
        mainPanel.drawPanel.isBuild = false;
    }
}
