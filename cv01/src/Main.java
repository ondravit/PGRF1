import controller.Controller2D;
import view.Window;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Window window = new Window(800, 600);
        new Controller2D(window.getPanel());
    }
}