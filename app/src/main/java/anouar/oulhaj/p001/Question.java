package anouar.oulhaj.p001;

public class Question {

    private String theQst;
    private String option0;
    private String option1;
    private String option2;
    private String rightAnswer;

    public Question() {
    }

    public Question(String theQst, String option1, String option2, String option3, String rightAnswer) {
        this.theQst = theQst;
        this.option0 = option1;
        this.option1 = option2;
        this.option2 = option3;
        this.rightAnswer = rightAnswer;
    }

    public String getTheQst() {
        return theQst;
    }

    public void setTheQst(String theQst) {
        this.theQst = theQst;
    }

    public String getOption0() {
        return option0;
    }

    public void setOption0(String option0) {
        this.option0 = option0;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }
}
