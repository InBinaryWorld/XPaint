package pl.Menu;

import pl.Figures;
import pl.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

class LoadAdapter implements ActionListener {
    private JFrame frame;
    private MainPanel mainPanel;


    LoadAdapter(JFrame frame, MainPanel mainPanel) {
        super();
        this.frame = frame;
        this.mainPanel = mainPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        File file;
        String fileDirectory;
        JFileChooser chooser = new JFileChooser();
        ArrayList<Figures> ar = new ArrayList<>();

        chooser.setDialogTitle("Load");
        chooser.setFileFilter(new MyFileFilter());
        int returnVal = chooser.showSaveDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {                     //CZY POPRAWNIE POBRANE DANE
            fileDirectory = chooser.getSelectedFile().getAbsolutePath();
            if (fileDirectory.endsWith(".txt")) {                           //CZY POPRAWNY FORMAT PLIKU
                file = new File(fileDirectory);
                try {
                    Scanner in = new Scanner(file);
                    String word = in.next();
                    while (word != null) {                                  //ZCZYTYWANIE DANYCH
                        Color color = new Color(Integer.parseInt(in.next()));
                        boolean isFill;
                        isFill = in.next().equals("true");
                        System.out.println(word);
                        int y;
                        int w;
                        int x;
                        int h;
                        switch (word) {
                            case "P":
                                x = Integer.parseInt(in.next());
                                y = Integer.parseInt(in.next());
                                w = Integer.parseInt(in.next());
                                h = Integer.parseInt(in.next());
                                ar.add(new Figures(new Rectangle2D.Double(x, y, w, h), color, isFill));
                                break;
                            case "E":
                                x = Integer.parseInt(in.next());
                                y = Integer.parseInt(in.next());
                                w = Integer.parseInt(in.next());
                                h = Integer.parseInt(in.next());
                                ar.add(new Figures(new Ellipse2D.Double(x, y, w, h), color, isFill));
                                break;
                            case "W":
                                int n = Integer.parseInt(in.next());
                                int[] xTab = new int[n];
                                int[] yTab = new int[n];
                                for (int i = 0; i < n; i++) {
                                    xTab[i] = Integer.parseInt(in.next());
                                    yTab[i] = Integer.parseInt(in.next());
                                }
                                ar.add(new Figures(new Polygon(xTab, yTab, n), color, isFill));
                                break;
                        }
                        try {
                            word = in.next();
                        } catch (NoSuchElementException e2) {
                            break;
                        }
                    }
                    in.close();
                    mainPanel.figuresList = ar;                                     //ODSWIERZANIE WIDOKU PLANELU
                    mainPanel.drawPanel.repaint();
                } catch (Exception e2) {                                           //OKNO DIALOGOWE Z OSTRZERZENIEM
                    JOptionPane.showMessageDialog(frame, "Nie wybrano pliku *.txt LUB plik jest uszkodzony",
                            "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
}
