package Pages;

import GUI.*;
import Network.*;

import javax.swing.Box;
import java.awt.Dimension;

public class WelcomePage extends Page {

    public WelcomePage(Client client) {
        super(client);
        frame = new Frame("Login");
    }

    @Override
    public void Content() {
        Panel panel = new Panel();
        Label label = new Label("Connect and chat with your friends. Instantly", 35);

        Button createAccountButton = new Button("Create an Account", () -> {

        });

        Button loginButton = new Button("Login", () -> {
            frame.switchPage(new LoginPage(client));
        });

        //Sizing
        createAccountButton.setMaximumSize(new Dimension(400, 40));
        loginButton.setMaximumSize(new Dimension(400, 40));

        //Spacing
        panel.add(Box.createVerticalStrut(250));
        panel.add(label);
        panel.add(Box.createVerticalStrut(60));
        panel.add(loginButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(createAccountButton);

        frame.addComponent(panel);
    }
}
