# CS4013-Project

This is a short explanation on where each file is located and how to run the programme. This project was coded soley using Java using CSV files as our database for data

# File Locations

All related CSV files and required libraries are located in the lib folder. This includes the CSV files for employees, payrole and jobtypes aswell as the openCSV and common Lang library.
All classes and java files are contained within the src folder Including Main, Account, Employee, HR, Admin, Payroll and Tax.

# Instructions

Below I will give a brief description on how to work the programme <br />

1. When first running the program you will be given an option on whether to access the command-line function or the GUI version. Our command-line version is the version with every requirement fulfilled and completed as there wasn't enough time to implement it as a GUI aswell
2. Once the command-line option has been chosen you will be given the option to login or quit
   - When choosing the login option there are 3 account types you can login as Admin, HR or Employee
   - For Admin the login id and password are both: admin
   - For HR the login id and password are both: hr
   - For Employee there are 4 accounts whose id's are 1 to 4, the password for the accounts are the same as there id e.g. id:1 password: 1
3. Admin Login:
   - When logging on as admin you will be giving 2 options, to either add an employee or quit
   - When adding an employee you will be given some data fields to fill out including job type since there are a lot of job types use these for examples: Library Assistant, Technical Officer, Senior Administrator, Computer Operator
   - Once you have added an employee it will be written into the employee.csv file
4. HR Login:
   - When logging is as HR it will search through each full-time employee and determine if they are eligable for a promotion, you will then be given a prompt on whether you want to promote them or not
   - Once an employee has been promoted a prompt will pop up for them the next time they login
5. Employee Login:
   - When logging in as any full-time employee if you have been promoted by HR you will be given a prompt on wether you want to accept the promotion or not, if accepted you payscale and salary will increase accordingly
   - You will then be given 4 options: view recent payslip, view details, view previous payslips and quit
   - View recent payslip will show you your most recent payslip with you current pay and taxes, if you are a part-time employee you will then be asked to submit hours worked and then the payslip will be generated
   - View details will show you all details about the employee currently logged in including name, salary, marriage status and other details
   - View previous payslips will scan through the payslip.csv file and retrieve all payslips for the logged in employee
