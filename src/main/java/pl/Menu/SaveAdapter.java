package pl.Menu;

import pl.Figures;
import pl.MainPanel;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

class SaveAdapter implements ActionListener {
    private JFrame frame;
    private MainPanel mainPanel;

    SaveAdapter(JFrame frame, MainPanel mainPanel) {
        super();
        this.frame = frame;
        this.mainPanel = mainPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String fileDirectory;
        JFileChooser chooser = new JFileChooser();
        String input;

        chooser.setFileFilter(new MyFileFilter());
        int returnVal = chooser.showSaveDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            fileDirectory = chooser.getSelectedFile().getAbsolutePath();
            if (!fileDirectory.endsWith(".txt"))                                 //USTALANIE PORAWNEGO FORMATU PLIKU
                fileDirectory = chooser.getSelectedFile().getAbsolutePath() + ".txt";

            PrintWriter zapis;

            try {
                zapis = new PrintWriter(fileDirectory);                           //ZAPIS LISTY FIUGUR DO PLIKU
                for (Figures f : mainPanel.figuresList) {
                    String name = f.shape.getClass().getCanonicalName();
                    switch (name) {
                        case "java.awt.geom.Rectangle2D.Double":
                            input = "P ";
                            zapis.print(input);
                            zapis.print(f.color.getRGB() + " ");
                            zapis.print(f.isFill + " ");
                            zapis.print(f.shape.getBounds().x + " ");
                            zapis.print(f.shape.getBounds().y + " ");
                            zapis.print(f.shape.getBounds().width + " ");
                            zapis.print(f.shape.getBounds().height + " ");

                            break;
                        case "java.awt.geom.Ellipse2D.Double":
                            input = "E ";
                            zapis.print(input);
                            zapis.print(f.color.getRGB() + " ");
                            zapis.print(f.isFill + " ");
                            zapis.print(f.shape.getBounds().x + " ");
                            zapis.print(f.shape.getBounds().y + " ");
                            zapis.print(f.shape.getBounds().width + " ");
                            zapis.print(f.shape.getBounds().height + " ");

                            break;
                        case "java.awt.Polygon":
                            Polygon pol = (Polygon) f.shape;
                            input = "W ";
                            zapis.print(input);
                            zapis.print(f.color.getRGB() + " ");
                            zapis.print(f.isFill + " ");
                            zapis.print(pol.npoints + " ");
                            for (int i = 0; i < pol.npoints; i++) {
                                zapis.print(pol.xpoints[i] + " ");
                                zapis.print(pol.ypoints[i] + " ");
                            }

                            break;
                    }
                }
                zapis.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }

}
