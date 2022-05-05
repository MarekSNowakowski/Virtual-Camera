package gui;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {//inheriting JFrame
    JFrame f;

    public Window(Panel panel) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1600, 900);
        setLayout(null);
        setVisible(true);

        Container pane = this.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));

        panel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        pane.add(panel);
    }
}