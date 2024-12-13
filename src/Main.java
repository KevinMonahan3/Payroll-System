import java.util.Scanner;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Account acc = new Account();
        boolean more = true;
        
        Scanner scanner = new Scanner(System.in);
        while(more) {
            System.out.println("C)ommand-Line G)UI Q)uit");
            String input = scanner.nextLine().toUpperCase();
            if (input.equals("C")) {
                acc.Login();
                more = false;
            } else if (input.equals("G")) {
                SwingUtilities.invokeLater(AccountGUI::new);
                more = false;
            } else if (input.equals("Q")) {
                more = false;
            } else {
                System.out.println("Incorrect Input!");

            }

        }   

        scanner.close();

    }
}
