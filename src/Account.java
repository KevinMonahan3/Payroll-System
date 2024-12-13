import java.io.*;
import java.util.*;
import com.opencsv.*;

public class Account {
    private Scanner in;
    private String employeeFile = "lib/Employee.csv";
    private String[] cols;
    private String hoursworked;

    public String gethours() {
        return hoursworked;
    }

    public String[] getCols() {
        return cols;
    }

    public String getEmployeeFile() {
        return employeeFile;
    }

    /** Main Login Method*/
    public void Login(){
        boolean more = true;
        in = new Scanner(System.in);

        while(more) {
            try(CSVReader reader = new CSVReader(new FileReader(employeeFile))) {
                
                System.out.println("L)ogin Q)uit");
                String command = in.nextLine().toUpperCase();

                if (command.equals("L")) {
                    System.out.println("Enter login id: ");
                    String checkUser = in.nextLine();
                    System.out.println();
                    /**Skips the header line of the employee file*/
                    reader.readNext();

                    
                    boolean userFound = false;
                    while((cols = reader.readNext()) != null) {
                        /**Checks to see if employee id exists*/
                        if (cols[0].equals(checkUser)) {
                            userFound = true;

                            System.out.println();

                            System.out.println("Enter Password: ");
                            String checkPassword = in.nextLine();
                            System.out.println();
                            /**Checks to see if password matches for that employee*/
                            if(checkPassword.equals(cols[1])) {
                                System.out.println("Login Successful");
                                
                                /**Determines what account type the person who logged in is*/
                                if(cols[2].equals("admin")) {
                                    Admin a = new Admin();
                                    a.addEmployee();
                                }

                                else if(cols[2].equals("hr")) {
                                    HR hr = new HR();
                                    hr.promotionEligability();
                                }
                                
                                else if(cols[2].equals("employee")) {
                                    boolean more1 = true;
                                    
                                    /**Only happens if employee has been promoted by HR*/
                                    if(cols[8].equals("true")) {
                                        System.out.println("You have been promoted!! Do you accept (Y/N): ");
                                        String promoteCommand = in.nextLine().toUpperCase();

                                        HR hr = new HR();
                                        hr.confirmedPromotion(cols, promoteCommand);
                                        
                                    }

                                    while(more1) {
                                        Employee e = new Employee(cols);

                                        /**Asks what the employee wants to do*/
                                        System.out.println("R)ecent Payslips, D)etails, P)revious Payslips, Q)uit");
                                        String employeeCommand = in.nextLine().toUpperCase();

                                        if(employeeCommand.equals("R") && cols[6].equals("yes")) {
                                            e.viewPayslip();
                                            
                                        }
                                        else if(employeeCommand.equals("R")&& cols[6].equals("no")) {
                                            /**Submit payclaim for part-time employees*/
                                            System.out.println("S)ubmit payclaim");
                                            String newCommand= in.nextLine().toUpperCase();
                                            if(newCommand.equals("S")){
                                                System.out.println("Submit Hours worked: ");
                                                hoursworked=in.nextLine();
                                                System.out.println(hoursworked + " hours worked");
                                                e.viewParttimepayslip(cols,hoursworked);
                                            }
                                        }else if(employeeCommand.equals("D")) {
                                            e.viewDetails();
                                        }else if(employeeCommand.equals("P")){
                                            e.viewPrevious(cols[0]);
                                        }else if(employeeCommand.equals("Q")) {
                                            more1 = false;
                                        }
                                    }
                                }
                                break;
                        
                            } else {
                                System.out.println("Incorrect Password");
                                break;
                            }
                        }
                    }

                    if (!userFound) {
                        System.out.println("User not found");
                    }
                    

                } 
                else if (command.equals("Q")) {
                    more = false;
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }

    }
}




