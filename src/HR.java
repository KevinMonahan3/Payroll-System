import java.io.*;
import java.util.*;
import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

public class HR extends Account {
    /** Setting up data fields */
    private String jobTypesFile = "lib/jobtypes.csv";
    String employeeFile = "lib/Employee.csv";
    private String[] cols;
    private int currentPayscale;
    private int maxPayscale = 0;
    private String promotedSalary;

    public HR() {
    }

    /**Method to find wether an employee is eligable for a promotion*/
    public void promotionEligability() throws CsvException {
        boolean eligibleFound = false;
        /**Keeping scanner open as we ran into an infinite recursion loop when it was closed*/
        Scanner hrScanner = new Scanner(System.in);

        try (CSVReader reader = new CSVReader(new FileReader(employeeFile))) {
            reader.readNext(); // Skip header row

            /**Loop determines what the employees payscale is*/
            while ((this.cols = reader.readNext()) != null) {
                if (cols.length > 5 && cols[5] != null && !cols[5].isEmpty()) {
                    try {
                        /**Set payscale from csv*/
                        this.currentPayscale = Integer.valueOf(cols[5]);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid payscale value for employee ID: " + cols[0] + ". Skipping this row.");
                        /**Skip this row if payscale is invalid*/
                        continue;
                    }
                } else {
                    System.err.println("Payscale is missing for employee ID: " + cols[0] + ". Skipping this row.");
                    /**Skip if payscale is missing*/
                    continue;
                }

                String fullTimeStatus = cols[6];
                boolean isFullTime = fullTimeStatus.equalsIgnoreCase("Yes");
                String jobType = cols[3];

                /**Reads each job type and determines it's max payscale*/
                try (CSVReader jobReader = new CSVReader(new FileReader(jobTypesFile))) {
                    String[] jobRow;
                    while ((jobRow = jobReader.readNext()) != null) {
                        if (jobRow[0].equalsIgnoreCase(jobType)) {
                            for (int i = 1; i < jobRow.length; i++) {
                                if (!jobRow[i].isEmpty()) {
                                    /**Update max payscale for this job type*/
                                    this.maxPayscale = i;
                                }
                            }
                            /**Stop searching once the relevant job type is found*/
                            break;
                        }
                    }
                }

                /**Check if employee is eligible for promotion*/
                if (isFullTime && maxPayscale > 0) {
                    if (currentPayscale < maxPayscale) {
                        eligibleFound = true;
                        System.out.println("Employee eligible for promotion: " + cols[0]);

                        /**Ask for HR confirmation for promotion*/
                        System.out.println("Do you want to promote this employee? (Y/N): ");
                        if (hrScanner.hasNextLine()) {
                            String hrConfirmation = hrScanner.nextLine().toUpperCase();

                            /**Sets employee's promotion status to true*/
                            if (hrConfirmation.equals("Y")) {
                                int colToModify = 8;
                                if (cols.length > colToModify) {
                                    /**Only modify this employee's promotion status*/
                                    setPromotionTrue(employeeFile, colToModify, cols[0]);
                                    System.out.println("Employee promotion updated successfully.");
                                }
                            } else if (hrConfirmation.equals("N")) {
                                System.out.println("HR canceled the promotion.");
                            }
                        } else {
                            System.out.println("No input found for promotion decision.");
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!eligibleFound) {
            System.out.println("No employees are eligible for promotion.");
        } 
    }

    /**Modify the CSV directly, only for the selected employee*/
    public void setPromotionTrue(String employeeFile, int colToModify, String employeeId) throws IOException, CsvException {
        List<String[]> allRows = new ArrayList<>();
        boolean employeeFound = false;

        /**Makes sure the file is empty or malformed*/
        try (CSVReader reader = new CSVReader(new FileReader(employeeFile))) {
            /**Read header row*/
            String[] header = reader.readNext();
            if (header != null) {
                /**Add header to the list*/
                allRows.add(header);
            } else {
                System.out.println("Error: The CSV file is empty or malformed.");
                return;
            }

            String[] row;
            while ((row = reader.readNext()) != null) {
                /**Only update the promotion status for the employee that is found*/
                if (/**row.length > colToModify &&*/ row[0].equals(employeeId)) {
                    if ("false".equalsIgnoreCase(row[colToModify])) {
                        /**Update promotion status*/
                        row[colToModify] = "true";
                        /**Flag that we found the employee*/
                        employeeFound = true;
                        System.out.println("Promotion status updated for employee: " + employeeId);
                    }
                }
                /**Add the row to the list (modified or not)*/
                allRows.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        /**Write back to the CSV file only if the employee was found and updated*/
        if (employeeFound) {
            try (CSVWriter writer = new CSVWriter(new FileWriter(employeeFile))) {
                /**Write the updated data back to the file*/
                writer.writeAll(allRows);
                System.out.println("CSV file updated successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Employee ID " + employeeId + " was not found.");
        }
    }

    /**Method to update the employee's payscale if they accept the promotion*/
    public void confirmedPromotion(String[] cols, String decision) throws FileNotFoundException, IOException, CsvValidationException {
        List<String[]> allRows = new ArrayList<>();
        boolean employeeFound = false;

        try (CSVReader reader = new CSVReader(new FileReader(employeeFile))) {
            String[] header = reader.readNext();
            if (header != null) {
                allRows.add(header);
            } else {
                System.out.println("Error: The CSV file is empty or malformed.");
                return;
            }

            String[] row;
            int payscaleValue = Integer.valueOf(cols[5]);
            int modifySalary = 4;
            int modifyPayscale = 5;
            int promotionRow = 8;
            String targetEmployee = cols[0];
            int employeeId = 0;

            while ((row = reader.readNext()) != null) {
                /**Only update the promotion status for the employee that is found*/
                if (row.length > modifyPayscale) {
                    int promotedPayscale = payscaleValue + 1;
                    /**Checks if it is the target employee, if their promotion status is true and if they are not already at the top of their payscale*/
                    if (row[employeeId].equals(targetEmployee) && "true".equalsIgnoreCase(row[promotionRow]) && modifyPayscale != maxPayscale) {
                        if(decision.equals("Y")) {

                            try (CSVReader jobReader = new CSVReader(new FileReader(jobTypesFile))) {
                                String[] jobRow;
                                while ((jobRow = jobReader.readNext()) != null) {
                                    if (jobRow[0].equalsIgnoreCase(cols[3])) {
                                        this.promotedSalary = jobRow[promotedPayscale];
                                        System.out.println("New Salary: " + promotedSalary);
                                        break;
                                    }
                                }
                            }

                            row[modifySalary] = promotedSalary;
                            row[modifyPayscale] = String.valueOf(promotedPayscale); /**Update payscale*/
                            row[promotionRow] = "false"; /**Reset their promotion status*/
                            employeeFound = true; /**Flag that we found the employee*/
                            //System.out.println("Promotion status updated for employee: ");
                        } else if(decision.equals("N")) {
                            row[promotionRow] = "false";
                            employeeFound = true; /**Flag that we found the employee*/
                            //System.out.println("Promotion status updated for employee: ");
                        }
                    }
                }
                /**Add the row to the list (modified or not)*/
                allRows.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        /**Write back to the CSV file only if the employee was found and updated*/
        if (employeeFound) {
            try (CSVWriter writer = new CSVWriter(new FileWriter(employeeFile))) {
                writer.writeAll(allRows); /**Write the updated data back to the file*/
                //System.out.println("CSV file updated successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Could not promote");
        }
    }
}
