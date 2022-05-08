package main;

import gui.Panel;
import gui.Scene;
import gui.Window;

public class Main {
    private static Control control;
    private static Camera camera;
    private static Panel panel;
    private static Window window;
    private static Scene scene;

    public static void main(String[] args) {
        // Load lines from the file
        var lineData = LineLoader.loadFromFile("data.txt");

        // Initialize camera and scene
        camera = new Camera(-1200,600.0);
        scene = new Scene(camera, lineData);

        // Initialize control script and gui
        control = new Control(scene);
        panel = control.getPanel();
        window = new Window(panel);
        window.setVisible(true);
        window.addKeyListener(control);
    }
}
