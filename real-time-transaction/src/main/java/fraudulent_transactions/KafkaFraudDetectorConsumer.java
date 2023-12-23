package fraudulent_transactions;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaFraudDetectorConsumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "fraud-detector-group");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        try (Consumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singletonList("transactions"));

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

                // Process each transaction
                records.forEach(record -> {
                    String transactionData = record.value();

                    // Apply fraud detection
                    boolean isFraudulent = FraudDetector.detectFraud(transactionData);

                    if (isFraudulent) {
                        System.out.println("Potential Fraud Detected: " + transactionData);
                        // Add logic to take action on fraudulent activity
                    } else {
                        System.out.println("Transaction Accepted: " + transactionData);
                    }
                });
            }
        }
    }
}






