import javax.swing.JFrame;
import javax.swing.JPanel;

public class Login {

        public static void createAndShowLoginGUI() {
        JFrame frame = new JFrame("로그인");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        LoginPanel.placeComponents(panel);
        
        frame.setLocationRelativeTo(null); // 창을 화면 중앙에 배치    
        frame.setVisible(true);
    }
}