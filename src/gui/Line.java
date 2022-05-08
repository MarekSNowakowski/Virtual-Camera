package gui;

public class Line {
    private Point start;
    private Point end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public static Line copy (Line l) {
        Point start = new Point(l.getStart().getX(),l.getStart().getY(), l.getStart().getZ());
        Point end = new Point(l.getEnd().getX(),l.getEnd().getY(), l.getEnd().getZ());
        return new Line(start, end);
    }
}
