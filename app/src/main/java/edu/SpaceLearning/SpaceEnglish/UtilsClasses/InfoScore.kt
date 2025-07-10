package edu.SpaceLearning.SpaceEnglish.UtilsClasses

/**
 * InfoScore class represents a data structure for holding title labels and score counters.
 */
class InfoScore(
    /**
     * Get the title label associated with the score.
     *
     * @return The title label.
     */
    @JvmField val titleLabel: String, // Title label associated with the score
    /**
     * Get the score counter value.
     *
     * @return The score counter value.
     */
    @JvmField var scoreCounter: String // Score counter value
) {
    /**
     * Constructor to initialize InfoScore with title label and score counter.
     *
     * @param titleLabel   The title label associated with the score.
     * @param scoreCounter The score counter value.
     */
    init {
        this.scoreCounter = scoreCounter
    }
}
