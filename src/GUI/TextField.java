package GUI;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

        //FIXME: Clear text on click is not working
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (getText().equals(placeholder)) {
                    System.out.println("Mouse clicked");
                    setText("");
                    repaint();
                }
            }
        });
    }
}
