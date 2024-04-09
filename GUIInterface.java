import java.time.LocalDateTime;
import java.util.List;

/**
 * Project05 -- GUIInterface
 *
 * Creates an interface for the GUI of the app.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 *
 * @version April 1, 2024
 *
 */
public interface GUIInterface {
    void login();
    void userSearch(String user);
    void homePage();
    void userInteraction(String choice, String info);
}
