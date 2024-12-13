public class Tax extends Account{
    //private Payslip payslip;
    private double tax;
    private double usc;
    private double deductions;
    private double prsi;
    private String[] cols;
    private double salary;
    private String civilStatus;

    public Tax(String[] cols) {
        if (cols == null) {
            throw new IllegalArgumentException("cols cannot be null");
        }
        this.cols = cols;
        this.salary = Math.round(Double.parseDouble(cols[4]) * 100.0) / 100.0;
        this.civilStatus = cols[8];
    }
    

    public void calculateUSC() {
        this.usc = 0.0;
        
        /**Apply 0.5% rate for income up to €12,012*/
        if (salary <= 12012) {
            this.usc += salary * 0.005;
                
        } else {
            this.usc += 12012 * 0.005;
        }

        /**Apply 2% rate for income from €12,012.01 to €25,760*/
        if (salary <= 25760) {
            this.usc += (salary - 12012) * 0.02;
                
        } else {
            this.usc += (25760 - 12012) * 0.02;
        }

        /**Apply 4% rate for income from €25,760.01 to €70,044*/
        if (salary <= 70044) {
            this.usc += (salary - 25760) * 0.04;
                
        } else {
            this.usc += (70044 - 25760) * 0.04;
        }

        /**Apply 8% rate for income from €70,044.01 and above*/
        if (salary > 70044) {
            this.usc += (salary - 70044) * 0.08;
        }

        /**Apply additional 11% for self-employed income over €100,000*/
        if (salary > 100000) {
            this.usc += (salary - 100000) * 0.11;
        }
            
    }
    
    
    public void calculateIncomeTax() {
        double taxThreshold = 0;
        double lowRate = 0.20;
        double highRate = 0.40;
        

        /**Determine tax threshold based on civil status using if-else*/
        if (civilStatus.equalsIgnoreCase("1")) {
            taxThreshold = 42000;
        } else if (civilStatus.equalsIgnoreCase("2")) {
            taxThreshold = 46000;
        } else if (civilStatus.equalsIgnoreCase("3")) {
            taxThreshold = 51000;
        } else if (civilStatus.equalsIgnoreCase("4")) {
            taxThreshold = 84000;
        } else {
            //System.out.println("Unknown civil status");
            return;
        }

        /**Calculate tax*/
        if (salary <= taxThreshold) {
            this.tax = salary * lowRate;
        } else {
            double lowerTax = taxThreshold * lowRate;
            double upperTax = (salary - taxThreshold) * highRate;
            this.tax = lowerTax + upperTax;
        }
    }

    public void calculatePrsi(){
        if(salary/52>=352){
          this.prsi=salary*(0.004);
        }
    }

    public double getDeductions(){
        calculateIncomeTax();
        calculatePrsi();
        calculateUSC();
        deductions = usc + prsi + tax;
        deductions = Math.round(deductions * 100.0) / 100.0;
        return deductions;
    }

}