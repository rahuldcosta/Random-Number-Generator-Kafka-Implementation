package producer;

public class MainApplication {


    public static void main(String[] args) throws Exception {
        RandomNumberProducer producer = new RandomNumberProducer();
        producer.runProducer();
    }
}
