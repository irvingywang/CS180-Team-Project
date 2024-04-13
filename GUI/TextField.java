package GUI;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class TextField extends JTextField {

    private String placeholder;

    public TextField(String placeholder) {
        super(placeholder);
        this.placeholder = placeholder;
        this.setEditable(true);
        this.setFocusable(true);
        initUI();

    }

    private void initUI() {
        //TODO styling
        setText(placeholder);

        setVisible(true);

        //FIXME: This is not working
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(placeholder)) {
                    System.out.println("Focus gained");
                    setText("");
                }
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    System.out.println("Focus lost");
                    setText(placeholder);
                }
                repaint();
            }
        });
    }
}
