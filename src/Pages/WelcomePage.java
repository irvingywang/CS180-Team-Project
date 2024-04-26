package Pages;

import GUI.*;
import Network.Client;

/**
 * Project05 -- WelcomePage
 * <p>
 * Creates page that is shown after program is ran
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 * @author Karan Vankwani, L08
 * @version April 14, 2024
 */
public class WelcomePage extends Page {
    private Label label;
    private Label label2;
    private Button createAccountButton;
    private Button loginButton;

    public WelcomePage(Client client) {
        super(client);
    }

    @Override
    public void initContent() {
        label = new Label("Connect and chat with your", 40);
        label2 = new Label("friends. Instantly.", 40);

        createAccountButton = new Button("Create an account",
                () -> window.switchPage(new CreateUserPage(client)), GUIConstants.SIZE_400_40, true);

        loginButton = new Button("Login",
                () -> window.switchPage(new LoginPage(client)), GUIConstants.SIZE_400_40);

        addComponents();
    }

    @Override
    public void addComponents() {
        panel.add(new Spacer(250));
        panel.add(label);
        panel.add(label2);
        panel.add(new Spacer(60));
        panel.add(loginButton);
        panel.add(new Spacer(10));
        panel.add(createAccountButton);

        panel.revalidate();
    }
}