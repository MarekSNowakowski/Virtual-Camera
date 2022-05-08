package main;

import gui.Point;

public class Camera {
    private double y;
    private double distance;
    public final int WINDOW_WIDTH = 1600;
    public final int WINDOW_HEIGHT = 900;

    public Camera (double y, double distance) {
        this.y = y;
        this.distance = distance;
    }

    // Checks if point may be visible by the camera
    public boolean isVisible(Point p) {
        if (p.getY() >= y+distance) return true;
        else return false;
    }

    public double getDistance(){
        return distance;
    }

    public void setDistance(double d) {
        distance = d;
    }

    public double getY(){
        return y;
    }
}