package org.example.loginpage.gameLoader;

import java.awt.event.MouseAdapter;

public class MouseController extends MouseAdapter {
    public int x, y;
    public boolean pressed;

    public void mousePressed(javafx.scene.input.MouseEvent e) {
        pressed = true;
    }

    public void mouseReleased(javafx.scene.input.MouseEvent e) {
        pressed = false;
    }

    public void mouseDragged(javafx.scene.input.MouseEvent e) {
        x = (int) e.getX();
        y = (int) e.getY();
    }

    public void mouseMoved(javafx.scene.input.MouseEvent e) {
        x = (int) e.getX();
        y = (int) e.getY();
    }
}

