import java.time.*;
import java.util.*;

public class Accounts{

    private static class Transaction{
        private final String transactionType;
        private final double previousBalance;
        private final double currentBalance;
        private final double amount;
        private final LocalDateTime dateTime;

        public Transaction(Customer customer_, double amount, boolean b) {
            this.amount = amount;
            this.previousBalance = customer_.getAccount().getBalance(customer_);
            this.currentBalance = b? customer_.getAccount().getBalance(customer_) + amount : customer_.getAccount().getBalance(customer_) - amount;
            this.transactionType = b ? "Deposit" : "Withdraw";
            this.dateTime = LocalDateTime.now();
        }
        public String toString() {
            return "Dated->" + dateTime + "\tPrevious Balance->" + previousBalance + " \t" + transactionType + "ed amount->" + amount + "\tCurrent Balance->" + currentBalance;
        }
    }

    List<Transaction> transactions = new ArrayList<Transaction>();

    public void myTransaction(){
        for(Transaction transaction : transactions){
            System.out.println(transaction);
        }
    }

    private final String accountNumber;
    private double balance = 0.0;
    private String password;
    private final String status;

    public Accounts(String accountNumber, int age){
        this.status = age<18? "Minor Account" : "Major Account";
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance(Customer cus) {
        return Math.round(cus.getAccount().balance*100.0)/100.0;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }

    public double depositAmount(Customer depositCustomer, double amountToDeposit) {
        transactions.add(new Transaction(depositCustomer, amountToDeposit, true));
        depositCustomer.getAccount().balance += Math.round(amountToDeposit*100.0)/100.0;
        return depositCustomer.getAccount().getBalance(depositCustomer);
    }

    public double withdraw(Customer withdrawalCustomer, double withdrawAmount) {
        transactions.add(new Transaction(withdrawalCustomer, withdrawAmount, false));
        withdrawalCustomer.getAccount().balance -= withdrawAmount;
        return Math.round(withdrawalCustomer.getAccount().getBalance(withdrawalCustomer)*100.0)/100.0;
    }

    public void reSetPassword(Customer customerNewPassword, String newPassword) {
        customerNewPassword.getAccount().setPassword(newPassword);
    }
}