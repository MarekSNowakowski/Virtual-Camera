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
    private ArrayList<Line> lines;  // Lines to draw

    public Panel (Control c) {
        Dimension d = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setPreferredSize(d);
        this.setBackground(Color.DARK_GRAY);
        control = c;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        lines = control.getProjected();
        g.setColor(Color.WHITE);

        // Paint lines
        for (Line line : lines) {
            g.drawLine((int)line.getStart().getX(), (int)line.getStart().getY(),
                    (int)line.getEnd().getX(), (int)line.getEnd().getY());
        }
    }

}