import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class join {
    
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
                // 아이디, 비밀번호, 이름, 호수, 휴대폰 번호, 이메일 주소가 모두 입력되었는지 확인
                String id = idField.getText();
                String password = String.valueOf(passwordField.getPassword());
                String confirmPassword = String.valueOf(confirmPasswordField.getPassword());
                String name = nameField.getText();
                String floor = floorField.getText();
                String phone = phoneField.getText();
                String email = emailField.getText();

                if (id.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || name.isEmpty() || floor.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "모든 필드를 입력해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
                } else if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(panel, "비밀번호와 비밀번호 확인이 일치하지 않습니다.", "경고", JOptionPane.WARNING_MESSAGE);
                } else {
                    // 회원가입 로직 처리
                    // 여기에서 입력된 데이터를 처리하는 로직을 추가할 수 있습니다.
                    // 예: 데이터베이스에 회원 정보를 저장하는 등의 작업을 수행합니다.
                    // 이 부분을 실제 회원가입 로직으로 대체해주세요.
                    JOptionPane.showMessageDialog(panel, "회원가입이 완료되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ApartmentManagementProgram::createAndShowMainGUI);
    }
}
