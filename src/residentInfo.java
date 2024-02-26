import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResidentInfo {
    public static void createAndShowGUI() {
        JFrame frame = new JFrame("주민정보 검색");
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
        JLabel searchLabel = new JLabel("검색어:");
        searchLabel.setBounds(10, 20, 80, 25);
        panel.add(searchLabel);

        JTextField searchField = new JTextField(20);
        searchField.setBounds(100, 20, 200, 25);
        panel.add(searchField);

        // 검색 버튼 추가
        JButton searchButton = new JButton("검색");
        searchButton.setBounds(310, 20, 80, 25);
        panel.add(searchButton);

        // 결과를 표시할 테이블 모델 생성
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable resultTable = new JTable(tableModel);

        // 컬럼 이름 설정
        String[] columnNames = {"아이디", "세대주 이름", "호수", "구성원 이름", "휴대폰 번호", "이메일 주소"};
        tableModel.setColumnIdentifiers(columnNames);

        // 테이블을 스크롤 가능한 패널에 추가
        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBounds(10, 60, 760, 400);
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
                String searchKeyword = searchField.getText();

                // 검색된 결과를 임시로 만듭니다. 실제로는 데이터베이스 또는 다른 저장소에서 검색해야 합니다.
                // 여기에서는 더미 데이터를 사용합니다.
                Object[][] data = {
                        {"ID001", "김철수", "101", "아들1", "010-1234-5678", "kim@example.com"},
                        {"ID002", "이영희", "102", "딸1", "010-9876-5432", "lee@example.com"}
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
        SwingUtilities.invokeLater(ResidentInfo::createAndShowGUI);
    }
}
