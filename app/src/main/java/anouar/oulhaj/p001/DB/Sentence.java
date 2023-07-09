package anouar.oulhaj.p001.DB;

public class Sentence {

    private int sentence_id;
    private String sentence_fr;
    private String sentence_eng;

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
