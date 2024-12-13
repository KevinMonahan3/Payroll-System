import javax.swing.*;

public class AdminGUI {
    public AdminGUI() {
        JFrame frame = new JFrame("Admin Dashboard");
        JLabel welcomeLabel = new JLabel("Welcome Admin! Features coming soon!");
        JPanel panel = new JPanel();
        panel.add(welcomeLabel);
        frame.add(panel);

        frame.setSize(300, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
