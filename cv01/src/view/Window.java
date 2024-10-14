package view;

import javax.swing.*;

public class Window extends JFrame {

    private final Panel panel;

    public Window(int width, int height) {
        setTitle("PGRF1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        panel = new Panel(width, height);
        add(panel);
        pack();
        setLocationRelativeTo(null);

        panel.setFocusable(true);
        panel.grabFocus();
    }

    public Panel getPanel() {
        return panel;
    }
}
