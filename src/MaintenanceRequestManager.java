import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

public class MaintenanceRequestManager {
    private static JComboBox<String> comboBox;
    private static JTextField searchField;
    private static DefaultTableModel tableModel;
    private static Properties props;

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("정비 & 수리 요청");
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
        comboBox.addItem("요청자");
        comboBox.addItem("호수");
        comboBox.addItem("요청일시");
        comboBox.addItem("요청한 내용");
        comboBox.addItem("완료일시");
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
        String[] columnNames = {"요청자", "호수", "요청일시", "요청내용", "완료일시"};
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
                    columnMap.put("요청자", "requestname");
                    columnMap.put("호수", "apartment_number");
                    columnMap.put("요청일시", "requestdate");
                    columnMap.put("요청내용", "requesttext");
                    columnMap.put("완료일시", "completedate");

                    // 선택된 검색 조건에 해당하는 영어 컬럼 이름 가져오기
                    String columnName = columnMap.get(selectedOption);

                    String query = "SELECT requestname,apartment_number,requestdate,requesttext,completedate FROM request WHERE " + columnName + "=?";
                    PreparedStatement statement = conn.prepareStatement(query);
                    statement.setString(1, searchKeyword);
                    ResultSet resultSet = statement.executeQuery();

                    // 테이블 모델에 검색 결과 추가
                    tableModel.setRowCount(0); // 기존 데이터 지우기
                    while (resultSet.next()) {
                        Object[] rowData = {resultSet.getString("requestname"), resultSet.getString("apartment_number"), resultSet.getString("requestdate"), resultSet.getString("requesttext"), resultSet.getString("completedate")};
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

        // 요청 버튼 추가
        JButton requestButton = new JButton("요청");
        requestButton.setBounds(200, 490, 80, 25);
        panel.add(requestButton);

        // 요청 버튼에 대한 액션 리스너 추가
        requestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 요청 대화 상자 표시
                JTextField requestNameField = new JTextField();
                JTextField apartmentNumberField = new JTextField();
                JTextField requestTextField = new JTextField();

                JPanel dialogPanel = new JPanel();
                dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
                dialogPanel.add(new JLabel("요청자:"));
                dialogPanel.add(requestNameField);
                dialogPanel.add(new JLabel("호수:"));
                dialogPanel.add(apartmentNumberField);
                dialogPanel.add(new JLabel("요청 내용:"));
                dialogPanel.add(requestTextField);

                int result = JOptionPane.showConfirmDialog(null, dialogPanel, "수리 & 정비 요청", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    // 사용자가 입력한 값 가져오기
                    String requestName = requestNameField.getText();
                    String apartmentNumber = apartmentNumberField.getText();
                    String requestText = requestTextField.getText();
                    String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                    // 테이블 모델에 요청 추가
                    Object[] rowData = {requestName, apartmentNumber, currentDate, requestText, ""};
                    tableModel.addRow(rowData);

                    // 데이터베이스에 요청 추가
                    try {
                        String url = props.getProperty("db.url");
                        String user = props.getProperty("db.user");
                        String pass = props.getProperty("db.userpassword");

                        Connection conn = DriverManager.getConnection(url, user, pass);

                        // 요청 추가 SQL 쿼리 실행
                        String insertQuery = "INSERT INTO request (requestname, apartment_number, requestdate, requesttext) VALUES (?, ?, ?, ?)";
                        PreparedStatement insertStatement = conn.prepareStatement(insertQuery);
                        insertStatement.setString(1, requestName);
                        insertStatement.setString(2, apartmentNumber);
                        insertStatement.setString(3, currentDate);
                        insertStatement.setString(4, requestText);

                        insertStatement.executeUpdate();

                        // 리소스 해제
                        insertStatement.close();
                        conn.close();

                        JOptionPane.showMessageDialog(null, "요청이 성공적으로 등록되었습니다.");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "데이터베이스에 요청을 추가하는 중 오류가 발생했습니다.");
                    }
                }
            }
        });

        // 수정 버튼 추가
        JButton updateButton = new JButton("수정");
        updateButton.setBounds(300, 490, 80, 25);
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
                String requestName = (String) tableModel.getValueAt(selectedRow, 0);
                String apartmentNumber = (String) tableModel.getValueAt(selectedRow, 1);
                String requestDate = (String) tableModel.getValueAt(selectedRow, 2);
                String requestText = (String) tableModel.getValueAt(selectedRow, 3);

                // 수정 대화 상자 표시
                JTextField requestNameField = new JTextField(requestName);
                JTextField apartmentNumberField = new JTextField(apartmentNumber);
                JTextField requestDateField = new JTextField(requestDate);
                JTextField requestTextField = new JTextField(requestText);

                JPanel dialogPanel = new JPanel();
                dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
                dialogPanel.add(new JLabel("요청자:"));
                dialogPanel.add(requestNameField);
                dialogPanel.add(new JLabel("호수:"));
                dialogPanel.add(apartmentNumberField);
                dialogPanel.add(new JLabel("요청일시:"));
                dialogPanel.add(requestDateField);
                dialogPanel.add(new JLabel("요청 내용:"));
                dialogPanel.add(requestTextField);

                int result = JOptionPane.showConfirmDialog(null, dialogPanel, "정보 수정", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    // 사용자가 입력한 값 가져오기
                    String newRequestName = requestNameField.getText();
                    String newApartmentNumber = apartmentNumberField.getText();
                    String newRequestDate = requestDateField.getText();
                    String newRequestText = requestTextField.getText();

                    // 선택된 행의 데이터 업데이트
                    tableModel.setValueAt(newRequestName, selectedRow, 0);
                    tableModel.setValueAt(newApartmentNumber, selectedRow, 1);
                    tableModel.setValueAt(newRequestDate, selectedRow, 2);
                    tableModel.setValueAt(newRequestText, selectedRow, 3);

                    // 데이터베이스 업데이트 실행
                    try {
                        String url = props.getProperty("db.url");
                        String user = props.getProperty("db.user");
                        String pass = props.getProperty("db.userpassword");

                        Connection conn = DriverManager.getConnection(url, user, pass);

                        // 선택된 행의 데이터를 업데이트하는 SQL 쿼리 실행
                        String updateQuery = "UPDATE request SET requestname=?, apartment_number=?, requestdate=?, requesttext=? WHERE requestname=? AND apartment_number=? AND requestdate=?";
                        PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
                        updateStatement.setString(1, newRequestName);
                        updateStatement.setString(2, newApartmentNumber);
                        updateStatement.setString(3, newRequestDate);
                        updateStatement.setString(4, newRequestText);
                        updateStatement.setString(5, requestName);
                        updateStatement.setString(6, apartmentNumber);
                        updateStatement.setString(7, requestDate);

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
        deleteButton.setBounds(400, 490, 80, 25);
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
                String requestName = (String) tableModel.getValueAt(selectedRow, 0);
                String apartmentNumber = (String) tableModel.getValueAt(selectedRow, 1);
                String requestDate = (String) tableModel.getValueAt(selectedRow, 2);

                // 데이터베이스에서 해당 항목 삭제
                try {
                    String url = props.getProperty("db.url");
                    String user = props.getProperty("db.user");
                    String pass = props.getProperty("db.userpassword");

                    Connection conn = DriverManager.getConnection(url, user, pass);

                    // 선택된 행의 데이터를 삭제하는 SQL 쿼리 실행
                    String deleteQuery = "DELETE FROM request WHERE requestname=? AND apartment_number=? AND requestdate=?";
                    PreparedStatement deleteStatement = conn.prepareStatement(deleteQuery);
                    deleteStatement.setString(1, requestName);
                    deleteStatement.setString(2, apartmentNumber);
                    deleteStatement.setString(3, requestDate);

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


        // 완료 버튼 추가
JButton completeButton = new JButton("완료");
completeButton.setBounds(500, 490, 80, 25);
panel.add(completeButton);

// 완료 버튼에 대한 액션 리스너 추가
completeButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedRow = resultTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "완료할 항목을 선택하세요.");
            return;
        }

        // 선택된 행의 데이터 가져오기
        String requestName = (String) tableModel.getValueAt(selectedRow, 0);
        String apartmentNumber = (String) tableModel.getValueAt(selectedRow, 1);
        String requestDate = (String) tableModel.getValueAt(selectedRow, 2);

        // 현재 날짜 가져오기
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        // 데이터베이스에 완료일시 업데이트
        try {
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String pass = props.getProperty("db.userpassword");

            Connection conn = DriverManager.getConnection(url, user, pass);

            // 선택된 행의 완료일시 업데이트하는 SQL 쿼리 실행
            String updateQuery = "UPDATE request SET completedate=? WHERE requestname=? AND apartment_number=? AND requestdate=?";
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setString(1, currentDate);
            updateStatement.setString(2, requestName);
            updateStatement.setString(3, apartmentNumber);
            updateStatement.setString(4, requestDate);

            updateStatement.executeUpdate();

            // 테이블 모델에서 완료일시 업데이트
            tableModel.setValueAt(currentDate, selectedRow, 4);

            // 리소스 해제
            updateStatement.close();
            conn.close();

            JOptionPane.showMessageDialog(null, "완료 처리가 성공적으로 완료되었습니다.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "데이터베이스 업데이트 중 오류가 발생했습니다.");
        }
    }
});
    }

    

    public static void main(String[] args) {
        try {
            // database.properties 파일 로드
            props = new Properties();
            props.load(new FileInputStream("src/database.properties"));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "데이터베이스 연결 정보를 로드하는 중 오류가 발생했습니다.");
            System.exit(1);
        }
        createAndShowGUI();
    }
}
