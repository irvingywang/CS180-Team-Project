package GUI;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Component;

public class Frame extends JFrame {
    Panel currentPanel;

    public Frame(String title) {
        super(title);
        setSize(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(GUIConstants.PRIMARY_BLACK);
        setVisible(true);
        setResizable(false);

        //TODO change frame color
    }

    public void changePanel(Panel newPanel) {
        if (currentPanel != null) {
            getContentPane().remove(currentPanel);
            currentPanel = null;
        }
        currentPanel = newPanel;
        getContentPane().add(currentPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void addComponent(Component component) {
        getContentPane().add(component);
    }
}
