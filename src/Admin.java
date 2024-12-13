import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

public class Admin extends Account {

    private String employeeFile = getEmployeeFile();
    private Scanner scanner;
    private String jobtypeFile = "lib/jobtypes.csv";
    String promotion = "false";
    String fulltime; 
    double rate;
    String ratevalue;

    /** Method to add a new Employee to the CSV file via command line input*/
    public void addEmployee() {
        boolean more = true;

        while(more) {
            scanner = new Scanner(System.in);
        
            System.out.println("A)dd Employee Q)uit");
            String input = scanner.nextLine().toUpperCase();
        
            if (input.equalsIgnoreCase("A")) {
            
                /** reader reads jobtypefile csv */
                try (CSVReader jobTypeReader = new CSVReader(new FileReader(jobtypeFile))) {
                    boolean jobtypeFound = false;
                    String[] jobTypeRow = null;
            
                    /** Prompt for employee details */
                    System.out.print("Enter Employee ID: ");
                    String id = scanner.nextLine();

                    System.out.print("Enter Employee name: ");
                    String name = scanner.nextLine();
            
                    System.out.print("Enter Employee Password: ");
                    String password = scanner.nextLine();
            
                    System.out.print("Enter Employee Account Type: ");
                    String account = scanner.nextLine();
            
                    System.out.print("Enter Employee Job Type: ");
                    String jobtype = scanner.nextLine();
            
                    /** while loop runs untill csv reader is null*/
                    while ((jobTypeRow = jobTypeReader.readNext()) != null) {
                        if (jobtype.equalsIgnoreCase(jobTypeRow[0])) {
                            jobtypeFound = true;
            
                            System.out.print("Enter Employee Pay Scale : ");
                            String payscale = scanner.nextLine();
                            int scaleIndex = Integer.parseInt(payscale);
                            /** if statement checks to see if payscale is valid 
                             *  sets salary to value of payscale index
                             */

                            if (scaleIndex >= 1 && scaleIndex < jobTypeRow.length) {
                                String salary = jobTypeRow[scaleIndex];

                                System.out.print("Enter Employee Civil Status:  ");
                                System.out.print("1)Single 2)lone parent 3)Married one incomes 4)Married two incomes ");
                                String civilStatus = scanner.nextLine();
            
                                System.out.print("Is the Employee Full-Time (yes/no): ");
                                fulltime = scanner.nextLine();
                                
                                if(fulltime.equalsIgnoreCase("no")){
                                    rate=Double.parseDouble(salary);
                                    rate=(rate/12)/4/40;
                                    rate = Math.round(rate * 100.0) / 100.0;;
                                    ratevalue = String.valueOf(rate);
                                
                                }
                                /**Write employee data to CSV*/
                                writeEmployeeToFile(id, password, account, jobtype, salary, payscale, fulltime, ratevalue, promotion, civilStatus, name);
                                System.out.println("Employee added successfully!");
                            } else {
                                System.out.println("Invalid pay scale index");
                            }
                            break;
                        }
                    }

                    if (!jobtypeFound) {
                        System.out.println("Job type not found");
                    }

                } catch (IOException | CsvValidationException e) {
                    System.out.println("An error occurred: " + e.getMessage());
                }
            } else if(input.equalsIgnoreCase("Q")) {
                more = false;
            }
        }
    }
    
    private void writeEmployeeToFile(String id, String password, String account, String jobtype, String salary,
                                    String payscale, String fullTime,String ratevalue, String promotion, String civilStatus, String name) {
        try (CSVWriter writer = new CSVWriter(
                new FileWriter(employeeFile, true),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END)) {
            /**Writes the new employees details to the csv */
            String[] employeeData = {id, password, account, jobtype, salary, payscale, fullTime,ratevalue, promotion, civilStatus, name};
            writer.writeNext(employeeData);
        } catch (IOException e) {
            System.out.println("Error writing to the employee file: " + e.getMessage());
        }
    }
}

