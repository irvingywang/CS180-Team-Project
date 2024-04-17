package GUI;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class TextField extends JTextField {

    private String placeholder;

    public TextField(String placeholder) {
        super(placeholder);
        this.placeholder = placeholder;

        initStyle();
    }

    private void initStyle() {
        //TODO styling
        setText(placeholder);
        setEditable(true);
        setVisible(true);

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(placeholder)) {
                    setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(placeholder);
                }
            }
        });
    }
}
