package Pages;

import GUI.*;
import Network.*;

import javax.swing.*;
import java.awt.Dimension;

public class WelcomePage extends Page {
    Label label;
    Button createAccountButton;
    Button loginButton;

    public WelcomePage(Client client) {
        super(client);
        initContent();
    }

    @Override
    public void initContent() {
        label = new Label("Connect and chat with your friends. Instantly", 35);

        createAccountButton = new Button("Create an Account", () -> {
            //TODO switch to create account page
        });

        loginButton = new Button("Login", () -> window.switchPage(new LoginPage(client)));

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
    }
}