package anouar.oulhaj.p001;

public class Question {

    private String thQqst;
    private String option1;
    private String option2;
    private String option3;
    private int index_rightAnswer;

    public Question(String thQqst, String option1, String option2, String option3, int index_rightAnswer) {
        this.thQqst = thQqst;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.index_rightAnswer = index_rightAnswer;
    }

    public Question() {
    }

    public String getThQqst() {
        return thQqst;
    }

    public void setThQqst(String thQqst) {
        this.thQqst = thQqst;
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

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public int getIndex_rightAnswer() {
        return index_rightAnswer;
    }

    public void setIndex_rightAnswer(int index_rightAnswer) {
        this.index_rightAnswer = index_rightAnswer;
    }
}
