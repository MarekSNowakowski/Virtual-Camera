package main;
import gui.Line;
import gui.Panel;
import gui.Scene;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Control implements KeyListener {
    private final Panel panel;
    private final Double[] v;
    private final Matrix T = new Matrix();         // macierz translacji
    private final Matrix R = new Matrix();         // macierz rotacji
    private final Scene scene;
    private ArrayList<Line> projected;
    private boolean update = true;

    public Control(Scene scene) {
        this.scene = scene;
        this.panel = new Panel(this);
        projected = scene.project();

        v = new Double[3];
        for (int i=0; i<3; i++)
            v[i]=0.0;

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new UpdateTimer(), 250, 50);
        CreateRotationMatrixes();
    }

    public Panel getPanel() {
        return panel;
    }

    public ArrayList<Line> getProjected () {
        return projected;
    }

    public void translate(int i, boolean adding) {
        double translationStep = 100.0;
        if(adding) {
            v[i] += translationStep;
        }
        else{
            v[i] -= translationStep;
        }
        update = true;
    }

    // Zooming in and out
    public void changeFocal(boolean zoomIn) {
        double fStep = 10.0;
        if(zoomIn) {
            scene.changeFocal(fStep);
        }
        else {
            scene.changeFocal(-fStep);
        }
        update = true;
    }

    private Matrix rotateXdown = new Matrix();
    private Matrix rotateXup = new Matrix();
    private Matrix rotateYright = new Matrix();
    private Matrix rotateYleft = new Matrix();
    private Matrix rotateZright = new Matrix();
    private Matrix rotateZleft = new Matrix();

    private void CreateRotationMatrixes() {
        var rotationStep = 0.1;
        rotateXdown  = this.makeRotationXMatrix(rotationStep);
        rotateXup    = this.makeRotationXMatrix(-rotationStep);
        rotateYleft  = this.makeRotationYMatrix(rotationStep);
        rotateYright = this.makeRotationYMatrix(-rotationStep);
        rotateZleft  = this.makeRotationZMatrix(-rotationStep);
        rotateZright = this.makeRotationZMatrix(rotationStep);
    }

    public Matrix makeRotationXMatrix (double step) {
        Matrix m = new Matrix();
        double cos, sin, c;
        c = scene.getCameraY();
        cos = java.lang.Math.cos(step);
        sin = java.lang.Math.sin(step);

        m.setMatrixValue(1, 1, cos);
        m.setMatrixValue(2, 1, sin);
        m.setMatrixValue(1, 2, -sin);
        m.setMatrixValue(2, 2, cos);

        m.setMatrixValue(1, 3, c * (cos-1));
        m.setMatrixValue(2, 3, sin*c);
        return m;
    }

    private Matrix makeRotationYMatrix (double step) {
        Matrix m = new Matrix();
        double cos, sin;
        cos = java.lang.Math.cos(step);
        sin = java.lang.Math.sin(step);

        m.setMatrixValue(0, 0, cos);
        m.setMatrixValue(0, 2, sin);
        m.setMatrixValue(2, 0, -sin);
        m.setMatrixValue(2, 2, cos);
        return m;
    }

    private Matrix makeRotationZMatrix (double step) {
        Matrix m = new Matrix();
        double cos, sin, c;
        c = scene.getCameraY();
        cos = java.lang.Math.cos(step);
        sin = java.lang.Math.sin(step);

        m.setMatrixValue(0, 0, cos);
        m.setMatrixValue(0, 1, -sin);
        m.setMatrixValue(1, 0, sin);
        m.setMatrixValue(1, 1, cos);

        m.setMatrixValue(0, 3, (-1)*sin*c);
        m.setMatrixValue(1, 3, (cos-1)*c);
        return m;
    }

    private void rotate(Matrix rotationM)
    {
        R.multiple(rotationM);
        update = true;
    }

    // Key events
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'w')
            translate(1,false);
        if (e.getKeyChar() == 'a')
            translate(0, true);
        if (e.getKeyChar() == 's')
            translate(1, true);
        if (e.getKeyChar() == 'd')
            translate(0, false);
        if (e.getKeyCode() == KeyEvent.VK_SHIFT)
            translate(2, false);
        if (e.getKeyCode() == KeyEvent.VK_CONTROL)
            translate(2, true);
        if (e.getKeyChar() == 'f')
            changeFocal(true);
        if (e.getKeyChar() == 'g')
            changeFocal(false);
        if (e.getKeyChar() == 'q')
            rotate(rotateYleft);
        if (e.getKeyChar() == 'e')
            rotate(rotateYright);
        if (e.getKeyCode()==KeyEvent.VK_UP)
            rotate(rotateXup);
        if (e.getKeyCode()==KeyEvent.VK_DOWN)
            rotate(rotateXdown);
        if (e.getKeyCode()==KeyEvent.VK_LEFT)
            rotate(rotateZleft);
        if (e.getKeyCode()==KeyEvent.VK_RIGHT)
            rotate(rotateZright);
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) { }

    // Timer for updating
    private class UpdateTimer extends TimerTask {
        @Override
        public void run() {
            if (update) {
                for (int w = 0; w < 3; w++)
                    T.setMatrixValue(w, 3, v[w]);

                T.multiple(R);
                scene.multiplyPoints(T);
                projected = scene.project();

                R.createI();
                T.createI();

                for (int i = 0; i < 3; i++)
                    v[i] = 0.0;

                panel.repaint();
                update = false;
            }
        }
    }
}
