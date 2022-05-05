package main;
import gui.Window;
import gui.Panel;

public class Control {
    private Panel panel;
    private Camera camera;

    public Control (Camera camera, Panel panel) {
        this.camera = camera;
        this.panel = panel;
    }
}
