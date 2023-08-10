package anouar.oulhaj.p001.DB;

public class Idiom {

    private int idiom_id;
    private String idiom_eng;
    private String idiom_fr;
    private String idiom_sp;
    private String idiom_ar;

    public Idiom(int idiom_id, String idiom_eng, String idiom_fr, String idiom_sp, String idiom_ar) {
        this.idiom_id = idiom_id;
        this.idiom_eng = idiom_eng;
        this.idiom_fr = idiom_fr;
        this.idiom_sp = idiom_sp;
        this.idiom_ar = idiom_ar;
    }

    public int getIdiom_id() {
        return idiom_id;
    }

    public void setIdiom_id(int idiom_id) {
        this.idiom_id = idiom_id;
    }

    public String getIdiom_eng() {
        return idiom_eng;
    }

    public void setIdiom_eng(String idiom_eng) {
        this.idiom_eng = idiom_eng;
    }

    public String getIdiom_fr() {
        return idiom_fr;
    }

    public void setIdiom_fr(String idiom_fr) {
        this.idiom_fr = idiom_fr;
    }

    public String getIdiom_sp() {
        return idiom_sp;
    }

    public void setIdiom_sp(String idiom_sp) {
        this.idiom_sp = idiom_sp;
    }

    public String getIdiom_ar() {
        return idiom_ar;
    }

    public void setIdiom_ar(String idiom_ar) {
        this.idiom_ar = idiom_ar;
    }
}
