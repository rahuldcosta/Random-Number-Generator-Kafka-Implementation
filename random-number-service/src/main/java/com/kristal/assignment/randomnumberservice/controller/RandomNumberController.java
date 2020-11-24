package com.kristal.assignment.randomnumberservice.controller;

import com.kristal.assignment.randomnumberservice.model.CumulativeScore;
import com.kristal.assignment.randomnumberservice.service.RandomNumberConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/number")
public class RandomNumberController {

    private final RandomNumberConsumerService consumerService;

    @Autowired
    RandomNumberController(RandomNumberConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @GetMapping(value = "/showResults")
    public CumulativeScore getMessageFromKafkaTopic() {
        return consumerService.getCumulativeScore();
    }
}
