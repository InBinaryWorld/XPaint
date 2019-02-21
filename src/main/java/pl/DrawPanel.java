package pl;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public  class DrawPanel extends JPanel {
    private MainPanel mainPanel;
    Point movePoint;
    public ArrayList<Point> buildPoints = new ArrayList<>();
    public ArrayList<Rectangle2D> squares = new ArrayList<>();
    public DrawPanelAdapter adapter;
    public boolean isBuild = false;
    public boolean isPick = false;
    private double squareSize = 7;

    ///NADANIE PANELOWI SLUCHACHY
    DrawPanel(MainPanel mainPanel) {
        super(true);
        this.mainPanel = mainPanel;
        adapter = new DrawPanelAdapter(mainPanel);
        addMouseListener(adapter);
        addMouseMotionListener(adapter);

    }

    ///RYZOWANIE KWADRATOW WYBRANEJ FIGURY
    private void drawPickedFigure(Graphics2D g) {
        if (isPick) {
            g.setColor(Color.BLACK);
            squares.clear();
            switch (adapter.pickedFigureString) {
                case "Prostokat": {
                    Rectangle2D r = (Rectangle2D) adapter.pickedFigure.shape;
                    Rectangle2D sq;
                    sq = new Rectangle2D.Double(r.getX() + r.getWidth() - squareSize / 2,
                            r.getY() + r.getHeight() - squareSize / 2, squareSize, squareSize);
                    squares.add(sq);
                    g.fill(sq);

                    break;
                }
                case "Elipsa": {
                    Ellipse2D e = (Ellipse2D) adapter.pickedFigure.shape;
                    Rectangle2D sq;
                    sq = new Rectangle2D.Double(e.getX() + e.getWidth() - squareSize / 2,
                            e.getY() + e.getHeight() / 2 - squareSize / 2, squareSize, squareSize);
                    squares.add(sq);
                    g.fill(sq);
                    sq = new Rectangle2D.Double(e.getX() + e.getWidth() / 2 - squareSize / 2,
                            e.getY() + e.getHeight() - squareSize / 2, squareSize, squareSize);
                    squares.add(sq);
                    g.fill(sq);

                    break;
                }
                case "Wielokat": {
                    Polygon pol = (Polygon) adapter.pickedFigure.shape;
                    int x[] = pol.xpoints;
                    int y[] = pol.ypoints;
                    int n = pol.npoints;
                    Rectangle2D sq;
                    for (int i = 0; i < n; i++) {
                        sq = new Rectangle2D.Double(x[i] - squareSize / 2, y[i] - squareSize / 2, squareSize, squareSize);
                        squares.add(sq);
                        g.fill(sq);
                    }
                    break;
                }
            }
        }
    }

    ///WIZUALIZOWANIE BUDOWY FIGURY
    private void drawBuild(Graphics2D g) {
        if (isBuild) {
            switch (mainPanel.currentShape) {
                case "Prostokat":
                    Rectangle2D r = new Rectangle2D.Double();
                    r.setFrameFromDiagonal(buildPoints.get(0), buildPoints.get(1));
                    g.setColor(Color.BLACK);
                    g.draw(r);
                    break;
                case "Elipsa":
                    Ellipse2D el = new Ellipse2D.Double();
                    el.setFrameFromDiagonal(buildPoints.get(0), buildPoints.get(1));
                    g.setColor(Color.BLACK);
                    g.draw(el);
                    break;
                case "Wielokat":
                    g.setColor((Color.BLACK));
                    squares.add(new Rectangle2D.Double(buildPoints.get(0).x - squareSize / 2, buildPoints.get(0).y - squareSize / 2, squareSize, squareSize));
                    g.fill(squares.get(0));
                    for (int i = 0; i < buildPoints.size() - 1; i++) {
                        g.drawLine(buildPoints.get(i).x, buildPoints.get(i).y, buildPoints.get(i + 1).x, buildPoints.get(i + 1).y);
                    }
                    g.drawLine(buildPoints.get(buildPoints.size() - 1).x, buildPoints.get(buildPoints.size() - 1).y, movePoint.x, movePoint.y);
                    break;
            }
        }

    }

    ///RYZSOWANIE AKTUALNEJ LIZTY FIGUR
    private void draw(Graphics2D g) {
        for (Figures f : mainPanel.figuresList) {
            g.setColor(f.color);
            if (f.isFill)
                g.fill(f.shape);
            else
                g.draw(f.shape);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        draw(g2);
        drawBuild(g2);
        drawPickedFigure(g2);

    }
}
