package Network;

/**
 * Project05 -- Client.GUIInterface
 *
 * Creates an interface for the GUI of the app.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 *@author Karan Vankwani, L08

 * @version April 14, 2024
 *
 */
public interface GUIInterface {
    void welcomePage();
    void showError(String error);
    void createUser();
    void userSearch(String user);
    void homePage();
}