package anouar.oulhaj.p001.DB;

public class Phrasal {

    private int phrasal_id;
    private String phrasal_fr;
    private String getPhrasal_eng;

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
