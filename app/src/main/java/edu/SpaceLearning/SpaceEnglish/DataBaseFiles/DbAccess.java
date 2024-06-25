/**
 * DbAccess.java
 * This class manages database access for retrieving category elements for the Space English learning app.
 * It provides methods to open and close database connections, retrieve category lists, and calculate category sizes.
 * Created on: [Date]
 * Author: [Your Name]
 * Version: 1.0
 */
package edu.SpaceLearning.SpaceEnglish.DataBaseFiles;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

import java.util.ArrayList;

import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Category;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils;

public class DbAccess {

    private SQLiteDatabase sqLiteDB;
    private final SQLiteOpenHelper openHelper;
    private static DbAccess instance;

    // Private constructor to enforce singleton pattern
    private DbAccess(Context context) {
        this.openHelper = new MyDatabase(context);
    }

    // Singleton instance retrieval method
    public static DbAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DbAccess(context.getApplicationContext());
        }
        return instance;
    }

    // Opens database connection for reading
    public void open_to_read() {
        this.sqLiteDB = this.openHelper.getReadableDatabase();
    }

    // Opens database connection for writing
    public void open_to_write() {
        this.sqLiteDB = this.openHelper.getWritableDatabase();
    }

    // Closes database connection
    public void close() {
        if (sqLiteDB != null) {
            sqLiteDB.close();
        }
    }

    // Retrieves a list of all elements for a given category type
    // Optionally includes example sentences for categories that support it
    public ArrayList<Category> getAllElementsCategoryList(String categoryType, boolean isWithExample) {
        ArrayList<Category> elementList = new ArrayList<>();

        // Query to fetch all rows from the specified category table
        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM " + Utils.tableHashNames.get(categoryType), null);

        // Iterate through the cursor to extract category elements
        if (cursor.moveToFirst()) {
            do {
                // Extracting column values from the cursor
                int element_id = cursor.getInt(0);
                String element_eng = cursor.getString(1);
                String element_fr = cursor.getString(2);
                String element_sp = cursor.getString(3);
                String element_ar = cursor.getString(4);

                // Optionally, retrieve example sentences if requested
                String element_examples = null;
                if (isWithExample) {
                    element_examples = cursor.getString(5);
                }

                // Creating Category object based on retrieved data
                Category category;
                if (element_examples != null) {
                    category = new Category(element_id, element_eng, element_fr, element_sp, element_ar, element_examples);
                } else {
                    category = new Category(element_id, element_eng, element_fr, element_sp, element_ar);
                }

                // Adding the created Category object to the list
                elementList.add(category);
            } while (cursor.moveToNext());

            // Closing the cursor after iteration
            cursor.close();
        }

        return elementList;
    }

    // Retrieves and calculates the size of each category list from the database
    public void getDBListCategorySize() {
        open_to_read(); // Open database connection

        // Retrieve all category lists and store them
        ArrayList<Category> allVerbsList = new ArrayList<>(getAllElementsCategoryList(Constants.VERB_NAME, true));
        ArrayList<Category> allSentencesList = new ArrayList<>(getAllElementsCategoryList(Constants.SENTENCE_NAME, false));
        ArrayList<Category> allPhrasalsList = new ArrayList<>(getAllElementsCategoryList(Constants.PHRASAL_NAME, true));
        ArrayList<Category> allNounsList = new ArrayList<>(getAllElementsCategoryList(Constants.NOUN_NAME, true));
        ArrayList<Category> allAdjsList = new ArrayList<>(getAllElementsCategoryList(Constants.ADJ_NAME, true));
        ArrayList<Category> allAdvsList = new ArrayList<>(getAllElementsCategoryList(Constants.ADV_NAME, true));
        ArrayList<Category> allIdiomsList = new ArrayList<>(getAllElementsCategoryList(Constants.IDIOM_NAME, false));

        close(); // Close database connection

        // Initialize the total count for each category
        Utils.totalVerbsNumber = allVerbsList.size();
        Utils.totalSentencesNumber = allSentencesList.size();
        Utils.totalPhrasalsNumber = allPhrasalsList.size();
        Utils.totalNounsNumber = allNounsList.size();
        Utils.totalAdjsNumber = allAdjsList.size();
        Utils.totalAdvsNumber = allAdvsList.size();
        Utils.totalIdiomsNumber = allIdiomsList.size();
    }

    // Retrieves a subset of elements for a specific category type based on allowed number
    // Updates the TextView to display category title and current/total count of elements
    public ArrayList<Category> getRequiredElementsList(String categoryType, TextView tvHeadTitleCategory) {
        open_to_read(); // Open database connection

        ArrayList<Category> elements = new ArrayList<>();

        // Retrieve elements based on category type
        switch (categoryType) {
            case Constants.VERB_NAME:
                elements = new ArrayList<>(getAllElementsCategoryList(Constants.VERB_NAME, true).subList(0, Utils.allowedVerbsNumber));
                tvHeadTitleCategory.setText("Table of " + categoryType + "(" + elements.size() + "/" + Utils.totalVerbsNumber + ")");
                break;
            case Constants.SENTENCE_NAME:
                elements = new ArrayList<>(getAllElementsCategoryList(Constants.SENTENCE_NAME, false).subList(0, Utils.allowedSentencesNumber));
                tvHeadTitleCategory.setText("Table of " + categoryType + "(" + elements.size() + "/" + Utils.totalSentencesNumber + ")");
                tvHeadTitleCategory.setTextSize(22f);
                break;
            case Constants.PHRASAL_NAME:
                elements = new ArrayList<>(getAllElementsCategoryList(Constants.PHRASAL_NAME, true).subList(0, Utils.allowedPhrasalsNumber));
                tvHeadTitleCategory.setText("Table of " + categoryType + "(" + elements.size() + "/" + Utils.totalPhrasalsNumber + ")");
                break;
            case Constants.NOUN_NAME:
                elements = new ArrayList<>(getAllElementsCategoryList(Constants.NOUN_NAME, true).subList(0, Utils.allowedNounsNumber));
                tvHeadTitleCategory.setText("Table of " + categoryType + "(" + elements.size() + "/" + Utils.totalNounsNumber + ")");
                break;
            case Constants.ADJ_NAME:
                elements = new ArrayList<>(getAllElementsCategoryList(Constants.ADJ_NAME, true).subList(0, Utils.allowedAdjsNumber));
                tvHeadTitleCategory.setText("Table of " + categoryType + "(" + elements.size() + "/" + Utils.totalAdjsNumber + ")");
                break;
            case Constants.ADV_NAME:
                elements = new ArrayList<>(getAllElementsCategoryList(Constants.ADV_NAME, true).subList(0, Utils.allowedAdvsNumber));
                tvHeadTitleCategory.setText("Table of " + categoryType + "(" + elements.size() + "/" + Utils.totalAdvsNumber + ")");
                break;
            case Constants.IDIOM_NAME:
                elements = new ArrayList<>(getAllElementsCategoryList(Constants.IDIOM_NAME, false).subList(0, Utils.allowedIdiomsNumber));
                tvHeadTitleCategory.setText("Table of " + categoryType + "(" + elements.size() + "/" + Utils.totalIdiomsNumber + ")");
                break;
        }

        close(); // Close database connection

        return elements;
    }
}
