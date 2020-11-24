package com.kristal.assignment.randomnumberservice.service;

import com.kristal.assignment.randomnumberservice.model.CumulativeScore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

@Service
public class RandomNumberConsumerService {
    public static final String MESSAGE_INPUT_SPLITTER = ",";
    private final Logger logger = LoggerFactory.getLogger(KafkaProperties.Producer.class);
    private List<String> messageList = new LinkedList<>();
    @Value("${timeinseconds.interval.first}")
    private long firstInterval;
    @Value("${timeinseconds.interval.second}")
    private long secondInterval;
    @Value("${timeinseconds.interval.third}")
    private long thirdInterval;

    @KafkaListener(topics = "kristalai", groupId = "1")
    public void consume(String message) {
        if (messageList.size() > thirdInterval)
            messageList.remove(0);
        logger.info(String.format("#Consumed message# from Producer -> %s", message));
        messageList.add(message);
    }

    public CumulativeScore getCumulativeScore() {
        return generateCumulativeScore();
    }

    private CumulativeScore generateCumulativeScore() {
        ListIterator<String> messageIterator = messageList.listIterator(messageList.size());
        int indexCounter = 0;
        CumulativeScore score = new CumulativeScore();
        long totalCumulativeValue = 0;
        while (messageIterator.hasPrevious()) {
            indexCounter++;
            totalCumulativeValue += extractTotalFromMessage(messageIterator.previous());
            if (indexCounter == firstInterval) {
                score.setScoreForLast5Minutes(totalCumulativeValue);
            }
            if (indexCounter == secondInterval) {
                score.setScoreForLast10Minutes(totalCumulativeValue);
            }
            if (indexCounter == thirdInterval) {
                score.setScoreForLast30Minutes(totalCumulativeValue);
            }
        }
        return score;
    }

    private long extractTotalFromMessage(String message) {
        String[] values = message.split(MESSAGE_INPUT_SPLITTER);
        long cumulativeTotal = 0;
        for (String value : values
        ) {
            cumulativeTotal += Long.parseLong(value);
        }
        return cumulativeTotal;
    }
}
