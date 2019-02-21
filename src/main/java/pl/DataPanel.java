package pl;

import pl.ToolPart.ToolPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.ArrayList;

public class DataPanel extends JPanel {
  private ArrayList<JLabel> labelList = new ArrayList<>();
  ArrayList<JTextField> textFieldList = new ArrayList<>();
  private JButton removeButton, colorButton;
  public MainPanel mainPanel;
  private JCheckBox checkBox;


  private void initRectangle(Figures f, Rectangle2D r) {
    foo(f, r.getX(), r.getY(), r.getWidth(), r.getHeight());
  }

  private void initEllipse(Figures f, Ellipse2D el) {
    foo(f, el.getX(), el.getY(), el.getWidth(), el.getHeight());
  }

  private void addEllipseActionListeners(Figures f, Ellipse2D el) {
    addAL(f, el);
  }

  private void addRectangleActionListeners(Figures f, Rectangle2D r) {
    addAL(f, r);
  }

  private void foo(Figures f, double x, double y, double width, double height) {
    labelList.add(new JLabel("X:"));
    labelList.add(new JLabel("Y:"));
    labelList.add(new JLabel("Szerokosc:"));
    labelList.add(new JLabel("Wysokosc:"));
    labelList.add(new JLabel("Wypelnienie:"));
    textFieldList.add(new JTextField("" + x));
    textFieldList.add(new JTextField("" + y));
    textFieldList.add(new JTextField("" + width));
    textFieldList.add(new JTextField("" + height));
    colorButton = new JButton("Kolor");
    checkBox = new JCheckBox("", f.isFill);
    removeButton = new JButton("Usun");

    for (JLabel l : labelList) {
      l.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
      l.setHorizontalAlignment(JLabel.CENTER);
    }
    for (JTextField t : textFieldList) {
      t.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
      t.setHorizontalAlignment(JLabel.CENTER);
    }
    colorButton.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
    checkBox.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
    removeButton.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
  }

  private void addAL(Figures f, RectangularShape r) {
    textFieldList.get(0).addActionListener(e -> {
      try {

        if (Double.parseDouble(textFieldList.get(0).getText()) >= 0)
          r.setFrame(Double.parseDouble(textFieldList.get(0).getText()), r.getY(), r.getWidth(), r.getHeight());
        mainPanel.drawPanel.repaint();
      } catch (NumberFormatException ignored) {
      }
    });
    textFieldList.get(1).addActionListener(e -> {
      try {

        if (Double.parseDouble(textFieldList.get(1).getText()) >= 0)
          r.setFrame(r.getX(), Double.parseDouble(textFieldList.get(1).getText()), r.getWidth(), r.getHeight());
        mainPanel.drawPanel.repaint();
      } catch (NumberFormatException ignored) {
      }
    });
    textFieldList.get(2).addActionListener(e -> {
      try {
        if (Double.parseDouble(textFieldList.get(2).getText()) >= 0)
          r.setFrame(r.getX(), r.getY(), Double.parseDouble(textFieldList.get(2).getText()), r.getHeight());
        mainPanel.drawPanel.repaint();
      } catch (NumberFormatException ignored) {
      }
    });
    textFieldList.get(3).addActionListener(e -> {
      try {
        if (Integer.parseInt(textFieldList.get(3).getText()) >= 0)
          r.setFrame(r.getX(), r.getY(), r.getWidth(), Double.parseDouble(textFieldList.get(3).getText()));
        mainPanel.drawPanel.repaint();
      } catch (NumberFormatException ignored) {
      }
    });
    foo3();
    foo2(f);
  }


  private void addRectangleComponents() {
    foo1();
  }

  private void addEllipseComponents() {
    foo1();
  }

  private void foo1() {
    add(colorButton);
    add(removeButton);
    add(labelList.get(4));
    add(checkBox);
    add(labelList.get(0));
    add(textFieldList.get(0));
    add(labelList.get(1));
    add(textFieldList.get(1));
    add(labelList.get(2));
    add(textFieldList.get(2));
    add(labelList.get(3));
    add(textFieldList.get(3));
  }

  private void foo2(Figures f) {
    checkBox.addActionListener(e -> {
      f.isFill = checkBox.isSelected();
      mainPanel.drawPanel.repaint();
    });
    colorButton.addActionListener(e -> {
      Color c = JColorChooser.showDialog(mainPanel, "Kolor Figur", Color.WHITE);
      if (c != null)
        f.color = c;
      mainPanel.drawPanel.repaint();
    });
  }

  private void foo3() {
    removeButton.addActionListener(e -> {
      ToolPanel t = mainPanel.toolPanel;
      mainPanel.figuresList.remove(mainPanel.figuresList.size() - 1);
      mainPanel.drawPanel.isPick = false;
      t.remove(t.scroll);
      t.dataPanel = new DataPanel();
      t.scroll = new JScrollPane(t.dataPanel);
      t.add(t.scroll, BorderLayout.CENTER);
      t.invalidate();
      t.validate();
      mainPanel.drawPanel.repaint();
    });
  }


  private int getPolygonWidth(Polygon pol) {
    int min = pol.xpoints[0];
    int max = pol.xpoints[0];
    for (int x : pol.xpoints) {
      if (min > x)
        min = x;
      if (max < x)
        max = x;
    }
    return max - min;
  }

  private int getPolygonHeight(Polygon pol) {
    int min = pol.ypoints[0];
    int max = pol.ypoints[0];
    for (int y : pol.ypoints) {
      if (min > y)
        min = y;
      if (max < y)
        max = y;
    }
    return max - min;
  }

  private void initPolygon(Figures f, Polygon pol) {
    labelList.add(new JLabel("Wypelnienie:"));
    labelList.add(new JLabel("Szerokosc:"));
    labelList.add(new JLabel("Wysokosc:"));
    textFieldList.add(new JTextField("" + getPolygonWidth(pol)));
    textFieldList.add(new JTextField("" + getPolygonHeight(pol)));
    for (int i = 0; i < pol.npoints; i++) {
      labelList.add(new JLabel((i + 1) + ".X: "));
      labelList.add(new JLabel((i + 1) + ".Y: "));
      textFieldList.add(new JTextField("" + pol.xpoints[i]));
      textFieldList.add(new JTextField("" + pol.ypoints[i]));
    }
    colorButton = new JButton("Kolor");
    checkBox = new JCheckBox("", f.isFill);
    removeButton = new JButton("Usun");
    for (JLabel l : labelList) {
      l.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
      l.setHorizontalAlignment(JLabel.CENTER);
    }
    for (JTextField t : textFieldList) {
      t.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
      t.setHorizontalAlignment(JLabel.CENTER);
    }
    colorButton.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
    checkBox.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
    removeButton.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
  }

  private void addPolygonActionListeners(Figures f, Polygon pol) {
    textFieldList.get(0).addActionListener(e -> {
      try {
        double width = Double.parseDouble(textFieldList.get(0).getText());
        if (width >= 0) {
          double x = (width / getPolygonWidth(pol));
          for (int i = 0; i < pol.xpoints.length; i++) {
            double c = x * (pol.xpoints[i] - pol.xpoints[0]);
            pol.xpoints[i] = pol.xpoints[0] + (int) c;
          }
        }
        mainPanel.drawPanel.repaint();
      } catch (NumberFormatException ignored) {
      }
    });
    textFieldList.get(1).addActionListener(e -> {
      try {
        double height = Double.parseDouble(textFieldList.get(1).getText());
        if (height >= 0) {
          Double x = height / getPolygonHeight(pol);
          for (int i = 0; i < pol.ypoints.length; i++) {
            double c = x * (pol.ypoints[i] - pol.ypoints[0]);
            pol.ypoints[i] = pol.ypoints[0] + (int) c;
          }
        }
        mainPanel.drawPanel.repaint();
      } catch (NumberFormatException ignored) {
      }
    });
    for (int i = 2; i < textFieldList.size(); i++)
      textFieldList.get(i).addActionListener(new PolygonActionListener(this, i, pol));
    foo3();
    foo2(f);
  }


  private void addPolygonComponents() {
    add(colorButton);
    add(removeButton);
    add(labelList.get(0));
    add(checkBox);
    for (int i = 0; i < textFieldList.size(); i++) {
      add(labelList.get(i + 1));
      add(textFieldList.get(i));
    }
  }

  //WYWOLANIE METOD TWORZACYCH DATAPANEL
  DataPanel(MainPanel mainPanel, Figures f, String shape) {
    super();
    this.mainPanel = mainPanel;

    switch (shape) {
      case "Prostokat": {
        Rectangle2D r = (Rectangle2D) f.shape;
        setPreferredSize(new Dimension(mainPanel.toolPanel.width - 30, 240));
        GridLayout layout = new GridLayout(6, 2);
        layout.setVgap(5);
        layout.setHgap(5);
        setLayout(layout);

        initRectangle(f, r);
        addRectangleActionListeners(f, r);
        addRectangleComponents();
        break;
      }
      case "Elipsa": {
        Ellipse2D el = (Ellipse2D) f.shape;
        setPreferredSize(new Dimension(mainPanel.toolPanel.width - 30, 240));
        GridLayout layout = new GridLayout(6, 2);
        layout.setVgap(5);
        layout.setHgap(5);
        setLayout(layout);

        initEllipse(f, el);
        addEllipseActionListeners(f, el);
        addEllipseComponents();

        break;
      }
      case "Wielokat": {
        Polygon pol = (Polygon) f.shape;
        setPreferredSize(new Dimension(mainPanel.toolPanel.width - 30, (4 + 2 * pol.npoints) * 40));
        GridLayout layout = new GridLayout(4 + 2 * pol.npoints, 2);
        layout.setVgap(5);
        layout.setHgap(5);
        setLayout(layout);

        initPolygon(f, pol);
        addPolygonActionListeners(f, pol);
        addPolygonComponents();
        break;
      }
    }

  }

  public DataPanel() {
    super();
  }

}
