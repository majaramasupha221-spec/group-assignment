package banking;

// Abstraction: common account details and abstract banking methods.
public abstract class BankAccount {
    // Encapsulation: fields are private.
    private String accountNumber;
    private String accountHolder;
    private double balance;

    public BankAccount(String accountNumber, String accountHolder,
                       double openingBalance) {
        if (openingBalance < 0) {
            throw new IllegalArgumentException("Opening balance cannot be negative.");
        }

        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = openingBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    // Controlled balance changes are made through these methods.
    protected void addToBalance(double amount) {
        balance += amount;
    }

    protected void subtractFromBalance(double amount) {
        balance -= amount;
    }

    public abstract boolean deposit(double amount);

    public abstract boolean withdraw(double amount);

    public abstract double calculateInterest();

    public void displayDetails() {
        System.out.println("Account number: " + accountNumber);
        System.out.println("Account holder: " + accountHolder);
        System.out.printf("Balance: M%.2f%n", balance);
        System.out.println("Account type: " + getClass().getSimpleName());
    }
}
