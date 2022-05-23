package gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Figure {
    private ArrayList<Line> lines = new ArrayList<>();
    public Color color = new Color((int)(Math.random() * 0x1000000));

    public Figure(ArrayList<Line> figureLines) {
        for (Line line : figureLines){
            lines.add(Line.copy(line));
        }
    }

    public ArrayList<Line> GetLines() {
        return lines;
    }

    public ArrayList<Point> getPoints() {
        ArrayList<Point> points = new ArrayList<>();
        for (Line l : lines) {
            if(!points.contains(l.getStart()))
                points.add(l.getStart());
            if(!points.contains(l.getEnd()))
                points.add(l.getEnd());
        }
        return points;
    }

    public double [] getPlaneEqation() {
        var points = getPoints();

        Point P = points.get(0);
        Point Q = null;
        Point R = null;

        for (Point p : points) {
            if(Q == null && (p.getX() != P.getX() || p.getY() != P.getY() || p.getZ() != P.getZ())){
                Q = p;
            }
            else if(Q!=null && (p.getX() != P.getX() || p.getY() != P.getY() || p.getZ() != P.getZ()) &&
                    (p.getX() != Q.getX() || p.getY() != Q.getY() || p.getZ() != Q.getZ())){
                R = p;
                break;
            }
        }
        double [] PQ = new double []{Q.getX() - P.getX(), Q.getY() - P.getY(), Q.getZ() - P.getZ()};
        double [] PR = new double []{R.getX() - P.getX(), R.getY() - P.getY(), R.getZ() - P.getZ()};

        double a = PQ[1]*PR[2] - PQ[2]*PR[1];
        double b = PQ[0]*PR[2] - PQ[2]*PR[0];
        double c = PQ[0]*PR[1] - PQ[1]*PR[0];
        double d = a*P.getX() + b*P.getY() + c*P.getZ();

        return new double []{a,b,c,-d};
    }
}
