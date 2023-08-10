package anouar.oulhaj.p001.DB;

public class Adjective {
    private int adj_id;
    private String getAdj_eng;
    private String adj_fr;
    private String adj_sp;
    private String adj_ar;

    public int getAdj_id() {
        return adj_id;
    }

    public void setAdj_id(int adj_id) {
        this.adj_id = adj_id;
    }

    public String getGetAdj_eng() {
        return getAdj_eng;
    }

    public void setGetAdj_eng(String getAdj_eng) {
        this.getAdj_eng = getAdj_eng;
    }

    public String getAdj_fr() {
        return adj_fr;
    }

    public void setAdj_fr(String adj_fr) {
        this.adj_fr = adj_fr;
    }

    public String getAdj_sp() {
        return adj_sp;
    }

    public void setAdj_sp(String adj_sp) {
        this.adj_sp = adj_sp;
    }

    public String getAdj_ar() {
        return adj_ar;
    }

    public void setAdj_ar(String adj_ar) {
        this.adj_ar = adj_ar;
    }

    public String getAdj_example() {
        return adj_example;
    }

    public void setAdj_example(String adj_example) {
        this.adj_example = adj_example;
    }

    private String adj_example;

    public Adjective(int adj_id, String getAdj_eng, String adj_fr, String adj_sp, String adj_ar, String adj_example) {
        this.adj_id = adj_id;
        this.getAdj_eng = getAdj_eng;
        this.adj_fr = adj_fr;
        this.adj_sp = adj_sp;
        this.adj_ar = adj_ar;
        this.adj_example = adj_example;
    }
}
