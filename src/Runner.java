import java.util.*;

public class Runner {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean looper = true;
        Map<String, Customer> customers = new HashMap<>(); //    <accountNumber and Customer objects
        Map<String, String> customerIds = new HashMap<>(); //    Customer Ids' and Passwords

        while (looper) {
            System.out.println("\n1-New Customer? Create an Account with us\n2-Existing Customer? Login\n3-Exit");
            switch (sc.nextInt()) {
                case 1: //creating an account
                    System.out.println("Please enter your name: ");
                    sc.nextLine();
                    String name = sc.nextLine();
                    System.out.println("Please enter your age: ");
                    int age = sc.nextInt();
                    System.out.println("Please enter your Phone Number: ");
                    String phoneNumber = sc.next();

                    Customer newCustomer = new Customer(age, name, phoneNumber);
                    int newAccountNumber = (int) (Math.random() * 1000000000);
                    do {
                        newAccountNumber += (int) (Math.random() * 1000000000);
                    } while (customers.containsKey(newAccountNumber + ""));

                    Accounts newAccount = new Accounts(newAccountNumber + "",age);
                    newCustomer.setAccount(newAccount);
                    customers.put(newAccountNumber + "", newCustomer);

                    int newCusId;
                    do {
                        newCusId = (int) (Math.random() * 1000000);
                    } while (customerIds.containsKey(newCusId + ""));
                    newCustomer.setCus_id(newCusId + "");

                    System.out.println("Do you want to set your Password? (y/n)");
                    String c = sc.next();
                    String cusPassword = "";
                    if (c.equals("y")) {
                        String ReCusPassword = "  ";
                        System.out.println("Create a new Password: ");
                        cusPassword = sc.next();
                        System.out.println("Please re-enter the Password: ");
                        ReCusPassword = sc.next();
                        while (!cusPassword.equals(ReCusPassword)) {
                            System.out.println("Password Mismatch,Create a new Password: ");
                            cusPassword = sc.next();
                            System.out.println("Please re-enter your Password: ");
                            ReCusPassword = sc.next();
                        }
                    }
                    newCustomer.getAccount().setPassword(c.equals("y") ? cusPassword : (newCustomer.getPhoneNumber()));
                    customerIds.put(newCusId + "", newCustomer.getAccount().getPassword());

                    System.out.println("Account created successfully!\nMake note of these Things...");
                    System.out.println("Your Customer ID is " + newCustomer.getCus_id());
                    System.out.println("Your Account Number is " + newAccount.getAccountNumber());
                    System.out.println("If you haven't created your own Password, Your Phone Number is the default Password");
                    break;
                case 2:
                    System.out.println("Enter your Customer ID:");
                    String cusId = sc.next();
                    while (!customerIds.containsKey(cusId)) {
                        System.out.println("Customer ID not found, Try Again");
                        cusId = sc.next();
                    }
                    System.out.println("Enter your Password:");
                    int cnt = 5;
                    while (!sc.next().equals(customerIds.get(cusId)) && cnt > 0) {
                        System.out.println("Incorrect Password, Please re-enter your Password");
                        cnt--;
                    }
                    if (cnt == 0) {
                        System.out.println("Maximum attempts have reached, Please Reset your password and try again\nEnter your Account number:");
                        String accountNumReset = sc.next();
                        while (!customers.containsKey(accountNumReset)) {
                            System.out.println("Account number not found, Try Again");
                            accountNumReset = sc.next();
                        }
                        Customer customerToResetPassword = customers.get(accountNumReset);
                        String resetForgetPassword = "";
                        String verifyResetForgetPassword = "";
                        System.out.println("Enter new Password:");
                        resetForgetPassword = sc.next();
                        System.out.println("Please re-enter your Password:");
                        verifyResetForgetPassword = sc.next();
                        while (!resetForgetPassword.equals(verifyResetForgetPassword)) {
                            System.out.println("Password Mismatch,Create new Password");
                            resetForgetPassword = sc.next();
                            System.out.println("Please re-enter new Password:");
                            verifyResetForgetPassword = sc.next();
                        }
                        customerToResetPassword.getAccount().reSetPassword(customerToResetPassword,resetForgetPassword);
                        customerIds.replace(cusId, customerToResetPassword.getAccount().getPassword());
                        System.out.println("Password changed successfully!");
                        break;
                    }

                    System.out.println("1-To deposit Amount\n2-To withdraw Amount\n3-To Check Balance\n4-To view Account Information's\n5-To Reset Your Password\n6-To view Transactions\n7-Log-out");
                    int choice = sc.nextInt();
                    String accountInfo = null;
                    Customer currentCustomer = null;
                    if (choice < 8) {
                        System.out.println("Enter Account Number: ");
                        accountInfo = sc.next();
                        while (!customers.containsKey(accountInfo)){
                            System.out.println("Account number not found, Try Again");
                            accountInfo = sc.next();
                        }
                        currentCustomer = customers.get(accountInfo);
                    }
                    switch (choice) {  //1-deposit  2-withdrawal  3-balance  4-accInfo 5-reset pw
                        case 1: //to deposit amount to an account
                            System.out.println("Is your Name " + currentCustomer.getCus_name() + "?(y/n)");
                            if (sc.next().equals("y")) {
                                System.out.println("Enter Amount to be deposited:");
                                double amountToDeposit = sc.nextDouble();
                                System.out.println("Amount added to your account successfully and your balance is Rs." + currentCustomer.getAccount().depositAmount(currentCustomer, amountToDeposit));
                            } else {
                                System.out.println("Account not found!!");
                                break;
                            }
                            break;

                        case 2:  // To withdraw amount from an account
                            System.out.println("Enter your Password");
                            String cusWithdrawPass = sc.next();
                            while(!cusWithdrawPass.equals(currentCustomer.getAccount().getPassword())) {
                                System.out.println("Incorrect Password, Please re-enter your Password");
                                cusWithdrawPass = sc.next();
                            }
                            System.out.println("Enter Amount to Withdraw: ");
                            double withdrawAmount = sc.nextDouble();
                            if(withdrawAmount==0){
                                System.out.println("Enter a valid amount to withdraw");
                                break;
                            }
                            if (withdrawAmount >= 20_000)
                                System.out.println("Can't Withdraw more than Rs.20000 at once");
                            else if (currentCustomer.getAccount().getBalance(currentCustomer) >= withdrawAmount)
                                System.out.println("Amount withdrawn Successfully,Take your Cash and your Balance Amount is Rs." + currentCustomer.getAccount().withdraw(currentCustomer, withdrawAmount));
                            else
                                System.out.println("Insufficient Balance");
                            break;

                        case 3:             //To view balance
                            System.out.println("Enter your Password");
                            String cusBalancePassword = sc.next();
                            while (!cusBalancePassword.equals(currentCustomer.getAccount().getPassword())) {
                                System.out.println("Password incorrect");
                                cusBalancePassword = sc.next();
                            }
                            System.out.println("Your Account Balance is Rs." + currentCustomer.getAccount().getBalance(currentCustomer));
                            break;
                        case 4:       //To view account details
                            System.out.println("Enter your Password");
                            String cusDetailsPassword = sc.next();
                            while (!cusDetailsPassword.equals(currentCustomer.getAccount().getPassword())) {
                                System.out.println("Password incorrect");
                                cusDetailsPassword = sc.next();
                            }
                            System.out.println(currentCustomer.getMyDetails());
                            break;
                        case 5:     //to reset password
                            System.out.println("Enter your Password");
                            String cusPassReSet = sc.next();
                            while (!cusPassReSet.equals(currentCustomer.getAccount().getPassword())) {
                                System.out.println("Incorrect Password, Please re-enter your Password");
                                cusPassReSet = sc.next();
                            }
                            System.out.println("Enter your Phone Number");
                            String resetCusPhoneNo = sc.next();
                            while(!resetCusPhoneNo.equals(currentCustomer.getPhoneNumber())) {
                                System.out.println("Incorrect Phone Number, Try again");
                                resetCusPhoneNo = sc.next();
                            }
                            System.out.println("Create a new Password: ");
                            String cusResetPassword = sc.next();
                            System.out.println("Please re-enter the Password: ");
                            String ReCusResetPassword = sc.next();
                            while (!cusResetPassword.equals(ReCusResetPassword)) {
                                System.out.println("Password Mismatch,Create a new Password: ");
                                cusPassword = sc.next();
                                System.out.println("Please re-enter your Password: ");
                                ReCusResetPassword = sc.next();
                            }
                            currentCustomer.getAccount().setPassword(cusResetPassword);
                            customerIds.replace(cusId, currentCustomer.getAccount().getPassword());
                            System.out.println("Password Reset successfully");
                            break;
                        case 6:
                            currentCustomer.getAccount().myTransaction();
                            break;
                        case 7:
                            System.out.println("Thank you!, Visit again :)");
                            break;
                        default:
                            System.out.println("Invalid Option");
                            break;
                        }
                        break;
                case 3:
                    System.out.println("Thank you!, Visit again:)");
                    looper = false;
                    break;
                default:
                    System.out.println("Invalid Choice");
                    break;
            }
        }
        sc.close();
    }
}