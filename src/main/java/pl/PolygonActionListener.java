package pl;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class PolygonActionListener implements ActionListener {
    private DataPanel dataPanel;
    int i;
    Polygon pol;

    PolygonActionListener(DataPanel dataPanel, int i, Polygon pol) {
        this.dataPanel = dataPanel;
        this.i = i;
        this.pol = pol;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            double value = Double.parseDouble(dataPanel.textFieldList.get(i).getText());
            if (value >= 0) {
                int nrPoint = (i - 2) / 2;
                if ((i - 2) % 2 == 0) {    //x
                    pol.xpoints[nrPoint] = (int) value;
                } else {
                    pol.ypoints[nrPoint] = (int) value;
                }
            }
            dataPanel.mainPanel.drawPanel.repaint();
        } catch (NumberFormatException ex) {
        }
    }
}
