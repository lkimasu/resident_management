import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ApartmentManagementProgram {
    public static void createAndShowMainGUI() {
        JFrame frame = new JFrame("주민 관리 프로그램");
        frame.setSize(1500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JButton signUpButton = new JButton("회원가입");
        signUpButton.setBounds(10, 20, 120, 25);
        panel.add(signUpButton);

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 회원가입 창 열기
                join.createAndShowMainGUI();
            }
        });
        

        JButton residentInfoButton = new JButton("주민정보 확인");
        residentInfoButton.setBounds(140, 20, 120, 25);
        panel.add(residentInfoButton);

        JButton feeInputButton = new JButton("관리비 입력");
        feeInputButton.setBounds(270, 20, 120, 25);
        panel.add(feeInputButton);

        JButton feeInfoButton = new JButton("관리비 정보");
        feeInfoButton.setBounds(400, 20, 120, 25);
        panel.add(feeInfoButton);

        JButton sendNotificationButton = new JButton("알림 보내기");
        sendNotificationButton.setBounds(530, 20, 120, 25);
        panel.add(sendNotificationButton);

        // 주민 커뮤니티 포럼 버튼
        JButton communityForumButton = new JButton("커뮤니티 포럼");
        communityForumButton.setBounds(660, 20, 120, 25);
        panel.add(communityForumButton);

        // 시설 예약 버튼
        JButton facilityReservationButton = new JButton("시설 예약");
        facilityReservationButton.setBounds(790, 20, 120, 25);
        panel.add(facilityReservationButton);

        // 리마인더 버튼
        JButton reminderButton = new JButton("리마인더");
        reminderButton.setBounds(920, 20, 120, 25);
        panel.add(reminderButton);

        // 이벤트 캘린더 버튼
        JButton eventCalendarButton = new JButton("이벤트 캘린더");
        eventCalendarButton.setBounds(1050, 20, 120, 25);
        panel.add(eventCalendarButton);
    }

    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ApartmentManagementProgram::createAndShowMainGUI);
    }
}
