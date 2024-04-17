import GUI.TextField;

import javax.swing.JFrame;
import javax.swing.JPanel;
public class Test {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Test TextField");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        JPanel panel = new JPanel();
        TextField textField = new TextField("Username");
        TextField textField2 = new TextField("Password");

        panel.add(textField);
        panel.add(textField2);
        frame.add(panel);
        frame.setVisible(true);
    }
}
