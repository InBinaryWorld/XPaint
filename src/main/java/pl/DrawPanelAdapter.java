package pl;

import pl.ToolPart.ToolPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DrawPanelAdapter implements MouseListener, MouseMotionListener {
  private MainPanel mainPanel;
  public Boolean isFigureBuilding = false;
  public Boolean isResizeing = false;
  private int whichSquareIsPressed;
  public Boolean isMoved = false;
  private Point catchPoint;
  Figures pickedFigure;
  String pickedFigureString;

  ///CZY KTRAS Z FIGUR ZAWIERA EVENT
  private boolean isFigureContainEvent(MouseEvent e) {
    for (Figures f : mainPanel.figuresList) {
      if (f.shape.contains(e.getPoint()))
        return true;
    }
    return false;
  }

  ///POTREBNY WARUNEJK
  private boolean needed(MouseEvent e) {
    if (isFigureContainEvent(e) && mainPanel.drawPanel.isPick) {
      return whichFigureIsPicked(e) == mainPanel.figuresList.size() - 1;
    }
    return false;
  }

  ///CZY WYBRANO KWADRACIK
  private boolean isSquareContainEvent(MouseEvent e) {
    for (Rectangle2D r : mainPanel.drawPanel.squares) {
      if (r.contains(e.getPoint()))
        return true;
    }
    return false;
  }

  ///FIGURA O JAKIM INDEKSIE ZOSTALA WYBRANA
  private int whichFigureIsPicked(MouseEvent e) {
    int i = 0;
    int wynik = -1;
    for (Figures f : mainPanel.figuresList) {
      if (f.shape.contains(e.getPoint()))
        wynik = i;
      i++;
    }
    return wynik;
  }

  ///KTORY KWADRACIK WYBRANO
  private int getWhichSquareIsPressed(MouseEvent e) {
    int i = 0;
    for (Rectangle2D r : mainPanel.drawPanel.squares) {
      if (r.contains(e.getPoint()))
        return i;
      i++;
    }
    return -1;
  }

  ///PRZESUWANIE WYBRANEJ FIGURY NA KONIEC LISTY==WYSUWANIE NA PIERWSZY PLAN
  private ArrayList<Figures> newFigurelist(int i) {
    ArrayList<Figures> ar = new ArrayList<>();
    for (int x = 0; x < mainPanel.figuresList.size(); x++) {
      if (i != x)
        ar.add(mainPanel.figuresList.get(x));
    }
    ar.add(mainPanel.figuresList.get(i));
    return ar;
  }

  ///AKTUALIZOWANIE DATAPANEL
  private void setNewDataPanel(boolean empty) {
    ToolPanel t = mainPanel.toolPanel;
    t.remove(t.scroll);
    t.panel = new JPanel(new FlowLayout());
    if (empty)
      t.panel.add(new DataPanel());
    else
      t.panel.add(new DataPanel(mainPanel, pickedFigure, pickedFigureString));
    t.scroll = new JScrollPane(t.panel);
    t.add(t.scroll, BorderLayout.CENTER);
    t.invalidate();
    t.validate();
  }

  public void mousePressed(MouseEvent e) {

    if (mainPanel.drawPanel.isBuild) {                      ///JESLI W BUDOWIE NIC NIE ROBI
    } else if (isSquareContainEvent(e)) {                   ///USTAWIANE FLAG
      isResizeing = true;
      whichSquareIsPressed = getWhichSquareIsPressed(e);
    } else if (needed(e)) {                                 ///USTAWIANIE FLAG
      isMoved = true;
      catchPoint = new Point(e.getPoint());
    } else {                                                ///FLAGA MOZLIWEGO MOCZTKU RYSOWANIA
      ///START RYSOWANIE
      if (mainPanel.currentShape.equals("Prostokat")) {
        isFigureBuilding = true;
        mainPanel.drawPanel.buildPoints.clear();
        mainPanel.drawPanel.buildPoints.add(e.getPoint());
      } else if (mainPanel.currentShape.equals("Elipsa")) {
        isFigureBuilding = true;
        mainPanel.drawPanel.buildPoints.clear();
        mainPanel.drawPanel.buildPoints.add(e.getPoint());
      }
    }
  }

  public void mouseClicked(MouseEvent e) {
    if (mainPanel.drawPanel.isBuild) {                             ///BUDOWA WIELOKATA
      if (mainPanel.currentShape.equals("Wielokat")) {
        if (isSquareContainEvent(e)) {
          int buildPointSize = mainPanel.drawPanel.buildPoints.size();
          if (buildPointSize > 1) {
            int x[] = new int[buildPointSize];
            int y[] = new int[buildPointSize];
            int i = 0;
            for (Point p : mainPanel.drawPanel.buildPoints) {
              x[i] = p.x;
              y[i] = p.y;
              i++;
            }
            Polygon pol = new Polygon(x, y, buildPointSize);
            mainPanel.figuresList.add(new Figures(pol, mainPanel.currentColor, mainPanel.currentFill));
            mainPanel.drawPanel.squares.clear();

          }
          mainPanel.drawPanel.isBuild = false;
          mainPanel.drawPanel.buildPoints.clear();
          mainPanel.drawPanel.squares.clear();
          mainPanel.drawPanel.repaint();

        } else {
          mainPanel.drawPanel.buildPoints.add(e.getPoint());
          mainPanel.drawPanel.repaint();

        }
      }
    } else if (isSquareContainEvent(e)) {

    } else if (isFigureContainEvent(e)) {               ///WYBIERANIE FIGURY

      mainPanel.drawPanel.squares.clear();
      mainPanel.drawPanel.isPick = true;
      pickedFigure = mainPanel.figuresList.get(whichFigureIsPicked(e));
      String name = pickedFigure.shape.getClass().getCanonicalName();
      mainPanel.figuresList = newFigurelist(whichFigureIsPicked(e));

      switch (name) {
        case "java.awt.geom.Rectangle2D.Double":
          pickedFigureString = "Prostokat";

          break;
        case "java.awt.geom.Ellipse2D.Double":
          pickedFigureString = "Elipsa";

          break;
        case "java.awt.Polygon":
          pickedFigureString = "Wielokat";
          break;
      }

      setNewDataPanel(false);

      mainPanel.drawPanel.repaint();
    } else {
      mainPanel.drawPanel.isPick = false;
      setNewDataPanel(true);


      if (mainPanel.currentShape.equals("Wielokat")) {            ///DTART BUDOWY WIELOKATA
        mainPanel.drawPanel.squares.clear();
        mainPanel.drawPanel.isBuild = true;
        mainPanel.drawPanel.buildPoints.clear();
        mainPanel.drawPanel.buildPoints.add(e.getPoint());
        mainPanel.drawPanel.movePoint = e.getPoint();
      }
      mainPanel.drawPanel.repaint();
    }
  }

  public void mouseReleased(MouseEvent e) {
    if (isFigureBuilding) {
      if (mainPanel.drawPanel.isBuild) {
        if (mainPanel.currentShape.equals("Prostokat")) {

          ArrayList<Point> p = mainPanel.drawPanel.buildPoints;
          int x = Math.min(p.get(0).x, e.getX());
          int y = Math.min(p.get(0).y, e.getY());
          int w = Math.abs(p.get(0).x - e.getX());
          int h = Math.abs(p.get(0).y - e.getY());
          Rectangle2D r = new Rectangle2D.Double(x, y, w, h);
          mainPanel.figuresList.add(new Figures(r, mainPanel.currentColor, mainPanel.currentFill));
          mainPanel.drawPanel.repaint();

          isFigureBuilding = false;
          mainPanel.drawPanel.isBuild = false;
        } else if (mainPanel.currentShape.equals("Elipsa")) {

          ArrayList<Point> p = mainPanel.drawPanel.buildPoints;
          int x = Math.min(p.get(0).x, e.getX());
          int y = Math.min(p.get(0).y, e.getY());
          int w = Math.abs(p.get(0).x - e.getX());
          int h = Math.abs(p.get(0).y - e.getY());
          Ellipse2D el = new Ellipse2D.Double(x, y, w, h);
          mainPanel.figuresList.add(new Figures(el, mainPanel.currentColor, mainPanel.currentFill));
          mainPanel.drawPanel.repaint();

          isFigureBuilding = false;
          mainPanel.drawPanel.isBuild = false;

        }
      } else {                                                      ///WYLACZANIE FLAG
        isFigureBuilding = false;
      }
    }
    if (isResizeing) {
      isResizeing = false;
    }
    if (isMoved) {
      isMoved = false;
    }
  }

  public void mouseEntered(MouseEvent e) {
  }

  public void mouseExited(MouseEvent e) {
  }

  public void mouseDragged(MouseEvent e) {
    mainPanel.toolPanel.xLabel.setText("x: " + e.getX());            ///AKTUALIZACJA POZYCJI KURSORA
    mainPanel.toolPanel.yLabel.setText("y: " + e.getY());

    if (isFigureBuilding) {                                         ///BUDOWA FIGUR
      if (mainPanel.currentShape.equals("Prostokat")) {
        mainPanel.drawPanel.isBuild = true;
        if (mainPanel.drawPanel.buildPoints.size() < 2)
          mainPanel.drawPanel.buildPoints.add(e.getPoint());
        else
          mainPanel.drawPanel.buildPoints.set(1, e.getPoint());
        mainPanel.drawPanel.repaint();
      } else if (mainPanel.currentShape.equals("Elipsa")) {
        mainPanel.drawPanel.isBuild = true;
        if (mainPanel.drawPanel.buildPoints.size() < 2)
          mainPanel.drawPanel.buildPoints.add(e.getPoint());
        else
          mainPanel.drawPanel.buildPoints.set(1, e.getPoint());
      }
    }
    if (isMoved) {                                                          ///PRZESUWANIE FIGUR
      switch (pickedFigureString) {
        case "Prostokat":
          Rectangle2D r = (Rectangle2D) pickedFigure.shape;
          r.setFrame(r.getX() + (e.getX() - catchPoint.x), r.getY() + (e.getY() - catchPoint.y), r.getWidth(), r.getHeight());

          break;
        case "Elipsa":
          Ellipse2D el = (Ellipse2D) pickedFigure.shape;
          el.setFrame(el.getX() + (e.getX() - catchPoint.x), el.getY() + (e.getY() - catchPoint.y), el.getWidth(), el.getHeight());

          break;
        case "Wielokat":
          Polygon pol = (Polygon) pickedFigure.shape;
          int xTab[] = pol.xpoints;
          int yTab[] = pol.ypoints;
          int n = pol.npoints;
          for (int i = 0; i < n; i++) {
            xTab[i] = xTab[i] + (e.getX() - catchPoint.x);          ///KORYGOWANIE O ZMIANE OD ODTATIEJ POZYZJI
            yTab[i] = yTab[i] + (e.getY() - catchPoint.y);
          }
          pickedFigure.shape = new Polygon(xTab, yTab, n);
          break;
      }
      catchPoint = e.getPoint();                                      ///AKTUALIZACJA OSTSTNIEJ POZYCJI

      setNewDataPanel(false);
    }
    if (isResizeing) {                                                  ///ZMIANA ROZMIARU
      if (pickedFigureString.equals("Prostokat")) {

        Rectangle2D r = (Rectangle2D) pickedFigure.shape;
        int width = e.getX() - (int) r.getX();
        if (width < 0) width = 1;
        int height = e.getY() - (int) r.getY();
        if (height < 0) height = 1;
        r.setFrame(r.getX(), r.getY(), width, height);

      } else if (pickedFigureString.equals("Elipsa")) {

        Ellipse2D el = (Ellipse2D) pickedFigure.shape;
        if (whichSquareIsPressed == 0) {
          int width = e.getX() - (int) el.getX();
          if (width < 0) width = 1;
          el.setFrame(el.getX(), el.getY(), width, el.getHeight());
        } else if (whichSquareIsPressed == 1) {
          int height = e.getY() - (int) el.getY();
          if (height < 0) height = 1;
          el.setFrame(el.getX(), el.getY(), el.getWidth(), height);

        }
      } else if (pickedFigureString.equals("Wielokat")) {

        Polygon pol = (Polygon) pickedFigure.shape;
        int n = pol.npoints;
        int xTab[] = pol.xpoints;
        int yTab[] = pol.ypoints;
        xTab[whichSquareIsPressed] = e.getX();
        yTab[whichSquareIsPressed] = e.getY();
        pickedFigure.shape = new Polygon(xTab, yTab, n);

      }

      setNewDataPanel(false);
    }
    mainPanel.drawPanel.repaint();

  }

  public void mouseMoved(MouseEvent e) {                             ///AKTUALIZACJA POZYCJI KURSORA
    mainPanel.toolPanel.xLabel.setText("x: " + e.getX());
    mainPanel.toolPanel.yLabel.setText("y: " + e.getY());
    if (mainPanel.drawPanel.isBuild) {
      if (mainPanel.currentShape.equals("Wielokat")) {
        mainPanel.drawPanel.movePoint = e.getPoint();
        mainPanel.drawPanel.repaint();
      }
    }
  }

  DrawPanelAdapter(MainPanel mainPanel) {
    this.mainPanel = mainPanel;
  }
}
