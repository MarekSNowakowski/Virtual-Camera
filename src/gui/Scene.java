package gui;

import main.Camera;
import main.Matrix;

import java.util.ArrayList;

public class Scene {
    private final Camera camera;
    private ArrayList<Line> lines = new ArrayList<Line>();
    private int xd, zd;

    public Scene(Camera camera, ArrayList<Line> lines) {
        this.camera = camera;
        listCopy(lines);
        xd = camera.WINDOW_WIDTH / 2;
        zd = camera.WINDOW_HEIGHT / 2;
    }

    private void listCopy (ArrayList<Line> list) {
        for (Line l : list) {
            lines.add(Line.copy(l));
        }
    }

    public ArrayList<Line> project () {
        ArrayList<Line> picture = new ArrayList<>();

        for (Line line : lines) {
            Line projectedLine;
            var p1 = line.getStart();
            var p2 = line.getEnd();
            var p1visible = camera.isVisible(p1);
            var p2visible = camera.isVisible(p2);

            if (p1visible && p2visible) {
                projectedLine = new Line(projectPoint(p1), projectPoint(p2));
                picture.add(projectedLine);
            }
            else if (!p1visible && p2visible) {
                projectedLine = projectPartially(p2, p1);
                picture.add(projectedLine);
            }
            else if (p1visible) {
                projectedLine = projectPartially(p1, p2);
                picture.add(projectedLine);
            }
        }

        return picture;
    }

    private Point projectPoint (Point p) {
        double l = camera.getDistance() / (p.getY()-camera.getY());

        int x = (int) (l*p.getX()+xd);
        int z = (int) (zd - l*p.getZ());

        return new Point (x,z,0);
    }

    private Line projectPartially(Point a, Point b) { // a is visible and b invisible
        Point newStart, newEnd;
        if (a.getY() == (camera.getY()+camera.getDistance())) {
            newStart = new Point(
                    (int)(a.getX() + xd),
                    (int)(zd - a.getZ()),
                    0
            );
            newEnd = new Point(newStart.getX(), newStart.getY(), 0);
            return new Line (newStart, newEnd);
        }
        else {
            double Y = a.getY() - b.getY();
            double y = a.getY() - ( camera.getY() + camera.getDistance() );
            double x, z, r;
            r = y/Y;
            x = a.getX() + ( (b.getX() - a.getX() ) * r );
            z = a.getZ() + ( (b.getZ() - a.getZ() ) * r );
            return new Line ( projectPoint(a), new Point((int)(xd+x), (int)(zd-z), 0));
        }
    }

    public void multiplyPoints(Matrix M) {
        Point start, end;
        for (Line l : lines) {
            start = l.getStart();
            start.multiply(M);
            start.normalize();

            end = l.getEnd();
            end.multiply(M);
            end.normalize();
        }
    }
}
