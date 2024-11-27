public class Customer {
    private final String cus_name;
    private final int age;
    private final String phoneNumber;
    private String cus_id;
    private Accounts account;

    public void setCus_id(String cus_id) {
        this.cus_id = cus_id;
    }

    public String getCus_id() {
        return cus_id;
    }

    public int getAge() {
        return age;
    }

    public void setAccount(Accounts account) {
        this.account = account;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCus_name() {
        return cus_name;
    }

    public Accounts getAccount() {
        return account;
    }

    public Customer(int age, String name, String phone){
        this.age = age;
        this.cus_name = name;
        this.phoneNumber = phone;
    }

    public String getMyDetails() {
        String details;
        details = "You Name is: " + this.getCus_name() + "\nCustomer Id is: " + this.getCus_id() + "\nAccount Status: " + this.getAccount().getStatus() +  "\nYour age: " + this.getAge()+ "\nYour Phone Number: " + this.getPhoneNumber() + "\nYour Account number: " + this.getAccount().getAccountNumber() + "\nYour Balance: " + this.getAccount().getBalance(this);
        return details;
    }
}