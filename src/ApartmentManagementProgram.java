import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ApartmentManagementProgram {
    public static void createAndShowMainGUI() {
        JFrame frame = new JFrame("주민 관리 프로그램");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // 주민등록 버튼
        JButton signUpButton = new JButton("주민등록");
        signUpButton.setBounds(10, 20, 120, 25);
        panel.add(signUpButton);

        // 주민등록 버튼에 대한 액션 리스너 추가
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 주민등록 이벤트
            }
        });

        // 주민정보 확인 버튼
        JButton residentInfoButton = new JButton("주민정보 확인");
        residentInfoButton.setBounds(140, 20, 120, 25);
        panel.add(residentInfoButton);

        // 관리비 입력 버튼
        JButton feeInputButton = new JButton("관리비 입력");
        feeInputButton.setBounds(270, 20, 120, 25);
        panel.add(feeInputButton);

        // 관리비 정보 버튼
        JButton feeInfoButton = new JButton("관리비 정보");
        feeInfoButton.setBounds(400, 20, 120, 25);
        panel.add(feeInfoButton);

        // 알림 보내기 버튼
        JButton sendNotificationButton = new JButton("알림 보내기");
        sendNotificationButton.setBounds(530, 20, 120, 25);
        panel.add(sendNotificationButton);

        // 커뮤니티 포럼 버튼
        JButton communityForumButton = new JButton("커뮤니티 포럼");
        communityForumButton.setBounds(660, 20, 120, 25);
        panel.add(communityForumButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ApartmentManagementProgram::createAndShowMainGUI);
    }
}
