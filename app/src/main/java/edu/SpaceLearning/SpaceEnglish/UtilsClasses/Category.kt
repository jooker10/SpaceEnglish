package edu.SpaceLearning.SpaceEnglish.UtilsClasses

/**
 * Represents a Category object with information in multiple languages and optional examples.
 */
class Category {
    /**
     * Retrieves the unique identifier of the category.
     *
     * @return The category ID.
     */
    /**
     * Sets the unique identifier of the category.
     *
     * @param categoryID The category ID to set.
     */
    @JvmField
    var categoryID: Int // Unique identifier for the category
    /**
     * Retrieves the category name in English.
     *
     * @return The category name in English.
     */
    /**
     * Sets the category name in English.
     *
     * @param categoryEng The category name in English to set.
     */
    @JvmField
    var categoryEng: String // Category name in English
    /**
     * Retrieves the category name in French.
     *
     * @return The category name in French.
     */
    /**
     * Sets the category name in French.
     *
     * @param categoryFr The category name in French to set.
     */
    @JvmField
    var categoryFr: String // Category name in French
    /**
     * Retrieves the category name in Spanish.
     *
     * @return The category name in Spanish.
     */
    /**
     * Sets the category name in Spanish.
     *
     * @param categorySp The category name in Spanish to set.
     */
    @JvmField
    var categorySp: String // Category name in Spanish
    /**
     * Retrieves the category name in Arabic.
     *
     * @return The category name in Arabic.
     */
    /**
     * Sets the category name in Arabic.
     *
     * @param categoryAr The category name in Arabic to set.
     */
    @JvmField
    var categoryAr: String // Category name in Arabic
    /**
     * Retrieves examples related to the category.
     *
     * @return Examples related to the category.
     */
    /**
     * Sets examples related to the category.
     *
     * @param categoryExamples Examples related to the category to set.
     */
    @JvmField
    var categoryExamples: String? = null // Optional examples related to the category

    /**
     * Constructor for creating a Category object without examples.
     *
     * @param categoryID   The unique identifier for the category.
     * @param categoryEng  The category name in English.
     * @param categoryFr   The category name in French.
     * @param categorySp   The category name in Spanish.
     * @param categoryAr   The category name in Arabic.
     */
    constructor(
        categoryID: Int,
        categoryEng: String,
        categoryFr: String,
        categorySp: String,
        categoryAr: String
    ) {
        this.categoryID = categoryID
        this.categoryEng = categoryEng
        this.categoryFr = categoryFr
        this.categorySp = categorySp
        this.categoryAr = categoryAr
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
    constructor(
        categoryID: Int,
        categoryEng: String,
        categoryFr: String,
        categorySp: String,
        categoryAr: String,
        categoryExamples: String?
    ) {
        this.categoryID = categoryID
        this.categoryEng = categoryEng
        this.categoryFr = categoryFr
        this.categorySp = categorySp
        this.categoryAr = categoryAr
        this.categoryExamples = categoryExamples
    }
}
