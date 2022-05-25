package gui;

import main.Control;

import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;


public class Panel extends JPanel {
    // Drawing panel dimensions
    private final int WINDOW_WIDTH = 1600;
    private final int WINDOW_HEIGHT = 900;
    private Control control;    // Control needed to get projected points
    private ArrayList<Figure> figures;  // Lines to draw
    private ArrayList<Color> colors = new ArrayList<>();

    public Panel (Control c) {
        Dimension d = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setPreferredSize(d);
        this.setBackground(Color.DARK_GRAY);
        control = c;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        figures = control.getProjected();

        int c = 0;
        for (Figure figure : figures)
        {
            g.setColor(figure.color);

            int xPoints[] = new int[4];
            int yPoints[] = new int[4];

            // Paint lines
            int i = 0;
            for (Point point : figure.getPoints()) {
                xPoints[i] = (int)point.getX();
                yPoints[i] = (int)point.getY();
                i++;
                if(i == 4){
                    g.fillPolygon(xPoints, yPoints, 4);
                    i = 0;
                }
            }
        }
    }

}