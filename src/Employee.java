import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import com.opencsv.CSVReader;

public class Employee extends Account {
    private Payslip payslip;
    private String[] cols;
    private  String payslipFile = "lib/payslips.csv";

    /**Gives access to the employee file and the current employee*/
    public Employee(String[] cols) {
        this.cols = cols;
        this.payslip = new Payslip(this.cols);
    }

    /**Views employee details*/
    public void viewDetails() {
        System.out.println(Arrays.toString(cols));
    }

    public void viewPayslip() {
        payslip.writeFullTime(cols);
    }

    public void viewParttimepayslip(String []cols ,String hoursworked){
        payslip.Writeparttime(cols,hoursworked);
    }

    public void viewPrevious(String loggedInUserId) {
        ArrayList<String[]> matchingRows = new ArrayList<>(); // Store matching rows

        try (CSVReader reader = new CSVReader(new FileReader(payslipFile))) {
            String[] row;
            String[] header = reader.readNext();
            if (header == null) {
                System.out.println("The CSV file is empty or missing.");
                return;
            }
    
            System.out.println(Arrays.toString(header));
           
            /**Read each row from the CSV*/
            while ((row = reader.readNext()) != null) {
                /**Compare the first column (index 0) with the logged-in user's ID*/
                if (row[0].equals(loggedInUserId)) {
                    /**Add matching payslip to a list*/
                    matchingRows.add(row);
                }
            }
           
            /**Finds all payslips belonging to the current employee*/
            if (!matchingRows.isEmpty()) {
                System.out.println("Matching records for user ID: " + loggedInUserId);
                for (String[] match : matchingRows) {
                    System.out.println(String.join(", ", match)); // Example: Print row as comma-separated string
                }
            } else {
                System.out.println("No records found for user ID: " + loggedInUserId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        
}