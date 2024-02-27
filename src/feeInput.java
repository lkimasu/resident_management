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

public class FeeInput {
   
    public static void createAndShowGUI() {
        JFrame frame = new JFrame("관리비 입력");
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

        // 연월 입력
        JLabel dateLabel = new JLabel("연 월:");
        dateLabel.setBounds(10, 20, 80, 25);
        panel.add(dateLabel);

        JTextField dateField = new JTextField(10);
        dateField.setBounds(100, 20, 200, 25);
        panel.add(dateField);

        // 호수 입력
        JLabel floorLabel = new JLabel("호수:");
        floorLabel.setBounds(10, 50, 80, 25);
        panel.add(floorLabel);

        JTextField floorField = new JTextField(10);
        floorField.setBounds(100, 50, 200, 25);
        panel.add(floorField);

        // 전기세 입력
        JLabel electricityLabel = new JLabel("전기세:");
        electricityLabel.setBounds(10, 80, 80, 25);
        panel.add(electricityLabel);

        JTextField electricityField = new JTextField(10);
        electricityField.setBounds(100, 80, 200, 25);
        panel.add(electricityField);

        // 수도세 입력
        JLabel waterLabel = new JLabel("수도세:");
        waterLabel.setBounds(10, 110, 80, 25);
        panel.add(waterLabel);

        JTextField waterField = new JTextField(10);
        waterField.setBounds(100, 110, 200, 25);
        panel.add(waterField);

        // 가스세 입력
        JLabel gasLabel = new JLabel("가스세:");
        gasLabel.setBounds(10, 140, 80, 25);
        panel.add(gasLabel);

        JTextField gasField = new JTextField(10);
        gasField.setBounds(100, 140, 200, 25);
        panel.add(gasField);

        // 경비비 입력
        JLabel securityLabel = new JLabel("경비비:");
        securityLabel.setBounds(10, 170, 80, 25);
        panel.add(securityLabel);

        JTextField securityField = new JTextField(10);
        securityField.setBounds(100, 170, 200, 25);
        panel.add(securityField);

        // 관리비 입력 버튼
        JButton inputFeeButton = new JButton("관리비 입력");
        inputFeeButton.setBounds(10, 200, 120, 25);
        panel.add(inputFeeButton);

        // 버튼에 대한 액션 리스너 추가
        inputFeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 입력된 값 가져오기
                String date = dateField.getText();
                String floor = floorField.getText();
                String electricityFee = electricityField.getText();
                String waterFee = waterField.getText();
                String gasFee = gasField.getText();
                String securityFee = securityField.getText();

                // 데이터베이스에 연결하여 관리비 정보를 저장
                Properties props = new Properties();
                try (FileInputStream in = new FileInputStream("src/database.properties")) {
                    props.load(in);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "데이터베이스 연결 정보를 로드하는 중 오류가 발생했습니다.");
                    return;
                }

                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.userpassword");

                try (Connection conn = DriverManager.getConnection(url, user, password)) {
                    // SQL 쿼리 작성
                    String sql = "INSERT INTO fee (days,apartment_number, electricity_fee, water_fee, gas_fee, security_fee) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setString(1, date);
                    statement.setString(2, floor);
                    statement.setString(3, electricityFee);
                    statement.setString(4, waterFee);
                    statement.setString(5, gasFee);
                    statement.setString(6, securityFee);

                    // 쿼리 실행
                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "관리비가 성공적으로 입력되었습니다.");
                    } else {
                        JOptionPane.showMessageDialog(null, "관리비 입력에 실패하였습니다.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "데이터베이스 연결 또는 쿼리 실행 중 오류가 발생했습니다.");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FeeInput::createAndShowGUI);
    }
}
