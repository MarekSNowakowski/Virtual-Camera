package main;

import gui.Line;
import gui.Point;

public class Camera {
    private Line[] lines;

    public Camera () {
        Point p1 = new Point(100,100, 0);
        Point p2 = new Point(1200,700, 0);
        Point p3 = new Point(250,400, 0);
        lines = new Line[3];
        lines[0] = new Line(p1, p2);
        lines[1] = new Line(p2, p3);
        lines[2] = new Line(p3, p1);
    }

    public Line[] getLines () {
        Line[] linesClone = lines.clone();
        return linesClone;
    }

}