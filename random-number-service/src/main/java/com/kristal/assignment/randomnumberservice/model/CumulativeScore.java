package com.kristal.assignment.randomnumberservice.model;

public class CumulativeScore {
    private Long scoreForLast5Minutes;
    private Long scoreForLast10Minutes;
    private Long scoreForLast30Minutes;

    public CumulativeScore() {
        scoreForLast5Minutes = 0L;
        scoreForLast10Minutes = 0L;
        scoreForLast30Minutes = 0L;
    }

    public Long getScoreForLast5Minutes() {
        return scoreForLast5Minutes;
    }

    public Long getScoreForLast10Minutes() {
        return scoreForLast10Minutes;
    }

    public Long getScoreForLast30Minutes() {
        return scoreForLast30Minutes;
    }

    public void setScoreForLast5Minutes(Long scoreForLast5Minutes) {
        this.scoreForLast5Minutes = scoreForLast5Minutes;
    }

    public void setScoreForLast10Minutes(Long scoreForLast10Minutes) {
        this.scoreForLast10Minutes = scoreForLast10Minutes;
    }

    public void setScoreForLast30Minutes(Long scoreForLast30Minutes) {
        this.scoreForLast30Minutes = scoreForLast30Minutes;
    }


}
