package gui;

import main.Camera;

import javax.swing.JPanel;
import java.awt.*;


public class Panel extends JPanel {
    private final int WINDOW_WIDTH = 1600;
    private final int WINDOW_HEIGHT = 900;
    private Camera camera;
    private Line[] lines = new Line[3];

    public Panel (Camera c) {
        Dimension d = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setPreferredSize(d);
        this.setBackground(Color.DARK_GRAY);

        camera = c;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        lines = camera.getLines();
        g.setColor(Color.WHITE);

        for (Line line : lines) {
            g.drawLine(line.getStart().getX(), line.getStart().getY(),
                    line.getEnd().getX(), line.getEnd().getY());
        }
    }

}