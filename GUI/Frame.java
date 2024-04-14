package GUI;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    public Frame(String title) {
        super(title);
        setSize(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(GUIConstants.PRIMARY_BLACK);
        setVisible(true);
    }

    public void addComponent(Component component) {
        getContentPane().add(component);
    }

    public void clearFrame() {
        getContentPane().removeAll();
        revalidate();
    }
}
