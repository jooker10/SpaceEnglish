/**
 * DbManager.java
 * This class manages database access for retrieving category elements for the Space English learning app.
 * It provides methods to open and close database connections, retrieve category lists, and calculate category sizes.
 * Created on: [Date]
 * Author: [Your Name]
 * Version: 1.0
 */
package edu.SpaceLearning.SpaceEnglish.DataBaseFiles

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.TextView
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Category
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils

class DbManager private constructor(context: Context) {
    private lateinit var sqLiteDB: SQLiteDatabase
    private val openHelper: SQLiteOpenHelper = MyDatabase(context)

    // Opens database connection for reading
    fun openToRead() {
        this.sqLiteDB = openHelper.readableDatabase
    }

    // Opens database connection for writing
    fun openToWrite() {
        this.sqLiteDB = openHelper.writableDatabase
    }

    // Closes database connection
    fun close() {
            sqLiteDB.close()
    }

    // Retrieves a list of all elements for a given category type
    // Optionally includes example sentences for categories that support it
    fun getRequiredCategoryDataOf(categoryType: String, withExample: Boolean): ArrayList<Category> {
        val elementList = ArrayList<Category>()

        // Query to fetch all rows from the specified category table
        val cursor = sqLiteDB.rawQuery("SELECT * FROM " + Utils.tableHashNames[categoryType], null)

        // Iterate through the cursor to extract category elements
        if (cursor.moveToFirst()) {
            do {
                // Extracting column values from the cursor
                val id = cursor.getInt(0)
                val eng = cursor.getString(1)
                val fr = cursor.getString(2)
                val sp = cursor.getString(3)
                val ar = cursor.getString(4)

                // Optionally, retrieve example sentences if requested
                var example: String? = null
                if (withExample) {
                    example = cursor.getString(5)
                }

                // Creating Category object based on retrieved data
                val category = if (example != null) {
                    Category(
                        id,
                        eng,
                        fr,
                        sp,
                        ar,
                        example
                    )
                } else {
                    Category(
                        id,
                        eng,
                        fr,
                        sp,
                        ar
                    )
                }

                // Adding the created Category object to the list
                elementList.add(category)
            } while (cursor.moveToNext())

            // Closing the cursor after iteration
            cursor.close()
        }

        return elementList
    }

    val dBListCategorySize: Unit
        // Retrieves and calculates the size of each category list from the database
        get() {
            openToRead() // Open database connection

            // Retrieve all category lists and store them
            val allVerbsList =
                ArrayList(
                    getRequiredCategoryDataOf(
                        Constants.VERB_NAME,
                        true
                    )
                )
            val allSentencesList =
                ArrayList(
                    getRequiredCategoryDataOf(
                        Constants.SENTENCE_NAME,
                        false
                    )
                )
            val allPhrasalsList =
                ArrayList(
                    getRequiredCategoryDataOf(
                        Constants.PHRASAL_NAME,
                        true
                    )
                )
            val allNounsList =
                ArrayList(
                    getRequiredCategoryDataOf(
                        Constants.NOUN_NAME,
                        true
                    )
                )
            val allAdjsList =
                ArrayList(
                    getRequiredCategoryDataOf(
                        Constants.ADJ_NAME,
                        true
                    )
                )
            val allAdvsList =
                ArrayList(
                    getRequiredCategoryDataOf(
                        Constants.ADV_NAME,
                        true
                    )
                )
            val allIdiomsList =
                ArrayList(
                    getRequiredCategoryDataOf(
                        Constants.IDIOM_NAME,
                        false
                    )
                )

            close() // Close database connection

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
    fun getRequiredCategoryList(
        categoryType: String,
        tvHeadTitleCategory: TextView
    ): ArrayList<Category> {
        openToRead() // Open database connection

        var elements = ArrayList<Category>()

        // Retrieve elements based on category type
        when (categoryType) {
            Constants.VERB_NAME -> {
                elements = ArrayList(
                    getRequiredCategoryDataOf(Constants.VERB_NAME, true).subList(
                        0,
                        Utils.allowedVerbsNumber
                    )
                )
                tvHeadTitleCategory.text =
                    "Table of " + categoryType + "(" + elements.size + "/" + Utils.totalVerbsNumber + ")"
            }

            Constants.SENTENCE_NAME -> {
                elements = ArrayList(
                    getRequiredCategoryDataOf(Constants.SENTENCE_NAME, false).subList(
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
                    getRequiredCategoryDataOf(Constants.PHRASAL_NAME, true).subList(
                        0,
                        Utils.allowedPhrasalsNumber
                    )
                )
                tvHeadTitleCategory.text =
                    "Table of " + categoryType + "(" + elements.size + "/" + Utils.totalPhrasalsNumber + ")"
            }

            Constants.NOUN_NAME -> {
                elements = ArrayList(
                    getRequiredCategoryDataOf(Constants.NOUN_NAME, true).subList(
                        0,
                        Utils.allowedNounsNumber
                    )
                )
                tvHeadTitleCategory.text =
                    "Table of " + categoryType + "(" + elements.size + "/" + Utils.totalNounsNumber + ")"
            }

            Constants.ADJ_NAME -> {
                elements = ArrayList(
                    getRequiredCategoryDataOf(Constants.ADJ_NAME, true).subList(
                        0,
                        Utils.allowedAdjsNumber
                    )
                )
                tvHeadTitleCategory.text =
                    "Table of " + categoryType + "(" + elements.size + "/" + Utils.totalAdjsNumber + ")"
            }

            Constants.ADV_NAME -> {
                elements = ArrayList(
                    getRequiredCategoryDataOf(Constants.ADV_NAME, true).subList(
                        0,
                        Utils.allowedAdvsNumber
                    )
                )
                tvHeadTitleCategory.text =
                    "Table of " + categoryType + "(" + elements.size + "/" + Utils.totalAdvsNumber + ")"
            }

            Constants.IDIOM_NAME -> {
                elements = ArrayList(
                    getRequiredCategoryDataOf(Constants.IDIOM_NAME, false).subList(
                        0,
                        Utils.allowedIdiomsNumber
                    )
                )
                tvHeadTitleCategory.text =
                    "Table of " + categoryType + "(" + elements.size + "/" + Utils.totalIdiomsNumber + ")"
            }
        }

        close() // Close database connection

        return elements
    }

    companion object {
        private var instance: DbManager? = null

        // Singleton instance retrieval method
        @JvmStatic
        fun getInstance(context: Context): DbManager {
            if (instance == null) {
                instance = DbManager(context.applicationContext)
            }
            return instance!!
        }
    }
}
