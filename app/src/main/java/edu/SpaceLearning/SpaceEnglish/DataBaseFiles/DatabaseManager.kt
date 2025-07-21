
package edu.SpaceLearning.SpaceEnglish.DataBaseFiles

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.TextView
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Category
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils

class DatabaseManager private constructor(context: Context) {
    private lateinit var readableDb: SQLiteDatabase
    private val dbHelper: SQLiteOpenHelper = AppDatabase(context)

    // Opens database connection for reading
    fun openReadConnection() {
        this.readableDb = dbHelper.readableDatabase
    }

    // Opens database connection for writing
    fun openWriteConnection() {
        this.readableDb = dbHelper.writableDatabase
    }

    // Closes database connection
    fun closeConnection() {
            readableDb.close()
    }

    // Retrieves a list of all elements for a given category type
    // Optionally includes example sentences for categories that support it
    fun fetchCategoryData(categoryType: String, withExample: Boolean): ArrayList<Category> {
        val elementList = ArrayList<Category>()

        // Query to fetch all rows from the specified category table
        val cursor = readableDb.rawQuery("SELECT * FROM " + Utils.categoryTableMap[categoryType], null)

        // Iterate through the cursor to extract category elements
        if (cursor.moveToFirst()) {
            do {
                // Extracting column values from the cursor
                val id = cursor.getInt(0)
                val englishName = cursor.getString(1)
                val frenchName = cursor.getString(2)
                val spanishName = cursor.getString(3)
                val arabicName = cursor.getString(4)

                // Optionally, retrieve example sentences if requested
                var exampleSentence: String? = null
                if (withExample) {
                    exampleSentence = cursor.getString(5)
                }

                // Creating Category object based on retrieved data
                val category = if (exampleSentence != null) {
                    Category(id, englishName, frenchName, spanishName, arabicName, exampleSentence)
                } else {
                    Category(id, englishName, frenchName, spanishName, arabicName)
                }

                // Adding the created Category object to the list
                elementList.add(category)
            } while (cursor.moveToNext())

            // Closing the cursor after iteration
            cursor.close()
        }

        return elementList
    }

    val updateCategorySizes: Unit
        // Retrieves and calculates the size of each category list from the database
        get() {
            openReadConnection() // Open database connection

            // Retrieve all category lists and store them
            val allVerbsList =
                ArrayList(
                    fetchCategoryData(
                        Constants.VERB_NAME,
                        true
                    )
                )
            val allSentencesList =
                ArrayList(
                    fetchCategoryData(
                        Constants.SENTENCE_NAME,
                        false
                    )
                )
            val allPhrasalsList =
                ArrayList(
                    fetchCategoryData(
                        Constants.PHRASAL_NAME,
                        true
                    )
                )
            val allNounsList =
                ArrayList(
                    fetchCategoryData(
                        Constants.NOUN_NAME,
                        true
                    )
                )
            val allAdjsList =
                ArrayList(
                    fetchCategoryData(
                        Constants.ADJ_NAME,
                        true
                    )
                )
            val allAdvsList =
                ArrayList(
                    fetchCategoryData(
                        Constants.ADV_NAME,
                        true
                    )
                )
            val allIdiomsList =
                ArrayList(
                    fetchCategoryData(
                        Constants.IDIOM_NAME,
                        false
                    )
                )

            closeConnection() // Close database connection

            // Initialize the total count for each category
            Utils.totalVerbsNumber = allVerbsList.size
            Utils.totalSentencesNumber =
                allSentencesList.size
            Utils.totalPhrasalsNumber =
                allPhrasalsList.size
            Utils.totalNounsNumber = allNounsList.size
            Utils.totalAdjsNumber = allAdjsList.size
            Utils.totalAdvsNumber = allAdvsList.size
            Utils.totalIdiomsNumber = allIdiomsList.size
        }

    // Retrieves a subset of elements for a specific category type based on allowed number
    // Updates the TextView to display category title and current/total count of elements
    fun getLimitedCategoryList(
        categoryType: String,
        tvHeadTitleCategory: TextView
    ): ArrayList<Category> {
        openReadConnection() // Open database connection

        var elements = ArrayList<Category>()

        // Retrieve elements based on category type
        when (categoryType) {
            Constants.VERB_NAME -> {
                elements = ArrayList(
                    fetchCategoryData(Constants.VERB_NAME, true).subList(
                        0,
                        Utils.allowedVerbsNumber
                    )
                )
                tvHeadTitleCategory.text =
                    "Table of " + categoryType + "(" + elements.size + "/" + Utils.totalVerbsNumber + ")"
            }

            Constants.SENTENCE_NAME -> {
                elements = ArrayList(
                    fetchCategoryData(Constants.SENTENCE_NAME, false).subList(
                        0,
                        Utils.allowedSentencesNumber
                    )
                )
                tvHeadTitleCategory.text =
                    "Table of " + categoryType + "(" + elements.size + "/" + Utils.totalSentencesNumber + ")"
                tvHeadTitleCategory.textSize = 22f
            }

            Constants.PHRASAL_NAME -> {
                elements = ArrayList(
                    fetchCategoryData(Constants.PHRASAL_NAME, true).subList(
                        0,
                        Utils.allowedPhrasalsNumber
                    )
                )
                tvHeadTitleCategory.text =
                    "Table of " + categoryType + "(" + elements.size + "/" + Utils.totalPhrasalsNumber + ")"
            }

            Constants.NOUN_NAME -> {
                elements = ArrayList(
                    fetchCategoryData(Constants.NOUN_NAME, true).subList(
                        0,
                        Utils.allowedNounsNumber
                    )
                )
                tvHeadTitleCategory.text =
                    "Table of " + categoryType + "(" + elements.size + "/" + Utils.totalNounsNumber + ")"
            }

            Constants.ADJ_NAME -> {
                elements = ArrayList(
                    fetchCategoryData(Constants.ADJ_NAME, true).subList(
                        0,
                        Utils.allowedAdjsNumber
                    )
                )
                tvHeadTitleCategory.text =
                    "Table of " + categoryType + "(" + elements.size + "/" + Utils.totalAdjsNumber + ")"
            }

            Constants.ADV_NAME -> {
                elements = ArrayList(
                    fetchCategoryData(Constants.ADV_NAME, true).subList(
                        0,
                        Utils.allowedAdvsNumber
                    )
                )
                tvHeadTitleCategory.text =
                    "Table of " + categoryType + "(" + elements.size + "/" + Utils.totalAdvsNumber + ")"
            }

            Constants.IDIOM_NAME -> {
                elements = ArrayList(
                    fetchCategoryData(Constants.IDIOM_NAME, false).subList(
                        0,
                        Utils.allowedIdiomsNumber
                    )
                )
                tvHeadTitleCategory.text =
                    "Table of " + categoryType + "(" + elements.size + "/" + Utils.totalIdiomsNumber + ")"
            }
        }

        closeConnection() // Close database connection

        return elements
    }

    companion object {
        private var instance: DatabaseManager? = null

        // Singleton instance retrieval method
        @JvmStatic
        fun getInstance(context: Context): DatabaseManager {
            if (instance == null) {
                instance = DatabaseManager(context.applicationContext)
            }
            return instance!!
        }
    }
}
