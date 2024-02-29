import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Properties;

public class ResidentInfo {
    private static JComboBox<String> comboBox;
    private static JTextField searchField;
    private static DefaultTableModel tableModel;
    private static Properties props;

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("주민정보");
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
        comboBox.addItem("아이디");
        comboBox.addItem("세대주 이름");
        comboBox.addItem("호수");
        comboBox.addItem("구성원 이름");
        comboBox.addItem("휴대폰 번호");
        comboBox.addItem("이메일 주소");
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
        String[] columnNames = {"아이디", "세대주 이름", "호수", "구성원 이름", "휴대폰 번호", "이메일 주소"};
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
                    columnMap.put("아이디", "user_id");
                    columnMap.put("세대주 이름", "name");
                    columnMap.put("호수", "apartment_number");
                    columnMap.put("구성원 이름", "member_name");
                    columnMap.put("휴대폰 번호", "phone");
                    columnMap.put("이메일 주소", "email");

                    // 선택된 검색 조건에 해당하는 영어 컬럼 이름 가져오기
                    String columnName = columnMap.get(selectedOption);

                    String query = "SELECT user_id, name, apartment_number, member_name, phone, email FROM user_join WHERE " + columnName + "=?";
                    PreparedStatement statement = conn.prepareStatement(query);
                    statement.setString(1, searchKeyword);
                    ResultSet resultSet = statement.executeQuery();

                    // 테이블 모델에 검색 결과 추가
                    tableModel.setRowCount(0); // 기존 데이터 지우기
                    while (resultSet.next()) {
                        Object[] rowData = {resultSet.getString("user_id"), resultSet.getString("name"), resultSet.getString("apartment_number"), resultSet.getString("member_name"), resultSet.getString("phone"), resultSet.getString("email")};
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
                String userId = (String) tableModel.getValueAt(selectedRow, 0);
                String name = (String) tableModel.getValueAt(selectedRow, 1);
                String apartmentNumber = (String) tableModel.getValueAt(selectedRow, 2);
                String memberName = (String) tableModel.getValueAt(selectedRow, 3);
                String phone = (String) tableModel.getValueAt(selectedRow, 4);
                String email = (String) tableModel.getValueAt(selectedRow, 5);

                // 수정 대화 상자 표시
                JTextField userIdField = new JTextField(userId);
                JTextField nameField = new JTextField(name);
                JTextField apartmentNumberField = new JTextField(apartmentNumber);
                JTextField memberNameField = new JTextField(memberName);
                JTextField phoneField = new JTextField(phone);
                JTextField emailField = new JTextField(email);

                JPanel dialogPanel = new JPanel();
                dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
                dialogPanel.add(new JLabel("아이디:"));
                dialogPanel.add(userIdField);
                dialogPanel.add(new JLabel("세대주 이름:"));
                dialogPanel.add(nameField);
                dialogPanel.add(new JLabel("호수:"));
                dialogPanel.add(apartmentNumberField);
                dialogPanel.add(new JLabel("구성원 이름:"));
                dialogPanel.add(memberNameField);
                dialogPanel.add(new JLabel("휴대폰 번호:"));
                dialogPanel.add(phoneField);
                dialogPanel.add(new JLabel("이메일 주소:"));
                dialogPanel.add(emailField);

                int result = JOptionPane.showConfirmDialog(null, dialogPanel, "정보 수정", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    // 사용자가 입력한 값 가져오기
                    String newUserId = userIdField.getText();
                    String newName = nameField.getText();
                    String newApartmentNumber = apartmentNumberField.getText();
                    String newMemberName = memberNameField.getText();
                    String newPhone = phoneField.getText();
                    String newEmail = emailField.getText();

                    // 선택된 행의 데이터 업데이트
                    tableModel.setValueAt(newUserId, selectedRow, 0);
                    tableModel.setValueAt(newName, selectedRow, 1);
                    tableModel.setValueAt(newApartmentNumber, selectedRow, 2);
                    tableModel.setValueAt(newMemberName, selectedRow, 3);
                    tableModel.setValueAt(newPhone, selectedRow, 4);
                    tableModel.setValueAt(newEmail, selectedRow, 5);

                    // 데이터베이스 업데이트 실행
                    try {
                        String url = props.getProperty("db.url");
                        String user = props.getProperty("db.user");
                        String pass = props.getProperty("db.userpassword");

                        Connection conn = DriverManager.getConnection(url, user, pass);

                        // 선택된 행의 데이터를 업데이트하는 SQL 쿼리 실행
                        String updateQuery = "UPDATE user_join SET user_id=?, name=?, apartment_number=?, member_name=?, phone=?, email=? WHERE user_id=?";
                        PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
                        updateStatement.setString(1, newUserId);
                        updateStatement.setString(2, newName);
                        updateStatement.setString(3, newApartmentNumber);
                        updateStatement.setString(4, newMemberName);
                        updateStatement.setString(5, newPhone);
                        updateStatement.setString(6, newEmail);
                        updateStatement.setString(7, userId); // 이전 아이디로 업데이트

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
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = resultTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "삭제할 항목을 선택하세요.");
                    return;
                }

                // 선택된 행의 데이터 가져오기
                String userId = (String) tableModel.getValueAt(selectedRow, 0);

                // 데이터베이스에서 해당 항목 삭제
                try {
                    String url = props.getProperty("db.url");
                    String user = props.getProperty("db.user");
                    String pass = props.getProperty("db.userpassword");

                    Connection conn = DriverManager.getConnection(url, user, pass);

                    // 선택된 행의 데이터를 삭제하는 SQL 쿼리 실행
                    String deleteQuery = "DELETE FROM user_join WHERE user_id=?";
                    PreparedStatement deleteStatement = conn.prepareStatement(deleteQuery);
                    deleteStatement.setString(1, userId);

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
    
            // GUI 생성 및 표시
            createAndShowGUI();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "데이터베이스 연결 정보를 로드하는 중 오류가 발생했습니다.");
            System.exit(1);
        }
    }
    
}
