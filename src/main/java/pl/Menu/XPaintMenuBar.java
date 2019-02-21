package pl.Menu;

import pl.MainPanel;

import javax.swing.*;
import java.awt.*;

public class XPaintMenuBar extends JMenuBar {
  private JMenuItem createJMI(String text) {
    JMenuItem item = new JMenuItem(text);
    item.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
    return item;
  }

  private JLabel createLabel(String text, int size) {
    JLabel label = new JLabel(text);
    label.setFont(new Font(Font.SERIF, Font.PLAIN, size));
    return label;
  }

  ///INICJALIZACJA I KONFIGURACJA PASKA MENU
  public XPaintMenuBar(JFrame frame, MainPanel mainPanel) {
    super();
    ///TWORZENIE OBIEKTÓW MENU I DODAWANIE DO MENUBAR
    JMenuItem infoMenuItem = createJMI("Info");
    JMenuItem exitMenuItem = createJMI("Exit");
    JMenuItem loadMenuItem = createJMI("Load");
    JMenuItem saveMenuItem = createJMI("Save");
    JMenu file = new JMenu("File");
    file.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
    file.add(loadMenuItem);
    file.add(saveMenuItem);
    file.addSeparator();
    file.add(infoMenuItem);
    file.addSeparator();
    file.add(exitMenuItem);
    exitMenuItem.addActionListener(e -> System.exit(0));
    infoMenuItem.addActionListener(e -> {
      JDialog dial;
      dial = new JDialog(frame, "Informacje", true);
      dial.setResizable(false);
      dial.setLocation(600, 250);
      dial.setLayout(new GridLayout(8, 1));
      dial.add(createLabel("", 17));
      dial.add(createLabel("XPaint", 17));
      dial.add(createLabel("", 15));
      dial.add(createLabel("Edytor graficzny umożliwiający", 15));
      dial.add(createLabel(" tworzenie i edycję figur geometrycznych.", 15));
      dial.add(createLabel("", 15));
      dial.add(createLabel("Autor: Szafraniak Krzysztof", 15));
      JButton button = new JButton("OK");
      button.addActionListener(e1 -> dial.setVisible(false));
      dial.add(button);
      dial.pack();
      dial.setVisible(true);
    });
    saveMenuItem.addActionListener(new SaveAdapter(frame, mainPanel));
    loadMenuItem.addActionListener(new LoadAdapter(frame, mainPanel));
    add(file);

  }
}
