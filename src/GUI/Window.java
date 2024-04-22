package GUI;

import javax.swing.JFrame;
import java.awt.*;

public class Window extends JFrame {
    private static volatile Window instance = null;
    private final Panel contentPanel;
    private final CardLayout cardLayout;

    public Window(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        contentPanel = new Panel(cardLayout);
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Set the background color
        contentPanel.setBackground(GUIConstants.PRIMARY_BLACK);

        setVisible(true);
    }

    public synchronized static Window getInstance() {
        if (instance == null) {
            instance = new Window("Yap");
        }
        return instance;
    }

    public void switchPage(Page newPage) {
        contentPanel.removeAll();
        contentPanel.add(newPage.getPanel(), "content");
        cardLayout.show(contentPanel, "content");
        revalidate();
        repaint();
    }
}
