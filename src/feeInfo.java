import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FeeInfo {
    public static void createAndShowGUI() {
        JFrame frame = new JFrame("관리비 정보 확인");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setLocationRelativeTo(null); // 창을 화면 중앙에 배치
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // 레이블 및 텍스트 필드 추가
        JLabel monthLabel = new JLabel("월:");
        monthLabel.setBounds(10, 20, 80, 25);
        panel.add(monthLabel);
        JTextField monthField = new JTextField(20);
        monthField.setBounds(100, 20, 200, 25);
        panel.add(monthField);

        JLabel floorLabel = new JLabel("호수:");
        floorLabel.setBounds(10, 50, 80, 25);
        panel.add(floorLabel);
        JTextField floorField = new JTextField(20);
        floorField.setBounds(100, 50, 200, 25);
        panel.add(floorField);

        // 검색 버튼 추가
        JButton searchButton = new JButton("검색");
        searchButton.setBounds(310, 20, 80, 25);
        panel.add(searchButton);

        // 결과를 표시할 테이블 모델 생성
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable resultTable = new JTable(tableModel);

        // 컬럼 이름 설정
        String[] columnNames = {"월", "호수", "전기세", "수도세", "가스비", "경비비", "합계"};
        tableModel.setColumnIdentifiers(columnNames);

        // 테이블을 스크롤 가능한 패널에 추가
        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBounds(10, 80, 760, 400);
        panel.add(scrollPane);

        // 수정 버튼 추가
        JButton updateButton = new JButton("수정");
        updateButton.setBounds(10, 490, 80, 25);
        panel.add(updateButton);

        // 삭제 버튼 추가
        JButton deleteButton = new JButton("삭제");
        deleteButton.setBounds(100, 490, 80, 25);
        panel.add(deleteButton);

        // 검색 버튼에 대한 액션 리스너 추가
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 검색어 가져오기
                String month = monthField.getText();
                String floor = floorField.getText();

                // 검색된 결과를 임시로 만듭니다. 실제로는 데이터베이스 또는 다른 저장소에서 검색해야 합니다.
                // 여기에서는 더미 데이터를 사용합니다.
                Object[][] data = {
                        {"2024-01", "101", "100000", "20000", "30000", "50000", "200000"},
                        {"2024-01", "102", "120000", "25000", "35000", "55000", "230000"}
                        // 검색 결과에 따라서 실제 데이터를 채워넣어야 합니다.
                };

                // 테이블 모델에 데이터를 추가
                tableModel.setDataVector(data, columnNames);
            }
        });

        // 수정 버튼에 대한 액션 리스너 추가
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 수정 버튼 클릭 시 수행할 동작 추가
                // 선택된 행의 데이터를 가져와서 수정하는 로직을 추가해야 합니다.
            }
        });

        // 삭제 버튼에 대한 액션 리스너 추가
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 삭제 버튼 클릭 시 수행할 동작 추가
                // 선택된 행을 삭제하는 로직을 추가해야 합니다.
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FeeInfo::createAndShowGUI);
    }
}
