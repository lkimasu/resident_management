import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

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

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());

                // 데이터베이스 연결 정보 읽기
                Properties props = new Properties();
                try (FileInputStream in = new FileInputStream("src/database.properties")) {
                    props.load(in);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    return;
                }

                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String pass = props.getProperty("db.userpassword");

                try (Connection conn = DriverManager.getConnection(url, user, pass)) {
                    String query = "SELECT * FROM user_join WHERE user_id=? AND pw=?";
                    try (PreparedStatement statement = conn.prepareStatement(query)) {
                        statement.setString(1, username);
                        statement.setString(2, password);
                        try (ResultSet resultSet = statement.executeQuery()) {
                            if (resultSet.next()) {
                                // 일치하는 사용자가 있으면 로그인 성공
                                JOptionPane.showMessageDialog(null, "로그인 성공!");
                                JFrame loginFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                                loginFrame.dispose(); // 로그인 창 닫기
                                ApartmentManagementProgram.createAndShowMainGUI(); // 주민 관리 프로그램 창 열기
                            } else {
                                // 일치하는 사용자가 없으면 로그인 실패
                                JOptionPane.showMessageDialog(null, "로그인 실패. 다시 시도하세요.");
                            }
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "로그인 중 오류가 발생했습니다.");
                }
            }
        });
        panel.add(loginButton);

        JButton joinButton = new JButton("회원가입");
        joinButton.setBounds(100, 80, 90, 25);

        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Join.createAndShowMainGUI(); // 회원가입 버튼
            }
        });
        panel.add(joinButton);

        JFrame loginFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);
        loginFrame.setLocationRelativeTo(null);
    }
}
