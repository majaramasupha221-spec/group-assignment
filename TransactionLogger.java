package banking;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// final class: this class is not intended to be extended.
public final class TransactionLogger {
    private static final String FILE_NAME = "transactions.txt";

    private TransactionLogger() {
        // Prevents creating TransactionLogger objects.
    }

    public static void log(String accountNumber, String type,
                           double amount, double balance, String message) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter format =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            writer.write(now.format(format)
                    + " | Account: " + accountNumber
                    + " | Type: " + type
                    + " | Amount: M" + String.format("%.2f", amount)
                    + " | Balance: M" + String.format("%.2f", balance)
                    + " | Result: " + message
                    + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Could not write to transactions.txt.");
        }
    }
}
