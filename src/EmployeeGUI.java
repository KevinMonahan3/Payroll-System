import javax.swing.*;

public class EmployeeGUI {
    public EmployeeGUI() {
        JFrame frame = new JFrame("Employee Dashboard");
        JLabel welcomeLabel = new JLabel("Welcome Employee! Features coming soon!");
        JPanel panel = new JPanel();
        panel.add(welcomeLabel);
        frame.add(panel);

        frame.setSize(300, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
