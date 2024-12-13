import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.util.List;
import com.opencsv.CSVReader;

public class AccountGUI {
    private final String employeeFile = "lib/Employee.csv";

    public AccountGUI() {
        JFrame frame = new JFrame("Login");
        JLabel userLabel = new JLabel("Enter Username:");
        JTextField userField = new JTextField(15);
        JLabel passLabel = new JLabel("Enter Password:");
        JPasswordField passField = new JPasswordField(15);
        JButton loginButton = new JButton("Login");

        JPanel panel = new JPanel();
        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(loginButton);
        frame.add(panel);

        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText().trim();
                String password = new String(passField.getPassword()).trim();
                
                String role = validateLogin(username, password);

                if (role != null) {
                    frame.dispose();
                    switch (role.toLowerCase()) {
                        case "admin":
                            SwingUtilities.invokeLater(AdminGUI::new);
                            break;
                        case "hr":
                            SwingUtilities.invokeLater(HRGUI::new);
                            break;
                        case "employee":
                            SwingUtilities.invokeLater(EmployeeGUI::new);
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Unknown role. Please contact admin.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials. Please try again.");
                }
            }
        });
    }

    private String validateLogin(String username, String password) {
        try (CSVReader reader = new CSVReader(new FileReader(employeeFile))) {
            List<String[]> records = reader.readAll();
            records.remove(0);
            for (String[] row : records) {
                if (row[0].equals(username) && row[1].equals(password)) {
                    return row[2];
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error reading employee file: " + e.getMessage());
        }
        return null;
    }
}
