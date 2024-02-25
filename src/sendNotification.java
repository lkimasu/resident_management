import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class sendNotification {

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("메일 보내기");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setLocationRelativeTo(null); // 창을 화면 중앙에 배치
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // From 레이블 및 텍스트 필드
        JLabel fromLabel = new JLabel("From");
        fromLabel.setBounds(10, 20, 80, 25);
        panel.add(fromLabel);
        JTextField fromField = new JTextField(20);
        fromField.setBounds(100, 20, 250, 25);
        panel.add(fromField);

        // To 레이블 및 텍스트 필드
        JLabel toLabel = new JLabel("To");
        toLabel.setBounds(10, 50, 80, 25);
        panel.add(toLabel);
        JTextField toField = new JTextField(20);
        toField.setBounds(100, 50, 250, 25);
        panel.add(toField);

        // 제목 레이블 및 텍스트 필드
        JLabel subjectLabel = new JLabel("제목");
        subjectLabel.setBounds(10, 80, 80, 25);
        panel.add(subjectLabel);
        JTextField subjectField = new JTextField(20);
        subjectField.setBounds(100, 80, 250, 25);
        panel.add(subjectField);

        // 내용 레이블 및 텍스트 영역
        JLabel contentLabel = new JLabel("내용");
        contentLabel.setBounds(10, 110, 80, 25);
        panel.add(contentLabel);
        JTextArea contentArea = new JTextArea();
        contentArea.setBounds(100, 110, 250, 100);
        panel.add(contentArea);

        // 보내기 버튼
        JButton sendButton = new JButton("보내기");
        sendButton.setBounds(100, 220, 100, 25);
        panel.add(sendButton);

        // 보내기 버튼에 대한 액션 리스너 추가
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String from = fromField.getText();
                String to = toField.getText();
                String subject = subjectField.getText();
                String content = contentArea.getText();

                // Gmail SMTP 서버 설정
                String host = "smtp.gmail.com";
                String port = "587";
                String username = "your-email@gmail.com"; // 본인의 Gmail 주소
                String password = "your-password"; // 본인의 Gmail 앱 비밀번호

                // SMTP 서버 속성 설정
                Properties props = new Properties();
                props.put("mail.smtp.host", host);
                props.put("mail.smtp.port", port);
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");

                // 세션 생성
                Session session = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

                try {
                    // 메시지 객체 생성
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(from));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                    message.setSubject(subject);
                    message.setText(content);

                    // 메일 보내기
                    Transport.send(message);

                    // 전송 성공 메시지 출력
                    JOptionPane.showMessageDialog(panel, "메일 전송이 완료되었습니다.");
                } catch (MessagingException ex) {
                    // 전송 실패 메시지 출력
                    JOptionPane.showMessageDialog(panel, "메일 전송에 실패했습니다.\n오류 메시지: " + ex.getMessage());
                }
            }
        });
    }
}
