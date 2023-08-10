package anouar.oulhaj.p001.DB;

public class Adverb {

    private int adv_id;
    private String adv_eng;
    private String adv_fr;
    private String adv_sp;
    private String adv_ar;
    private String adv_example;

    public int getAdv_id() {
        return adv_id;
    }

    public void setAdv_id(int adv_id) {
        this.adv_id = adv_id;
    }

    public String getAdv_eng() {
        return adv_eng;
    }

    public void setAdv_eng(String adv_eng) {
        this.adv_eng = adv_eng;
    }

    public String getAdv_fr() {
        return adv_fr;
    }

    public void setAdv_fr(String adv_fr) {
        this.adv_fr = adv_fr;
    }

    public String getAdv_sp() {
        return adv_sp;
    }

    public void setAdv_sp(String adv_sp) {
        this.adv_sp = adv_sp;
    }

    public String getAdv_ar() {
        return adv_ar;
    }

    public void setAdv_ar(String adv_ar) {
        this.adv_ar = adv_ar;
    }

    public String getAdv_example() {
        return adv_example;
    }

    public void setAdv_example(String adv_example) {
        this.adv_example = adv_example;
    }

    public Adverb(int adv_id, String adv_eng, String adv_fr, String adv_sp, String adv_ar, String adv_example) {
        this.adv_id = adv_id;
        this.adv_eng = adv_eng;
        this.adv_fr = adv_fr;
        this.adv_sp = adv_sp;
        this.adv_ar = adv_ar;
        this.adv_example = adv_example;
    }
}
