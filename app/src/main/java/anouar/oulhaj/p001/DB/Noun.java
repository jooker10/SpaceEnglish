package anouar.oulhaj.p001.DB;

public class Noun {

    private int noun_id;
    private String getNoun_eng;
    private String noun_fr;
    private String noun_sp;
    private String noun_ar;
    private String noun_example;

    public int getNoun_id() {
        return noun_id;
    }

    public void setNoun_id(int noun_id) {
        this.noun_id = noun_id;
    }

    public String getGetNoun_eng() {
        return getNoun_eng;
    }

    public void setGetNoun_eng(String getNoun_eng) {
        this.getNoun_eng = getNoun_eng;
    }

    public String getNoun_fr() {
        return noun_fr;
    }

    public void setNoun_fr(String noun_fr) {
        this.noun_fr = noun_fr;
    }

    public String getNoun_sp() {
        return noun_sp;
    }

    public void setNoun_sp(String noun_sp) {
        this.noun_sp = noun_sp;
    }

    public String getNoun_ar() {
        return noun_ar;
    }

    public void setNoun_ar(String noun_ar) {
        this.noun_ar = noun_ar;
    }

    public String getNoun_example() {
        return noun_example;
    }

    public void setNoun_example(String noun_example) {
        this.noun_example = noun_example;
    }

    public Noun(int noun_id, String getNoun_eng, String noun_fr, String noun_sp, String noun_ar, String noun_example) {
        this.noun_id = noun_id;
        this.getNoun_eng = getNoun_eng;
        this.noun_fr = noun_fr;
        this.noun_sp = noun_sp;
        this.noun_ar = noun_ar;
        this.noun_example = noun_example;
    }
}
