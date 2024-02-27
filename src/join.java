import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class Join {
    
    public static void createAndShowMainGUI() {
        JFrame frame = new JFrame("회원가입");
        frame.setSize(350, 340);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);
        
        frame.setLocationRelativeTo(null); // 창을 화면 중앙에 배치
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // 아이디 입력 필드
        JLabel idLabel = new JLabel("아이디");
        idLabel.setBounds(10, 20, 80, 25);
        panel.add(idLabel);
        JTextField idField = new JTextField(20);
        idField.setBounds(100, 20, 200, 25);
        panel.add(idField);

        // 비밀번호 입력 필드
        JLabel passwordLabel = new JLabel("비밀번호");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 50, 200, 25);
        panel.add(passwordField);

        // 비밀번호 확인 입력 필드
        JLabel confirmPasswordLabel = new JLabel("비밀번호 확인");
        confirmPasswordLabel.setBounds(10, 80, 120, 25);
        panel.add(confirmPasswordLabel);
        JPasswordField confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setBounds(100, 80, 200, 25);
        panel.add(confirmPasswordField);

        // 이름 입력 필드
        JLabel nameLabel = new JLabel("세대주 이름");
        nameLabel.setBounds(10, 110, 80, 25);
        panel.add(nameLabel);
        JTextField nameField = new JTextField(20);
        nameField.setBounds(100, 110, 200, 25);
        panel.add(nameField);

        // 호수 입력 필드
        JLabel floorLabel = new JLabel("호수");
        floorLabel.setBounds(10, 140, 80, 25);
        panel.add(floorLabel);
        JTextField floorField = new JTextField(20);
        floorField.setBounds(100, 140, 200, 25);
        panel.add(floorField);

       // 구성원 이름 입력 필드
       JLabel memberNameLabel = new JLabel("구성원 이름");
       memberNameLabel.setBounds(10, 170, 80, 25);
       panel.add(memberNameLabel);
       JTextField memberNameField = new JTextField(20);
       memberNameField.setBounds(100, 170, 200, 25);
       panel.add(memberNameField);

       // 휴대폰 번호 입력 필드
       JLabel phoneLabel = new JLabel("휴대폰 번호");
       phoneLabel.setBounds(10, 200, 80, 25);
       panel.add(phoneLabel);
       JTextField phoneField = new JTextField(20);
       phoneField.setBounds(100, 200, 200, 25);
       panel.add(phoneField);

       // 이메일 주소 입력 필드
       JLabel emailLabel = new JLabel("이메일 주소");
       emailLabel.setBounds(10, 230, 80, 25);
       panel.add(emailLabel);
       JTextField emailField = new JTextField(20);
       emailField.setBounds(100, 230, 200, 25);
       panel.add(emailField);

       // 회원가입 버튼
       JButton signUpButton = new JButton("회원가입");
       signUpButton.setBounds(10, 260, 120, 25);
       panel.add(signUpButton);

        // 회원가입 버튼에 대한 액션 리스너 추가
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //입력한 값 불러오기
                String id = idField.getText();
                String password = String.valueOf(passwordField.getPassword());
                String confirmPassword = String.valueOf(confirmPasswordField.getPassword());
                String name = nameField.getText();
                String member = memberNameField.getText();
                String floor = floorField.getText();
                String phone = phoneField.getText();
                String email = emailField.getText();

                if (id.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || name.isEmpty() || floor.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "모든 필드를 입력해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
                } else if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(panel, "비밀번호와 비밀번호 확인이 일치하지 않습니다.", "경고", JOptionPane.WARNING_MESSAGE);
                } else {
                    Connection conn = null;
                    PreparedStatement statement = null;
                    try {
                        Properties props = new Properties();
                        props.load(new FileInputStream("src/database.properties"));
                        String url = props.getProperty("db.url");
                        String user = props.getProperty("db.user");
                        String pass = props.getProperty("db.userpassword");

                        conn = DriverManager.getConnection(url, user, pass);
                        String sql = "INSERT INTO user_join (user_id, pw, name, apartment_number, member_name, phone,email) VALUES (?, ?, ?, ?, ?, ?, ?)";
                        statement = conn.prepareStatement(sql);
                        statement.setString(1, id);
                        statement.setString(2, password);
                        statement.setString(3, name);
                        statement.setString(4, floor);
                        statement.setString(5, member);
                        statement.setString(6, phone);
                        statement.setString(7, email);
                        int rowsInserted = statement.executeUpdate();
                        if (rowsInserted > 0) {
                            JOptionPane.showMessageDialog(panel, "회원가입이 완료되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
                        }
                        } catch (SQLException | IOException ex) {
                        JOptionPane.showMessageDialog(panel, "회원가입 중 오류가 발생했습니다: " + ex.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    } finally {
                        try {
                            if (statement != null) statement.close();
                            if (conn != null) conn.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ApartmentManagementProgram::createAndShowMainGUI);
    }
}
