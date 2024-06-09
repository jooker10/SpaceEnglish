package edu.SpaceLearning.SpaceEnglish.UtilsClasses;

public class Question {

    private String theMainElement;
    private String option0;
    private String option1;
    private String option2;
    private String rightAnswer;

    public Question(String theMainElement, String option1, String option2, String option3, String rightAnswer) {
        this.theMainElement = theMainElement;
        this.option0 = option1;
        this.option1 = option2;
        this.option2 = option3;
        this.rightAnswer = rightAnswer;
    }

    public String getTheMainElement() {
        return theMainElement;
    }

    public String getOption0() {
        return option0;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }
}
