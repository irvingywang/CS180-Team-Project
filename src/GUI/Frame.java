package GUI;

import Network.Client;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Component;

public class Frame extends JFrame {
    private Panel contentPanel;

    public Frame(String title) {
        super(title);
        setSize(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(GUIConstants.PRIMARY_BLACK);
        setVisible(true);
        setResizable(false);

        contentPanel = new Panel();
        add(contentPanel, BorderLayout.CENTER);
        contentPanel.setDoubleBuffered(true);

        //TODO change frame color
    }

    public void switchPage(Page newPage) {
        contentPanel.removeAll();
        newPage.Content();
        revalidate();
        repaint();
    }

    public void addComponent(Component component) {
        getContentPane().add(component);
    }
}
