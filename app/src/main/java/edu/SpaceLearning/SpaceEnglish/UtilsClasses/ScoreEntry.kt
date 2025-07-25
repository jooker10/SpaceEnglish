package edu.SpaceLearning.SpaceEnglish.UtilsClasses

/**
 * ScoreEntry class represents a data structure for holding title labels and score counters.
 */
class ScoreEntry(
    /**
     * Get the title label associated with the score.
     *
     * @return The title label.
     */
    val label: String, // Title label associated with the score
    /**
     * Get the score counter value.
     *
     * @return The score counter value.
     */
    var value: String // Score counter value
) {
    /**
     * Constructor to initialize ScoreEntry with title label and score counter.
     *
     * @param label   The title label associated with the score.
     * @param value The score counter value.
     */
    init {
        this.value = value
    }
}
