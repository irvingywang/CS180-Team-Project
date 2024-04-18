package GUI;

import Network.Client;
import Pages.*;

import java.awt.Color;

public class GUIConstants {
    // Colors
    public static final Color PRIMARY_BLUE = Color.decode("#386AC7");
    public static final Color DARKER_BLUE = Color.decode("#2D559F");
    public static final Color PRIMARY_STROKE = Color.decode("#343434");

    // Background
    public static final Color PRIMARY_BLACK = Color.decode("#1E1E1E");
    public static final Color SECONDARY_BLACK = Color.decode("#262626");
    public static final Color TERTIARY_BLACK = Color.decode("#2F2F2F");

    // Text
    public static final Color PRIMARY_WHITE = Color.decode("#FDFDFD");
    public static final Color SECONDARY_WHITE = Color.decode("#D0D0D0");

    //Window Size
    public static final int WINDOW_WIDTH = 900;
    public static final int WINDOW_HEIGHT = 650;

    // Pages
    public static final Client CLIENT = new Client();
    public static final Page WELCOME_PAGE = new WelcomePage(CLIENT);
    public static final Page LOGIN_PAGE = new LoginPage(CLIENT);

}
