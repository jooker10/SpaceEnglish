package edu.SpaceLearning.SpaceEnglish.UtilsClasses;

/**
 * Question represents a quiz question with the main element, options, and the correct answer.
 */
public class Question {

    private final String theMainElement; // The main element of the question
    private final String option1; // Option 1 for the question
    private final String option2; // Option 2 for the question
    private final String option3; // Option 3 for the question
    private final String rightAnswer; // The correct answer to the question

    /**
     * Constructor to initialize a Question object with the main element, options, and the correct answer.
     *
     * @param theMainElement The main element of the question.
     * @param option1        Option 1 for the question.
     * @param option2        Option 2 for the question.
     * @param option3        Option 3 for the question.
     * @param rightAnswer    The correct answer to the question.
     */
    public Question(String theMainElement, String option1, String option2, String option3, String rightAnswer) {
        this.theMainElement = theMainElement;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.rightAnswer = rightAnswer;
    }

    /**
     * Get the main element of the question.
     *
     * @return The main element.
     */
    public String getTheMainElement() {
        return theMainElement;
    }

    /**
     * Get option 1 for the question.
     *
     * @return Option 1.
     */
    public String getOption1() {
        return option1;
    }

    /**
     * Get option 2 for the question.
     *
     * @return Option 2.
     */
    public String getOption2() {
        return option2;
    }

    /**
     * Get option 3 for the question.
     *
     * @return Option 3.
     */
    public String getOption3() {
        return option3;
    }

    /**
     * Get the correct answer to the question.
     *
     * @return The correct answer.
     */
    public String getRightAnswer() {
        return rightAnswer;
    }
}
