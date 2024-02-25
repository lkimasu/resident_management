import javax.swing.*;

public class LoginPanel {
    public static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("사용자명");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("비밀번호");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        JButton loginButton = new JButton("로그인");
        loginButton.setBounds(10, 80, 80, 25);
        
        loginButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passwordText.getPassword());

            if (username.equals("admin") && password.equals("admin")) {
                JOptionPane.showMessageDialog(null, "로그인 성공!");
                JFrame loginFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                loginFrame.dispose(); // 로그인 창 닫기
                ApartmentManagementProgram.createAndShowMainGUI(); // 주민 관리 프로그램 창 열기
            } else {
                JOptionPane.showMessageDialog(null, "로그인 실패. 다시 시도하세요.");
            }
        });
        panel.add(loginButton);

        JButton joinButton = new JButton("회원가입");
        joinButton.setBounds(100, 80, 90, 25);

        joinButton.addActionListener(e -> {
        
                join.createAndShowMainGUI(); // 회원가입 버튼
        });
        panel.add(joinButton);

        JFrame loginFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);
        loginFrame.setLocationRelativeTo(null);
    }
}
