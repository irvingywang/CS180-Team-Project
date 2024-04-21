package GUI;

import Network.Client;
import Pages.*;

import java.awt.*;

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
    public static final Color TERTIARY_WHITE = Color.decode("#A4A4A4");

    //Window Size
    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 700;

    // Fonts
    public static final String FONT_NAME = "Arial";
    public static final int LEFT_PADDING = 15;
    public static final Font TEXT_FONT = new Font("Arial", Font.PLAIN, 15);


    // Styling
    public static final int EDGE_RADIUS = 10;
    public static final int STROKE_THICKNESS = 2;

    // Sizing
    public static final Dimension SIZE_400_40 = new Dimension(400, 40);
}
