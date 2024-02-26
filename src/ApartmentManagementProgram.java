import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ApartmentManagementProgram {
    public static void createAndShowMainGUI() {
        JFrame frame = new JFrame("메인화면");
        frame.setSize(550, 250);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        // 프레임을 화면 중앙에 배치
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // 회원가입 버튼
        JButton signUpButton = new JButton("회원가입");
        signUpButton.setBounds(10, 20, 120, 25);
        

        // 회원가입 버튼에 대한 액션 리스너 추가
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Join.createAndShowMainGUI();
            }
        });

        panel.add(signUpButton);

        // 주민정보 버튼
        JButton residentInfoButton = new JButton("주민정보");
        residentInfoButton.setBounds(140, 20, 120, 25);

        residentInfoButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                ResidentInfo.createAndShowGUI();
            }

        });
        panel.add(residentInfoButton);

        // 관리비 입력 버튼
        JButton feeInputButton = new JButton("관리비 입력");
        feeInputButton.setBounds(270, 20, 120, 25);

        feeInputButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                FeeInput.createAndShowGUI();
            }
        });
        panel.add(feeInputButton);



        // 관리비 정보 버튼
        JButton feeInfoButton = new JButton("관리비 정보");
        feeInfoButton.setBounds(400, 20, 120, 25);
        feeInfoButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                FeeInfo.createAndShowGUI();
            }

        });

        panel.add(feeInfoButton);

        // 알림 보내기 버튼
        JButton sendNotificationButton = new JButton("알림 보내기");
        sendNotificationButton.setBounds(10, 60, 120, 25);
        
        sendNotificationButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                SendNotification.createAndShowGUI();
            }

        });

        panel.add(sendNotificationButton);

        // 정비 & 수리요청
        JButton MaintenanceButton = new JButton("정비&수리 요청");
        MaintenanceButton.setBounds(140, 60, 120, 25);

        MaintenanceButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                MaintenanceRequestManager.createAndShowGUI();
            }

        });
        panel.add(MaintenanceButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ApartmentManagementProgram::createAndShowMainGUI);
    }
}
