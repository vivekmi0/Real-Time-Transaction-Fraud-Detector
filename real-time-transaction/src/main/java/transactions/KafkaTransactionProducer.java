package transactions;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Random;

public class KafkaTransactionProducer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            Random random = new Random();

            while (true) {
                // Simulate transaction data
                String transactionData = generateTransactionData();

                // Send data to Kafka topic "transactions"
                producer.send(new ProducerRecord<>("transactions", transactionData));
                Thread.sleep(1000); // Simulate a new transaction every second
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String generateTransactionData() {
        // Implement logic to generate random transaction data
        // Format: "user_id,amount,merchant"
        return String.format("%s,%.2f,%s", randomUserId(), randomAmount(), randomMerchant());
    }

    private static String randomUserId() {
        return "user" + (new Random().nextInt(10) + 1);
    }

    private static double randomAmount() {
        return Math.round((new Random().nextDouble() * 1000) * 100.0) / 100.0;
    }

    private static String randomMerchant() {
        String[] merchants = {"MerchantA", "MerchantB", "MerchantC"};
        return merchants[new Random().nextInt(merchants.length)];
    }
}




