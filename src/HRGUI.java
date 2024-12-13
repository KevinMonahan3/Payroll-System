import javax.swing.*;

public class HRGUI {
    public HRGUI() {
        JFrame frame = new JFrame("HR Dashboard");
        JLabel welcomeLabel = new JLabel("Welcome HR! Features coming soon!");
        JPanel panel = new JPanel();
        panel.add(welcomeLabel);
        frame.add(panel);

        frame.setSize(300, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
