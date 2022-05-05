package main;


import gui.Panel;
import gui.Window;

public class Main {
    private static Control control;
    private static Camera camera;
    private static Panel panel;
    private static Window window;

    public static void main(String[] args) {
        camera = new Camera();
        panel = new Panel(camera);

        control = new Control(camera, panel);
        window = new Window(panel);
        window.setVisible(true);
    }
}
