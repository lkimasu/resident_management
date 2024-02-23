import javax.swing.JFrame;
import javax.swing.JPanel;

public class login {

        public static void createAndShowLoginGUI() {
        JFrame loginFrame = new JFrame("로그인");
        loginFrame.setSize(300, 150);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        loginFrame.add(panel);
        LoginPanel.placeComponents(panel);

        loginFrame.setVisible(true);
    }
}