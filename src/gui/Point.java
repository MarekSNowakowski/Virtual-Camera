package gui;

import main.Matrix;

public class Point {
    private Double[] v;

    public Point(double x, double y, double z) {
        v = new Double[4];
        this.v[0] = x;
        this.v[1] = y;
        this.v[2] = z;
        this.v[3] = 1.0;
    }

    public double getX() {
        return v[0];
    }

    public double getY() {
        return v[1];
    }

    public double getZ() {
        return v[2];
    }

    protected void multiply (Matrix m) {
        Double [] tmp = new Double[4];
        Double[][] mat = m.getMatrix();

        for (int w =0; w<4; w++) {
            tmp[w] = 0.0;
            for (int k=0; k<4; k++) {
                tmp[w] += mat[w][k]*v[k];
            }
        }
        v = tmp.clone();
    }

    protected void normalize () {
        v[0] = v[0]*(1/v[3]);
        v[1] = v[1]*(1/v[3]);
        v[2] = v[2]*(1/v[3]);
        v[3] = 1.0;
    }
}
