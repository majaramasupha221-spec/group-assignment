package banking;

// Inheritance: CurrentAccount extends BankAccount and implements Loanable.
public class CurrentAccount extends BankAccount implements Loanable {
    private static final double OVERDRAFT_LIMIT = 2000.00;
    private static final double LOAN_LIMIT = 10000.00;

    public CurrentAccount(String accountNumber, String accountHolder,
                          double openingBalance) {
        super(accountNumber, accountHolder, openingBalance);
    }

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

        if (getBalance() - amount < -OVERDRAFT_LIMIT) {
            TransactionLogger.log(getAccountNumber(), "WITHDRAWAL", amount,
                    getBalance(), "FAILED: overdraft limit of M2000.00 exceeded");
            System.out.println("Withdrawal failed. Your overdraft limit is M2000.00.");
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
        System.out.println("Current accounts do not earn interest.");
        TransactionLogger.log(getAccountNumber(), "INTEREST", 0,
                getBalance(), "SUCCESS: no interest for current accounts");
        return 0;
    }

    @Override
    public boolean requestLoan(double amount) {
        if (amount <= 0 || amount > LOAN_LIMIT) {
            TransactionLogger.log(getAccountNumber(), "LOAN", amount,
                    getBalance(), "FAILED: loan must be positive and no more than M10000.00");
            System.out.println("Loan must be greater than zero and cannot exceed M10000.00.");
            return false;
        }

        addToBalance(amount);
        TransactionLogger.log(getAccountNumber(), "LOAN", amount,
                getBalance(), "SUCCESS: loan approved");
        System.out.println("Loan approved and added to your balance.");
        return true;
    }
}
