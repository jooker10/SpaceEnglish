package edu.SpaceLearning.SpaceEnglish.UtilsClasses;

public class InfoScore {
    private String titleLabel;
    private String scoreCounter;

    public InfoScore(String titleLabel, String scoreCounter) {
        this.titleLabel = titleLabel;
        this.scoreCounter = scoreCounter;
    }

    public String getTitleLabel() {
        return titleLabel;
    }

    public void setTitleLabel(String titleLabel) {
        this.titleLabel = titleLabel;
    }

    public String getScoreCounter() {
        return scoreCounter;
    }

    public void setScoreCounter(String scoreCounter) {
        this.scoreCounter = scoreCounter;
    }
}
