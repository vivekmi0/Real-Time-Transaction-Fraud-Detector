package fraudulent_transactions;

import java.util.HashMap;
import java.util.Map;

public class FraudDetector {
    private static final Map<String, Double> userAverageAmounts = new HashMap<>();

    public static boolean detectFraud(String transactionData) {
        String[] parts = transactionData.split(",");
        String userId = parts[0];
        double amount = Double.parseDouble(parts[1]);

        // Initialize user average amount if not exists
        userAverageAmounts.putIfAbsent(userId, amount);

        // Check if the transaction amount is significantly different from the user's average
        double userAverageAmount = userAverageAmounts.get(userId);
        double threshold = userAverageAmount * 1.5; // Adjust the threshold as needed

        // Update user average amount
        userAverageAmounts.put(userId, (userAverageAmount + amount) / 2);

        return amount > threshold;
    }
}