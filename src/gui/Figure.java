package gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Figure {
    private ArrayList<Line> lines = new ArrayList<>();
    public ArrayList<Point> points = new ArrayList<>();
    public Color color;

    public Figure(ArrayList<Line> figureLines) {
        for (Line line : figureLines){
            lines.add(Line.copy(line));
        }
        getDistinctPoints();
    }

    public ArrayList<Line> GetLines() {
        return lines;
    }

    public ArrayList<Point> getDistinctPoints() {
        if(points.size() == 0) {
            ArrayList<Point> points = new ArrayList<>();
            for (Line l : lines) {
                boolean addStart = true;
                boolean addEnd = true;
                for (Point p : points) {
                    if (p.getX() == l.getStart().getX() && p.getY() == l.getStart().getY() && p.getZ() == l.getStart().getZ())
                        addStart = false;
                    if (p.getX() == l.getEnd().getX() && p.getY() == l.getEnd().getY() && p.getZ() == l.getEnd().getZ())
                        addEnd = false;
                }
                if (addStart)
                    points.add(l.getStart());
                if (addEnd)
                    points.add(l.getEnd());
            }
            this.points = points;
            return points;
        }
        else{
            return points;
        }
    }

    public ArrayList<Point> getPoints() {
        ArrayList<Point> points = new ArrayList<>();
        for (Line l : lines) {
                points.add(l.getStart());
                points.add(l.getEnd());
        }
        return points;
    }

    public double [] getPlaneEqation() {
        Point P = points.get(1);
        Point Q = points.get(2);
        Point R = points.get(3);

        double [] PQ = new double []{Q.getX() - P.getX(), Q.getY() - P.getY(), Q.getZ() - P.getZ()};
        double [] PR = new double []{R.getX() - P.getX(), R.getY() - P.getY(), R.getZ() - P.getZ()};

        double a = PQ[1]*PR[2] - PQ[2]*PR[1];
        double b = PQ[0]*PR[2] - PQ[2]*PR[0];
        double c = PQ[0]*PR[1] - PQ[1]*PR[0];
        double d = a*P.getX() + b*P.getY() + c*P.getZ();

        return new double []{a,b,c,-d};
    }
}
