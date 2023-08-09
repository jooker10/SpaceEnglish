package anouar.oulhaj.p001.DB;

public class Phrasal {

    private int phrasal_id;
    private String getPhrasal_eng;
    private String phrasal_fr;
    private String phrasal_sp;
    private String phrasal_ar;
    private String phrasal_example;

    public Phrasal(int phrasal_id, String getPhrasal_eng, String phrasal_fr, String phrasal_sp, String phrasal_ar, String phrasal_example) {
        this.phrasal_id = phrasal_id;
        this.getPhrasal_eng = getPhrasal_eng;
        this.phrasal_fr = phrasal_fr;
        this.phrasal_sp = phrasal_sp;
        this.phrasal_ar = phrasal_ar;
        this.phrasal_example = phrasal_example;
    }

    public String getPhrasal_sp() {
        return phrasal_sp;
    }

    public void setPhrasal_sp(String phrasal_sp) {
        this.phrasal_sp = phrasal_sp;
    }

    public String getPhrasal_ar() {
        return phrasal_ar;
    }

    public void setPhrasal_ar(String phrasal_ar) {
        this.phrasal_ar = phrasal_ar;
    }

    public String getPhrasal_example() {
        return phrasal_example;
    }

    public void setPhrasal_example(String phrasal_example) {
        this.phrasal_example = phrasal_example;
    }

    public Phrasal() {
    }

    public Phrasal(int phrasal_id, String phrasal_fr, String getPhrasal_eng) {
        this.phrasal_id = phrasal_id;
        this.phrasal_fr = phrasal_fr;
        this.getPhrasal_eng = getPhrasal_eng;
    }

    public Phrasal(String phrasal_fr, String getPhrasal_eng) {
        this.phrasal_fr = phrasal_fr;
        this.getPhrasal_eng = getPhrasal_eng;
    }

    public int getPhrasal_id() {
        return phrasal_id;
    }

    public void setPhrasal_id(int phrasal_id) {
        this.phrasal_id = phrasal_id;
    }

    public String getPhrasal_fr() {
        return phrasal_fr;
    }

    public void setPhrasal_fr(String phrasal_fr) {
        this.phrasal_fr = phrasal_fr;
    }

    public String getGetPhrasal_eng() {
        return getPhrasal_eng;
    }

    public void setGetPhrasal_eng(String getPhrasal_eng) {
        this.getPhrasal_eng = getPhrasal_eng;
    }
}
