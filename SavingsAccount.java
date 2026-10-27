package banking;

// Inheritance: SavingsAccount extends BankAccount.
// Interface implementation: SavingsAccount implements Loanable.
public class SavingsAccount extends BankAccount implements Loanable {
    private static final double MINIMUM_BALANCE = 100.00;
    private static final double LOAN_LIMIT = 5000.00;

    public SavingsAccount(String accountNumber, String accountHolder,
                         double openingBalance) {
        super(accountNumber, accountHolder, openingBalance);
    }

    // Overriding: savings accounts have their own deposit behaviour.
    @Override
    public boolean deposit(double amount) {
        if (amount <= 0) {
            TransactionLogger.log(getAccountNumber(), "DEPOSIT", amount,
                    getBalance(), "FAILED: amount must be greater than zero");
            System.out.println("Deposit must be greater than zero.");
            return false;
        }

        addToBalance(amount);
        TransactionLogger.log(getAccountNumber(), "DEPOSIT", amount,
                getBalance(), "SUCCESS");
        System.out.println("Deposit successful.");
        return true;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            TransactionLogger.log(getAccountNumber(), "WITHDRAWAL", amount,
                    getBalance(), "FAILED: amount must be greater than zero");
            System.out.println("Withdrawal must be greater than zero.");
            return false;
        }

        if (getBalance() - amount < MINIMUM_BALANCE) {
            TransactionLogger.log(getAccountNumber(), "WITHDRAWAL", amount,
                    getBalance(), "FAILED: minimum balance of M100.00 must remain");
            System.out.println("Withdrawal failed. You must keep at least M100.00.");
            return false;
        }

        subtractFromBalance(amount);
        TransactionLogger.log(getAccountNumber(), "WITHDRAWAL", amount,
                getBalance(), "SUCCESS");
        System.out.println("Withdrawal successful.");
        return true;
    }

    @Override
    public double calculateInterest() {
        double interest = getBalance() * 0.05;
        deposit(interest);
        TransactionLogger.log(getAccountNumber(), "INTEREST", interest,
                getBalance(), "SUCCESS: 5% annual interest added");
        System.out.printf("Interest added: M%.2f%n", interest);
        return interest;
    }

    @Override
    public boolean requestLoan(double amount) {
        if (amount <= 0 || amount > LOAN_LIMIT) {
            TransactionLogger.log(getAccountNumber(), "LOAN", amount,
                    getBalance(), "FAILED: loan must be positive and no more than M5000.00");
            System.out.println("Loan must be greater than zero and cannot exceed M5000.00.");
            return false;
        }

        addToBalance(amount);
        TransactionLogger.log(getAccountNumber(), "LOAN", amount,
                getBalance(), "SUCCESS: loan approved");
        System.out.println("Loan approved and added to your balance.");
        return true;
    }
}
