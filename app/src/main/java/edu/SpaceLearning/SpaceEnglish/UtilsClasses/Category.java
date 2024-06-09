package edu.SpaceLearning.SpaceEnglish.UtilsClasses;

public class Category {

    private int categoryID;
    private String categoryEng;
    private String categoryFr;
    private String categorySp;
    private String categoryAr;
    private String categoryExamples;

    public Category(int categoryID, String categoryEng, String categoryFr, String categorySp, String categoryAr) {
        this.categoryID = categoryID;
        this.categoryEng = categoryEng;
        this.categoryFr = categoryFr;
        this.categorySp = categorySp;
        this.categoryAr = categoryAr;
    }

    public Category(int categoryID, String categoryEng, String categoryFr, String categorySp, String categoryAr, String categoryExamples) {
        this.categoryID = categoryID;
        this.categoryEng = categoryEng;
        this.categoryFr = categoryFr;
        this.categorySp = categorySp;
        this.categoryAr = categoryAr;
        this.categoryExamples = categoryExamples;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryEng() {
        return categoryEng;
    }

    public void setCategoryEng(String categoryEng) {
        this.categoryEng = categoryEng;
    }

    public String getCategoryFr() {
        return categoryFr;
    }

    public void setCategoryFr(String categoryFr) {
        this.categoryFr = categoryFr;
    }

    public String getCategorySp() {
        return categorySp;
    }

    public void setCategorySp(String categorySp) {
        this.categorySp = categorySp;
    }

    public String getCategoryAr() {
        return categoryAr;
    }

    public void setCategoryAr(String categoryAr) {
        this.categoryAr = categoryAr;
    }

    public String getCategoryExamples() {
        return categoryExamples;
    }

    public void setCategoryExamples(String categoryExamples) {
        this.categoryExamples = categoryExamples;
    }
}
