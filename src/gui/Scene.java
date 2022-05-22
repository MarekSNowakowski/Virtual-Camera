package gui;

import main.Camera;
import main.Matrix;
import main.PlaneSorting;

import java.util.ArrayList;

public class Scene {
    private final Camera camera;
    private ArrayList<Figure> figures = new ArrayList<>();
    private int xd, zd;

    public Scene(Camera camera, ArrayList<Figure> figures) {
        this.camera = camera;
        listCopy(figures);
        xd = camera.WINDOW_WIDTH / 2;
        zd = camera.WINDOW_HEIGHT / 2;
    }

    private void listCopy (ArrayList<Figure> fs) {
        figures.clear();
        for (Figure f : fs) {
            Figure fn = new Figure(f.GetLines());
            fn.color = f.color;
            figures.add(fn);
        }
    }

    public ArrayList<Figure> project () {
        ArrayList<Figure> picture = new ArrayList<>();
        for (Figure figure : figures) {
            ArrayList<Line> figureLines = new ArrayList<Line>();
            for (Line line : figure.GetLines()) {
                Line projectedLine;
                var p1 = line.getStart();
                var p2 = line.getEnd();
                var p1visible = camera.isVisible(p1);
                var p2visible = camera.isVisible(p2);

                if (p1visible && p2visible) {
                    projectedLine = new Line(projectPoint(p1), projectPoint(p2));
                    figureLines.add(projectedLine);
                } else if (!p1visible && p2visible) {
                    projectedLine = projectPartially(p2, p1);
                    figureLines.add(projectedLine);
                } else if (p1visible) {
                    projectedLine = projectPartially(p1, p2);
                    figureLines.add(projectedLine);
                }
            }
            Figure f = new Figure(figureLines);
            f.color = figure.color;
            picture.add(f);
        }

        return picture;
    }

    public void changeFocal(double change) {
        camera.setDistance(camera.getDistance() + change);
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

        for (Figure f : figures) {
            for (Line l : f.GetLines()) {
                start = l.getStart();
                start.multiply(M);
                start.normalize();

                end = l.getEnd();
                end.multiply(M);
                end.normalize();
            }
        }
    }

    public double getCameraY() {
        return camera.getY();
    }
}
