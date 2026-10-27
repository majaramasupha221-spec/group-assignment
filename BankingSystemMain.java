import banking.BankAccount;
import banking.SavingsAccount;
import banking.CurrentAccount;
import banking.Loanable;
import banking.TransactionLogger;

import java.util.Scanner;

public class BankingSystemMain {
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        // Polymorphism: BankAccount references hold subclass objects.
        BankAccount[] accounts = new BankAccount[2];

        try {
            accounts[0] = new SavingsAccount("S1001", "Thabo", 1000.00);
            accounts[1] = new CurrentAccount("C1001", "Lerato", 1500.00);
        } catch (IllegalArgumentException e) {
            System.out.println("Could not create accounts: " + e.getMessage());
            return;
        }

        int choice = 0;

        while (choice != 8) {
            showMenu();
            choice = readInt("Choose an option: ");

            try {
                switch (choice) {
                    case 1:
                        displayAllAccounts(accounts);
                        break;
                    case 2:
                        depositMoney(accounts);
                        break;
                    case 3:
                        withdrawMoney(accounts);
                        break;
                    case 4:
                        transferMoney(accounts);
                        break;
                    case 5:
                        calculateInterest(accounts);
                        break;
                    case 6:
                        requestLoan(accounts);
                        break;
                    case 7:
                        displayAccountDetails(accounts);
                        break;
                    case 8:
                        System.out.println("Thank you for using the banking system.");
                        break;
                    default:
                        System.out.println("Invalid menu choice. Choose 1 to 8.");
                        TransactionLogger.log("N/A", "MENU", 0, 0,
                                "FAILED: invalid menu choice " + choice);
                }
            } catch (Exception e) {
                System.out.println("Something went wrong: " + e.getMessage());
            }

            System.out.println();
        }

        // No input.close() here, as requested for beginner assignment code.
    }

    private static void showMenu() {
        System.out.println("===== CONSOLE BANKING SYSTEM =====");
        System.out.println("1. Display all accounts");
        System.out.println("2. Deposit money");
        System.out.println("3. Withdraw money");
        System.out.println("4. Transfer money");
        System.out.println("5. Calculate interest");
        System.out.println("6. Request bank loan");
        System.out.println("7. Display account details");
        System.out.println("8. Exit");
    }

    private static int readInt(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
                TransactionLogger.log("N/A", "INPUT", 0, 0,
                        "FAILED: invalid numeric menu input");
            }
        }
    }

    private static double readAmount(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Double.parseDouble(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number, for example 250 or 250.50.");
                TransactionLogger.log("N/A", "INPUT", 0, 0,
                        "FAILED: invalid amount input");
            }
        }
    }

    private static int chooseAccount() {
        System.out.println("1. Savings account (S1001 - Thabo)");
        System.out.println("2. Current account (C1001 - Lerato)");
        int choice = readInt("Choose account: ");

        if (choice == 1 || choice == 2) {
            return choice - 1;
        }

        System.out.println("Invalid account choice.");
        TransactionLogger.log("N/A", "ACCOUNT SELECTION", 0, 0,
                "FAILED: invalid account choice");
        return -1;
    }

    private static void displayAllAccounts(BankAccount[] accounts) {
        for (BankAccount account : accounts) {
            account.displayDetails();
            System.out.println("------------------------------");
        }
    }

    private static void depositMoney(BankAccount[] accounts) {
        int index = chooseAccount();
        if (index == -1) {
            return;
        }

        double amount = readAmount("Enter deposit amount: M");
        accounts[index].deposit(amount);
    }

    private static void withdrawMoney(BankAccount[] accounts) {
        int index = chooseAccount();
        if (index == -1) {
            return;
        }

        double amount = readAmount("Enter withdrawal amount: M");
        accounts[index].withdraw(amount);
    }

    private static void transferMoney(BankAccount[] accounts) {
        System.out.println("Select source account:");
        int source = chooseAccount();
        if (source == -1) {
            return;
        }

        System.out.println("Select destination account:");
        int destination = chooseAccount();
        if (destination == -1) {
            return;
        }

        if (source == destination) {
            System.out.println("Source and destination must be different accounts.");
            TransactionLogger.log(accounts[source].getAccountNumber(), "TRANSFER",
                    0, accounts[source].getBalance(),
                    "FAILED: source and destination are the same account");
            return;
        }

        double amount = readAmount("Enter transfer amount: M");
        if (amount <= 0) {
            System.out.println("Transfer amount must be greater than zero.");
            TransactionLogger.log(accounts[source].getAccountNumber(), "TRANSFER",
                    amount, accounts[source].getBalance(),
                    "FAILED: transfer amount must be greater than zero");
            return;
        }

        // Withdraw first. If it fails, do not deposit into the other account.
        if (accounts[source].withdraw(amount)) {
            if (accounts[destination].deposit(amount)) {
                TransactionLogger.log(accounts[source].getAccountNumber(), "TRANSFER OUT",
                        amount, accounts[source].getBalance(),
                        "SUCCESS: sent to " + accounts[destination].getAccountNumber());
                TransactionLogger.log(accounts[destination].getAccountNumber(), "TRANSFER IN",
                        amount, accounts[destination].getBalance(),
                        "SUCCESS: received from " + accounts[source].getAccountNumber());
                System.out.println("Transfer successful.");
            }
        } else {
            TransactionLogger.log(accounts[source].getAccountNumber(), "TRANSFER",
                    amount, accounts[source].getBalance(),
                    "FAILED: source account has insufficient available funds");
        }
    }

    private static void calculateInterest(BankAccount[] accounts) {
        int index = chooseAccount();
        if (index == -1) {
            return;
        }

        accounts[index].calculateInterest();
    }

    private static void requestLoan(BankAccount[] accounts) {
        int index = chooseAccount();
        if (index == -1) {
            return;
        }

        double amount = readAmount("Enter loan amount: M");

        // Interface reference: account object is used as a Loanable.
        Loanable loanAccount = (Loanable) accounts[index];
        loanAccount.requestLoan(amount);
    }

    private static void displayAccountDetails(BankAccount[] accounts) {
        int index = chooseAccount();
        if (index == -1) {
            return;
        }

        accounts[index].displayDetails();
    }
}
