package edu.SpaceLearning.SpaceEnglish.UtilsClasses;

/**
 * Represents a Category object with information in multiple languages and optional examples.
 */
public class Category {

    private int categoryID;          // Unique identifier for the category
    private String categoryEng;      // Category name in English
    private String categoryFr;       // Category name in French
    private String categorySp;       // Category name in Spanish
    private String categoryAr;       // Category name in Arabic
    private String categoryExamples; // Optional examples related to the category

    /**
     * Constructor for creating a Category object without examples.
     *
     * @param categoryID   The unique identifier for the category.
     * @param categoryEng  The category name in English.
     * @param categoryFr   The category name in French.
     * @param categorySp   The category name in Spanish.
     * @param categoryAr   The category name in Arabic.
     */
    public Category(int categoryID, String categoryEng, String categoryFr, String categorySp, String categoryAr) {
        this.categoryID = categoryID;
        this.categoryEng = categoryEng;
        this.categoryFr = categoryFr;
        this.categorySp = categorySp;
        this.categoryAr = categoryAr;
    }

    /**
     * Constructor for creating a Category object with examples.
     *
     * @param categoryID       The unique identifier for the category.
     * @param categoryEng      The category name in English.
     * @param categoryFr       The category name in French.
     * @param categorySp       The category name in Spanish.
     * @param categoryAr       The category name in Arabic.
     * @param categoryExamples Examples related to the category.
     */
    public Category(int categoryID, String categoryEng, String categoryFr, String categorySp, String categoryAr, String categoryExamples) {
        this.categoryID = categoryID;
        this.categoryEng = categoryEng;
        this.categoryFr = categoryFr;
        this.categorySp = categorySp;
        this.categoryAr = categoryAr;
        this.categoryExamples = categoryExamples;
    }

    /**
     * Retrieves the unique identifier of the category.
     *
     * @return The category ID.
     */
    public int getCategoryID() {
        return categoryID;
    }

    /**
     * Sets the unique identifier of the category.
     *
     * @param categoryID The category ID to set.
     */
    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    /**
     * Retrieves the category name in English.
     *
     * @return The category name in English.
     */
    public String getCategoryEng() {
        return categoryEng;
    }

    /**
     * Sets the category name in English.
     *
     * @param categoryEng The category name in English to set.
     */
    public void setCategoryEng(String categoryEng) {
        this.categoryEng = categoryEng;
    }

    /**
     * Retrieves the category name in French.
     *
     * @return The category name in French.
     */
    public String getCategoryFr() {
        return categoryFr;
    }

    /**
     * Sets the category name in French.
     *
     * @param categoryFr The category name in French to set.
     */
    public void setCategoryFr(String categoryFr) {
        this.categoryFr = categoryFr;
    }

    /**
     * Retrieves the category name in Spanish.
     *
     * @return The category name in Spanish.
     */
    public String getCategorySp() {
        return categorySp;
    }

    /**
     * Sets the category name in Spanish.
     *
     * @param categorySp The category name in Spanish to set.
     */
    public void setCategorySp(String categorySp) {
        this.categorySp = categorySp;
    }

    /**
     * Retrieves the category name in Arabic.
     *
     * @return The category name in Arabic.
     */
    public String getCategoryAr() {
        return categoryAr;
    }

    /**
     * Sets the category name in Arabic.
     *
     * @param categoryAr The category name in Arabic to set.
     */
    public void setCategoryAr(String categoryAr) {
        this.categoryAr = categoryAr;
    }

    /**
     * Retrieves examples related to the category.
     *
     * @return Examples related to the category.
     */
    public String getCategoryExamples() {
        return categoryExamples;
    }

    /**
     * Sets examples related to the category.
     *
     * @param categoryExamples Examples related to the category to set.
     */
    public void setCategoryExamples(String categoryExamples) {
        this.categoryExamples = categoryExamples;
    }
}
