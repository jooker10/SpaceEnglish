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
     * @param idCategory The category ID to set.
     */
    @JvmField
    var idCategory: Int // Unique identifier for the category
    /**
     * Retrieves the category name in English.
     *
     * @return The category name in English.
     */
    /**
     * Sets the category name in English.
     *
     * @param engCategory The category name in English to set.
     */
    @JvmField
    var engCategory: String // Category name in English
    /**
     * Retrieves the category name in French.
     *
     * @return The category name in French.
     */
    /**
     * Sets the category name in French.
     *
     * @param FrCategory The category name in French to set.
     */
    @JvmField
    var FrCategory: String // Category name in French
    /**
     * Retrieves the category name in Spanish.
     *
     * @return The category name in Spanish.
     */
    /**
     * Sets the category name in Spanish.
     *
     * @param spCategory The category name in Spanish to set.
     */
    @JvmField
    var spCategory: String // Category name in Spanish
    /**
     * Retrieves the category name in Arabic.
     *
     * @return The category name in Arabic.
     */
    /**
     * Sets the category name in Arabic.
     *
     * @param arCategory The category name in Arabic to set.
     */
    @JvmField
    var arCategory: String // Category name in Arabic
    /**
     * Retrieves examples related to the category.
     *
     * @return Examples related to the category.
     */
    /**
     * Sets examples related to the category.
     *
     * @param exampleCategory Examples related to the category to set.
     */
    @JvmField
    var exampleCategory: String? = null // Optional examples related to the category

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
        this.idCategory = categoryID
        this.engCategory = categoryEng
        this.FrCategory = categoryFr
        this.spCategory = categorySp
        this.arCategory = categoryAr
    }

    /**
     * Constructor for creating a Category object with examples.
     *
     * @param categoryID       The unique identifier for the category.
     * @param engCategory      The category name in English.
     * @param frCategory       The category name in French.
     * @param spCategory       The category name in Spanish.
     * @param arCategory       The category name in Arabic.
     * @param exampleCategory Examples related to the category.
     */
    constructor(
        idCategory: Int,
        engCategory: String,
        frCategory: String,
        spCategory: String,
        arCategory: String,
        exampleCategory: String?
    ) {
        this.idCategory = idCategory
        this.engCategory = engCategory
        this.FrCategory = frCategory
        this.spCategory = spCategory
        this.arCategory = arCategory
        this.exampleCategory = exampleCategory
    }
}
