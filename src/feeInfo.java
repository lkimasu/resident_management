import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Properties;


public class FeeInfo {
    private static JComboBox<String> comboBox;
    private static JTextField searchField;
    private static DefaultTableModel tableModel;
    private static Properties props;

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

        // 검색 조건 선택을 위한 콤보박스 추가
        comboBox = new JComboBox<>();
        comboBox.addItem("년월일");
        comboBox.addItem("호수");
        comboBox.setBounds(10, 20, 120, 25);
        panel.add(comboBox);

        // 검색어 입력 필드 추가
        searchField = new JTextField(20);
        searchField.setBounds(140, 20, 200, 25);
        panel.add(searchField);

        // 검색 버튼 추가
        JButton searchButton = new JButton("검색");
        searchButton.setBounds(350, 20, 80, 25);
        panel.add(searchButton);

        // 결과를 표시할 테이블 모델 생성
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 셀 편집 금지
            }
        };
        JTable resultTable = new JTable(tableModel);

        // 컬럼 이름 설정
        String[] columnNames = {"년월일", "호수", "전기세", "수도세", "가스비", "경비비", "합계"};
        tableModel.setColumnIdentifiers(columnNames);

        // 테이블을 스크롤 가능한 패널에 추가
        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBounds(10, 60, 760, 400);
        panel.add(scrollPane);

        // 검색 버튼에 대한 액션 리스너 추가
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 선택된 검색 조건 가져오기
                String selectedOption = (String) comboBox.getSelectedItem();
                // 검색어 가져오기
                String searchKeyword = searchField.getText();

                // MySQL 데이터베이스 연결 및 검색 수행
                try {
                    // 데이터베이스 연결 정보 읽기
                    String url = props.getProperty("db.url");
                    String user = props.getProperty("db.user");
                    String pass = props.getProperty("db.userpassword");

                    Connection conn = DriverManager.getConnection(url, user, pass);

                    HashMap<String, String> columnMap = new HashMap<>();
                    columnMap.put("연월", "days");
                    columnMap.put("호수", "apartment_number");

                    // 선택된 검색 조건에 해당하는 영어 컬럼 이름 가져오기
                    String columnName = columnMap.get(selectedOption);

                    String query = "SELECT days,apartment_number, electricity_fee,water_fee,gas_fee,security_fee FROM fee WHERE " + columnName + "=?";
                    PreparedStatement statement = conn.prepareStatement(query);
                    statement.setString(1, searchKeyword);
                    ResultSet resultSet = statement.executeQuery();

                    // 테이블 모델에 검색 결과 추가
                    tableModel.setRowCount(0); // 기존 데이터 지우기
                    while (resultSet.next()) {
                        String days = resultSet.getString("days");
                        String apartment_number = resultSet.getString("apartment_number");
                        String electricity_fee = resultSet.getString("electricity_fee");
                        String water_fee = resultSet.getString("water_fee");
                        String gas_fee = resultSet.getString("gas_fee");
                        String security_fee = resultSet.getString("security_fee");
        
                        // 전기세, 수도세, 가스세, 경비비에 대한 총 합 계산
                        int total = Integer.parseInt(electricity_fee) + Integer.parseInt(water_fee) + Integer.parseInt(gas_fee) + Integer.parseInt(security_fee);
        
                        Object[] rowData = {days, apartment_number, electricity_fee, water_fee, gas_fee, security_fee, total};
                        tableModel.addRow(rowData);
                    }

                    // 리소스 해제
                    resultSet.close();
                    statement.close();
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "검색 중 오류가 발생했습니다.");
                }
            }
        });

        // 수정 버튼 추가
        JButton updateButton = new JButton("수정");
        updateButton.setBounds(10, 490, 80, 25);
        panel.add(updateButton);

        // 수정 버튼에 대한 액션 리스너 추가
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = resultTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "수정할 항목을 선택하세요.");
                    return;
                }

                // 선택된 행의 데이터 가져오기
                String date = (String) tableModel.getValueAt(selectedRow, 0);
                String apartment_number = (String) tableModel.getValueAt(selectedRow, 1);
                String electricity_fee = (String) tableModel.getValueAt(selectedRow, 2);
                String water_fee = (String) tableModel.getValueAt(selectedRow, 3);
                String gas_fee = (String) tableModel.getValueAt(selectedRow, 4);
                String security_fee = (String) tableModel.getValueAt(selectedRow, 5);

                // 수정 대화 상자 표시
                JTextField datedField = new JTextField(date);
                JTextField apartmentnumberField = new JTextField(apartment_number);
                JTextField electricity_feeField = new JTextField(electricity_fee);
                JTextField water_feeField = new JTextField(water_fee);
                JTextField gas_feeField = new JTextField(gas_fee);
                JTextField security_feeField = new JTextField(security_fee);

                JPanel dialogPanel = new JPanel();
                dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
                dialogPanel.add(new JLabel("년월일"));
                dialogPanel.add(datedField);
                dialogPanel.add(new JLabel("호수"));
                dialogPanel.add(apartmentnumberField);
                dialogPanel.add(new JLabel("전기세"));
                dialogPanel.add(electricity_feeField);
                dialogPanel.add(new JLabel("수도세"));
                dialogPanel.add(water_feeField);
                dialogPanel.add(new JLabel("가스세"));
                dialogPanel.add(gas_feeField);
                dialogPanel.add(new JLabel("경비비"));
                dialogPanel.add(security_feeField);

                int result = JOptionPane.showConfirmDialog(null, dialogPanel, "정보 수정", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    // 사용자가 입력한 값 가져오기
                    String newDate = datedField.getText();
                    String newapartmentnumber = apartmentnumberField.getText();
                    String newelectricity = electricity_feeField.getText();
                    String newwater = water_feeField.getText();
                    String newgas = gas_feeField.getText();
                    String newsecurity = security_feeField.getText();

                    // 선택된 행의 데이터 업데이트
                    tableModel.setValueAt(newDate, selectedRow, 0);
                    tableModel.setValueAt(newapartmentnumber, selectedRow, 1);
                    tableModel.setValueAt(newelectricity, selectedRow, 2);
                    tableModel.setValueAt(newwater, selectedRow, 3);
                    tableModel.setValueAt(newgas, selectedRow, 4);
                    tableModel.setValueAt(newsecurity, selectedRow, 5);

                    // 데이터베이스 업데이트 실행
                    try {
                        String url = props.getProperty("db.url");
                        String user = props.getProperty("db.user");
                        String pass = props.getProperty("db.userpassword");

                        Connection conn = DriverManager.getConnection(url, user, pass);

                        // 선택된 행의 데이터를 업데이트하는 SQL 쿼리 실행
                        String updateQuery = "UPDATE fee SET days=?, apartment_number=?, electricity_fee=?, water_fee=?, gas_fee=?, security_fee=? WHERE days=?";
                        PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
                        updateStatement.setString(1, newDate);
                        updateStatement.setString(2, newapartmentnumber);
                        updateStatement.setString(3, newelectricity);
                        updateStatement.setString(4, newwater);
                        updateStatement.setString(5, newgas);
                        updateStatement.setString(6, newsecurity);
                        updateStatement.setString(7, date); // 이전 날짜로 업데이트
        
                        updateStatement.executeUpdate();

                        // 리소스 해제
                        updateStatement.close();
                        conn.close();

                        JOptionPane.showMessageDialog(null, "수정이 성공적으로 완료되었습니다.");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "데이터베이스 업데이트 중 오류가 발생했습니다.");
                    }
                }
            }
        });

        // 삭제 버튼 추가
        JButton deleteButton = new JButton("삭제");
        deleteButton.setBounds(100, 490, 80, 25);
        panel.add(deleteButton);

        // 삭제 버튼에 대한 액션 리스너 추가
     // 삭제 버튼에 대한 액션 리스너 추가
deleteButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedRow = resultTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "삭제할 항목을 선택하세요.");
            return;
        }

        // 선택된 행의 데이터 가져오기
        String date = (String) tableModel.getValueAt(selectedRow, 0);

        // 데이터베이스에서 해당 항목 삭제
        try {
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String pass = props.getProperty("db.userpassword");

            Connection conn = DriverManager.getConnection(url, user, pass);

            // 선택된 행의 데이터를 삭제하는 SQL 쿼리 실행
            String deleteQuery = "DELETE FROM fee WHERE days=?";
            PreparedStatement deleteStatement = conn.prepareStatement(deleteQuery);
            deleteStatement.setString(1, date);

            deleteStatement.executeUpdate();

            // 리소스 해제
            deleteStatement.close();
            conn.close();

            // 테이블 모델에서 해당 행 삭제
            tableModel.removeRow(selectedRow);

            JOptionPane.showMessageDialog(null, "삭제가 성공적으로 완료되었습니다.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "데이터베이스 삭제 중 오류가 발생했습니다.");
        }
    }
});
    }

    public static void main(String[] args) {
        try {
            // database.properties 파일 로드
            props = new Properties();
            props.load(new FileInputStream("src/database.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "데이터베이스 연결 정보를 로드하는 중 오류가 발생했습니다.");
            System.exit(1);
        }
        createAndShowGUI();
    }
}
