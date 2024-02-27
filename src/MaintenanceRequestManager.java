import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MaintenanceRequestManager extends JFrame {
    private List<MaintenanceRequest> requestList;
    private JTextArea requestTextArea;
    private JTextField nameField;
    private JTextField issueField;
    private JButton addButton;

    public MaintenanceRequestManager() {
        setTitle("수리 & 정비 요청");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        requestList = new ArrayList<>();

        JPanel panel = new JPanel(new BorderLayout());

        // 요청 목록 텍스트 영역
        requestTextArea = new JTextArea();
        requestTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(requestTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // 요청 추가 필드 및 버튼
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("이름:"));
        nameField = new JTextField();
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("메시지:"));
        issueField = new JTextField();
        inputPanel.add(issueField);
        addButton = new JButton("요청");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String issue = issueField.getText();
                if (!name.isEmpty() && !issue.isEmpty()) {
                    MaintenanceRequest request = new MaintenanceRequest(name, issue);
                    requestList.add(request);
                    updateRequestTextArea();
                    clearInputFields();
                } else {
                    JOptionPane.showMessageDialog(panel, "수리 & 정비를 요청할 정보를 입력하세요.");
                }
            }
        });
        inputPanel.add(addButton);
        panel.add(inputPanel, BorderLayout.SOUTH);

        getContentPane().add(panel);
    }

    private void updateRequestTextArea() {
        StringBuilder sb = new StringBuilder();
        for (MaintenanceRequest request : requestList) {
            sb.append("이름: ").append(request.getName()).append("\n");
            sb.append("메시지: ").append(request.getIssue()).append("\n\n");
        }
        requestTextArea.setText(sb.toString());
    }

    private void clearInputFields() {
        nameField.setText("");
        issueField.setText("");
    }

    public static void createAndShowGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MaintenanceRequestManager manager = new MaintenanceRequestManager();
                manager.setVisible(true);
            }
        });
    }
}

class MaintenanceRequest {
    private String name;
    private String issue;

    public MaintenanceRequest(String name, String issue) {
        this.name = name;
        this.issue = issue;
    }

    public String getName() {
        return name;
    }

    public String getIssue() {
        return issue;
    }
}