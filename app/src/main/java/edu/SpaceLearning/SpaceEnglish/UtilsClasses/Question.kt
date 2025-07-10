package edu.SpaceLearning.SpaceEnglish.UtilsClasses

/**
 * Question represents a quiz question with the main element, options, and the correct answer.
 */
class Question(
    /**
     * Get the main element of the question.
     *
     * @return The main element.
     */
    @JvmField val theMainElement: String, // The main element of the question
    /**
     * Get option 1 for the question.
     *
     * @return Option 1.
     */
    @JvmField var option1: String, // Option 1 for the question
    option2: String, option3: String, rightAnswer: String
) {
    /**
     * Get option 2 for the question.
     *
     * @return Option 2.
     */
    @JvmField
    val option2: String // Option 2 for the question

    /**
     * Get option 3 for the question.
     *
     * @return Option 3.
     */
    @JvmField
    val option3: String // Option 3 for the question

    /**
     * Get the correct answer to the question.
     *
     * @return The correct answer.
     */
    @JvmField
    val rightAnswer: String // The correct answer to the question

    /**
     * Constructor to initialize a Question object with the main element, options, and the correct answer.
     *
     * @param theMainElement The main element of the question.
     * @param option1        Option 1 for the question.
     * @param option2        Option 2 for the question.
     * @param option3        Option 3 for the question.
     * @param rightAnswer    The correct answer to the question.
     */
    init {
        this.option1 = option1
        this.option2 = option2
        this.option3 = option3
        this.rightAnswer = rightAnswer
    }
}
