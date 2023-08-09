package anouar.oulhaj.p001.DB;

public class Sentence {

    private int sentence_id;
    private String sentence_eng;
    private String sentence_fr;
    private String sentence_sp;
    private String sentence_ar;
    private String sentence_example;

    public Sentence(int sentence_id, String sentence_eng, String sentence_fr, String sentence_sp, String sentence_ar) {
        this.sentence_id = sentence_id;
        this.sentence_eng = sentence_eng;
        this.sentence_fr = sentence_fr;
        this.sentence_sp = sentence_sp;
        this.sentence_ar = sentence_ar;

    }

    public String getSentence_example() {
        return sentence_example;
    }

    public void setSentence_example(String sentence_example) {
        this.sentence_example = sentence_example;
    }

    public String getSentence_sp() {
        return sentence_sp;
    }

    public void setSentence_sp(String sentence_sp) {
        this.sentence_sp = sentence_sp;
    }

    public String getSentence_ar() {
        return sentence_ar;
    }

    public void setSentence_ar(String sentence_ar) {
        this.sentence_ar = sentence_ar;
    }

    public Sentence(int sentence_id, String sentence_fr, String sentence_eng) {
        this.sentence_id = sentence_id;
        this.sentence_fr = sentence_fr;
        this.sentence_eng = sentence_eng;
    }

    public Sentence(String sentence_fr, String sentence_eng) {
        this.sentence_fr = sentence_fr;
        this.sentence_eng = sentence_eng;
    }

    public Sentence() {
    }

    public int getSentence_id() {
        return sentence_id;
    }

    public void setSentence_id(int sentence_id) {
        this.sentence_id = sentence_id;
    }

    public String getSentence_fr() {
        return sentence_fr;
    }

    public void setSentence_fr(String sentence_fr) {
        this.sentence_fr = sentence_fr;
    }

    public String getSentence_eng() {
        return sentence_eng;
    }

    public void setSentence_eng(String sentence_eng) {
        this.sentence_eng = sentence_eng;
    }
}
