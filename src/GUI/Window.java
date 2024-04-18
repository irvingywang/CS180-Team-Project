package GUI;

import Network.Client;
import Pages.WelcomePage;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    private static Window instance = null;
    private Panel contentPanel;
    private CardLayout cardLayout;

    public Window(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create the content panel with CardLayout
        cardLayout = new CardLayout();
        contentPanel = new Panel(cardLayout);
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Set the background color
        contentPanel.setBackground(GUIConstants.PRIMARY_BLACK);

        setVisible(true);
    }

    public static Window getInstance() {
        if (instance == null) {
            instance = new Window("App");
        }
        return instance;
    }

    public void switchPage(Page newPage) {
        contentPanel.removeAll();
        contentPanel.add(newPage.getContent(), "content");
        cardLayout.show(contentPanel, "content");
        revalidate();
        repaint();
    }

    public void addComponent(Component component) {
        getContentPane().add(component);
    }
}
