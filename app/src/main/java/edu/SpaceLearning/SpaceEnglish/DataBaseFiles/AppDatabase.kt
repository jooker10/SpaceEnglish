/**
 * AppDatabase.java
 * This class extends SQLiteAssetHelper to manage the SQLite database for the English learning app.
 * The database is used in read-only mode by default. Uncomment the columns names below if you want to enable write operations.
 * Updated on: [23-Jun-2024]
 * Author: [Your Name]
 * Version: 1.0
 */
package edu.SpaceLearning.SpaceEnglish.DataBaseFiles

import android.content.Context
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

class AppDatabase(context: Context?) :
    SQLiteAssetHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        // Database name and version
        const val DATABASE_NAME: String = "english_learning.db"
           const val DATABASE_VERSION: Int = 1
         val dataBaseTablesNames
            = arrayOf("table_verbs","table_sentences_phrases","table_phrasals","table_nouns","table_adjectives","table_adverbs","table_idioms")

    }
}
