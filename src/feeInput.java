import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class feeInput {
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

        // 월 입력
        JLabel monthLabel = new JLabel("월:");
        monthLabel.setBounds(10, 20, 80, 25);
        panel.add(monthLabel);

        JTextField yearField = new JTextField(10);
        yearField.setBounds(100, 20, 80, 25);
        panel.add(yearField);

        JLabel yearLabel = new JLabel("년");
        yearLabel.setBounds(190, 20, 20, 25);
        panel.add(yearLabel);

        JTextField monthField = new JTextField(10);
        monthField.setBounds(220, 20, 80, 25);
        panel.add(monthField);

        JLabel monthValueLabel = new JLabel("월");
        monthValueLabel.setBounds(310, 20, 20, 25);
        panel.add(monthValueLabel);

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
                String year = yearField.getText();
                String month = monthField.getText();
                String floor = floorField.getText();
                String electricityFee = electricityField.getText();
                String waterFee = waterField.getText();
                String gasFee = gasField.getText();
                String securityFee = securityField.getText();

                // 여기서 입력된 값을 사용하여 관리비 입력 로직을 수행합니다.
                // 예: 데이터베이스에 관리비 정보를 저장하는 등의 작업을 수행합니다.
                // 이 부분을 실제 관리비 입력 로직으로 대체해주세요.
                System.out.println("Year: " + year);
                System.out.println("Month: " + month);
                System.out.println("Floor: " + floor);
                System.out.println("Electricity Fee: " + electricityFee);
                System.out.println("Water Fee: " + waterFee);
                System.out.println("Gas Fee: " + gasFee);
                System.out.println("Security Fee: " + securityFee);

                // 입력 후에 필요한 로직 추가 가능
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(feeInput::createAndShowGUI);
    }
}
