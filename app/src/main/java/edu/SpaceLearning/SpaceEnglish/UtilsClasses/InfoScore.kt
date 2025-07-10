package edu.SpaceLearning.SpaceEnglish.UtilsClasses;

/**
 * InfoScore class represents a data structure for holding title labels and score counters.
 */
public class InfoScore {
    private final String titleLabel; // Title label associated with the score
    private final String scoreCounter; // Score counter value

    /**
     * Constructor to initialize InfoScore with title label and score counter.
     *
     * @param titleLabel   The title label associated with the score.
     * @param scoreCounter The score counter value.
     */
    public InfoScore(String titleLabel, String scoreCounter) {
        this.titleLabel = titleLabel;
        this.scoreCounter = scoreCounter;
    }

    /**
     * Get the title label associated with the score.
     *
     * @return The title label.
     */
    public String getTitleLabel() {
        return titleLabel;
    }

    /**
     * Get the score counter value.
     *
     * @return The score counter value.
     */
    public String getScoreCounter() {
        return scoreCounter;
    }
}
