package anouar.oulhaj.p001.DB;

public class Verb {

    private int verb_id;
    private String verb_fr;
    private String verb_eng;

    public Verb(int verb_id, String verb_fr, String verb_eng) {
        this.verb_id = verb_id;
        this.verb_fr = verb_fr;
        this.verb_eng = verb_eng;
    }

    public Verb(String verb_fr, String verb_eng) {
        this.verb_fr = verb_fr;
        this.verb_eng = verb_eng;
    }

    public int getVerb_id() {
        return verb_id;
    }

    public void setVerb_id(int verb_id) {
        this.verb_id = verb_id;
    }

    public String getVerb_fr() {
        return verb_fr;
    }

    public void setVerb_fr(String verb_fr) {
        this.verb_fr = verb_fr;
    }

    public String getVerb_eng() {
        return verb_eng;
    }

    public void setVerb_eng(String verb_eng) {
        this.verb_eng = verb_eng;
    }
}
