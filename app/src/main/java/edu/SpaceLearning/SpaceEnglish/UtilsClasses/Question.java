package edu.SpaceLearning.SpaceEnglish.UtilsClasses;

public class Question {

    private String theMainElement;
    private String option1;
    private String option2;
    private String option3;
    private String rightAnswer;

    public Question(String theMainElement, String option1, String option2, String option3, String rightAnswer) {
        this.theMainElement = theMainElement;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.rightAnswer = rightAnswer;
    }

    public String getTheMainElement() {
        return theMainElement;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }
}