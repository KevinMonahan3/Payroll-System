import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import com.opencsv.*;



public class Payslip extends Account{
    private Tax tax;
    private  String payslipFile = "lib/payslips.csv";
    private String month = "25th";
    private double deductions;
    private String[] cols;
    String employeefile=getEmployeeFile();
   
    
    /** allows access to the current employee */ 
    public Payslip(String[] cols) {
        this.cols = cols;
        this.tax = new Tax(this.cols);
        this.deductions = tax.getDeductions();
    }

    public String getMonth() {
        return month;
    }
    
    public double getDeductions() {
        return deductions;
    }
    
    /**if employee is part time salary = rate * gethours(); */
    public void Writeparttime(String []cols,String hours){
        System.out.println("in pt method");
        try{
        
            String id=cols[0];
            String name=cols[10];
            double hoursXrate=Double.parseDouble(hours);
            hoursXrate=hoursXrate*Double.parseDouble(cols[7]);
            hoursXrate = Math.round(hoursXrate * 100.0) / 100.0;
            System.out.println(hoursXrate + "salary in part time");
            String grosspaystr = String.valueOf(hoursXrate);

            double netpay= Math.round((hoursXrate-deductions) * 100.0) / 100.0;
            String netpays= String.valueOf(netpay);
       
       
        

            try (CSVWriter writeparttime = new CSVWriter(new FileWriter(payslipFile, true))){
                System.out.println("in writer");
                String[] dat = {
                    id, /**id*/
                    name, /**name*/
                    "25th", /**date*/
                    grosspaystr, /**salary*/
                    String.valueOf(deductions), /**deductions*/
                    netpays /**netpay*/
                };
                
        
                /** Write the data row*/
                writeparttime.writeNext(dat);

                /**Ensure all data is flushed to the file//buffer*/
                writeparttime.flush();
    
                System.out.println("Payslip data successfully written to " + payslipFile);
            
            }catch(Exception e){
                e.printStackTrace();
            }
      
            try(CSVReader reader = new CSVReader(new FileReader(payslipFile))) {
                
                String [] employeeCols;
                String[] lastRow = null;
                String[] header=reader.readNext();
                System.out.println(Arrays.toString(header));
                while ((employeeCols = reader.readNext()) != null) {
                    lastRow = employeeCols;
                }
                System.out.println(Arrays.toString(lastRow));

            }catch(Exception e) {
                e.printStackTrace();
            }
        }catch(Exception e){

            e.printStackTrace();
        }
    }  
   
    
    public void writeFullTime(String[] cols) {
        
        boolean fileExists = new java.io.File(payslipFile).exists();

        try (CSVWriter writer = new CSVWriter(new FileWriter(payslipFile, true))) {
            /**If the file doesn't exist, write the header row*/
            if (!fileExists) {
                String[] header = { "EmployeeID", "EmployeeName", "Month", "GrossSalary", "Deductions", "NetPay" };
                writer.writeNext(header);
            }

            
            /**Prepare the data row*/
            String[] data = {
                cols[0], //id
                cols[10], //name
                month,
                String.valueOf(Math.round((Double.parseDouble(cols[4])/12) * 100.0) / 100.0),
                String.valueOf(deductions), //deductions
                String.valueOf(Math.round((Double.parseDouble(cols[4])/12 - deductions) * 100.0) / 100.0) //netpay
            };
                        
            writer.writeNext(data);

            /**Ensure all data is flushed to the file//buffer*/
            writer.flush();

            System.out.println("Payslip data successfully written to " + payslipFile);


            try(CSVReader reader = new CSVReader(new FileReader(payslipFile))) {
                String [] header=reader.readNext();

                String [] employeeCols;
                String[] lastRow = null;
                System.out.println(Arrays.toString(header));
                while ((employeeCols = reader.readNext()) != null) {
                    lastRow = employeeCols;
                }
                System.out.println(Arrays.toString(lastRow));
            }catch(Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
        }
    }
}
