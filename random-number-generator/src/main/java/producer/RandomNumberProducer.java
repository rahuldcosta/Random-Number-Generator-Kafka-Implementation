package producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class RandomNumberProducer {
    private final static String TOPIC = "kristalai";
    private final static String BOOTSTRAP_SERVERS = "localhost:9092";
    private final static long maxRandomRange = 5;
    private final static int RESPONSE_INTERVAL_IN_MILISECONDS = 1000;
    public static final String MESSAGE_INPUT_SPLITTER = ",";
    public static final int MAX_AMOUNT_OF_NUMBERS = 5;

    private static Producer<Long, String> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "RandomNumberProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        props.put(ProducerConfig.LINGER_MS_CONFIG, RESPONSE_INTERVAL_IN_MILISECONDS);
        return new KafkaProducer<>(props);
    }

    public void runProducer() throws Exception {
        final Producer<Long, String> producer = createProducer();
        long time = System.currentTimeMillis();
        try {
            long index = time;
            while (1 != 0) {
                final ProducerRecord<Long, String> record = new ProducerRecord<>(TOPIC, index, getRandomIntegerBetweenRange(1, maxRandomRange));
                RecordMetadata metadata = producer.send(record).get();
                long elapsedTime = System.currentTimeMillis() - time;
                System.out.printf("sent record(key=%s value=%s) " + "meta(partition=%d, offset=%d) time=%d\n", record.key(), record.value(), metadata.partition(), metadata.offset(), elapsedTime);
                index++;
            }
        } finally {
            producer.flush();
            producer.close();
        }
    }


    private String getRandomIntegerBetweenRange(long min, long max) {

        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < MAX_AMOUNT_OF_NUMBERS; i++) {
            if (i == MAX_AMOUNT_OF_NUMBERS - 1)
                stringBuilder.append((long) (Math.random() * max - min) + 1);
            else
                stringBuilder.append((long) (Math.random() * max - min) + 1 + MESSAGE_INPUT_SPLITTER);
        }
        return stringBuilder.toString();
    }
}
