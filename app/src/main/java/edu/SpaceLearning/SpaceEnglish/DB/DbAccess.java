package edu.SpaceLearning.SpaceEnglish.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

import java.util.ArrayList;

import edu.SpaceLearning.SpaceEnglish._Main.Constants;
import edu.SpaceLearning.SpaceEnglish._Main.Utils;

public class DbAccess {

    private SQLiteDatabase sqLiteDB;
    private SQLiteOpenHelper openHelper;
    private static DbAccess instance;

    private DbAccess(Context context) {
        this.openHelper = new MyDatabase(context);
    }

    public static DbAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DbAccess(context);
        }
        return instance;
    }

    public void open_to_read() {
        this.sqLiteDB = this.openHelper.getReadableDatabase();
    }

    public void open_to_write() {
        this.sqLiteDB = this.openHelper.getWritableDatabase();
    }

    public void close() {
        if (sqLiteDB != null) sqLiteDB.close();
    }

    //-------------------------------------Verbs Functions-------------------------------



    // Retrieve AllCategoryElements Method
    public ArrayList<Category> getAllElementsOfCategory(String categoryType, boolean isTable) {
        ArrayList<Category> elementList = new ArrayList<>();

        Cursor cursor = selectCursorTable(categoryType);

        if (cursor.moveToFirst()) {
            do {
                int element_id = cursor.getInt(0);
                String element_eng = cursor.getString(1);
                String element_fr = cursor.getString(2);
                String element_sp = cursor.getString(3);
                String element_ar = cursor.getString(4);

                if (isTable) {
                    String element_examples = cursor.getString(5);
                    Category category = new Category(element_id,element_eng,element_fr,element_sp,element_ar,element_examples);
                    elementList.add(category);
                } else {
                    Category category = new Category(element_id,element_eng,element_fr,element_sp,element_ar);
                    elementList.add(category);
                }
            }
            while (cursor.moveToNext());
            cursor.close();
        }
        return elementList;
    }

    private Cursor selectCursorTable(String categoryType) {

        switch (categoryType) {
            case Constants.VERB_NAME :
                return sqLiteDB.rawQuery("SELECT * FROM " + MyDatabase.TABLE_VERBS, null);

            case Constants.SENTENCE_NAME:
                return sqLiteDB.rawQuery("SELECT * FROM " + MyDatabase.TABLE_SENTENSES, null);

            case Constants.PHRASAL_NAME:
                return sqLiteDB.rawQuery("SELECT * FROM " + MyDatabase.TABLE_PHRASAL, null);

            case Constants.NOUN_NAME:
                return sqLiteDB.rawQuery("SELECT * FROM " + MyDatabase.TABLE_NOUNS, null);

            case Constants.ADJ_NAME:
                return sqLiteDB.rawQuery("SELECT * FROM " + MyDatabase.TABLE_ADJECTIVES, null);

            case Constants.ADV_NAME:
                return sqLiteDB.rawQuery("SELECT * FROM " + MyDatabase.TABLE_ADVERBS , null);

            case Constants.IDIOM_NAME:
                return sqLiteDB.rawQuery("SELECT * FROM " + MyDatabase.TABLE_IDIOMS, null);
        }

        return sqLiteDB.rawQuery("SELECT * FROM " + MyDatabase.TABLE_VERBS, null);
    }

    public void getDBListCategorySize() {

        open_to_read();

        ArrayList<Category> allVerbsList = new ArrayList<>(getAllElementsOfCategory(Constants.VERB_NAME, true));
        ArrayList<Category> allSentencesList = new ArrayList<>(getAllElementsOfCategory(Constants.SENTENCE_NAME, false));
        ArrayList<Category> allPhrasalsList = new ArrayList<>(getAllElementsOfCategory(Constants.PHRASAL_NAME, true));
        ArrayList<Category> allNounsList = new ArrayList<>(getAllElementsOfCategory(Constants.NOUN_NAME, true));
        ArrayList<Category> allAdjsList = new ArrayList<>(getAllElementsOfCategory(Constants.ADJ_NAME, true));
        ArrayList<Category> allAdvsList = new ArrayList<>(getAllElementsOfCategory(Constants.ADV_NAME, true));
        ArrayList<Category> allIdiomsList = new ArrayList<>(getAllElementsOfCategory(Constants.IDIOM_NAME, false));

        close();

        // initialize the max of each category
        Utils.totalVerbsNumber = allVerbsList.size();
        Utils.totalSentencesNumber = allSentencesList.size();
        Utils.totalPhrasalsNumber = allPhrasalsList.size();
        Utils.totalNounsNumber = allNounsList.size();
        Utils.totalAdjsNumber = allAdjsList.size();
        Utils.totalAdvsNumber = allAdvsList.size();
        Utils.totalIdiomsNumber = allIdiomsList.size();
    }

     public ArrayList<Category> getRequiredElementsList(String categoryType , TextView tvHeadTitleCategory) {

        open_to_read();
        ArrayList<Category> elements = new ArrayList<>();
        switch (categoryType) {
            case Constants.VERB_NAME:
                elements = new ArrayList<>(getAllElementsOfCategory(Constants.VERB_NAME, true).subList(0,Utils.allowedVerbsNumber));
                tvHeadTitleCategory.setText("Table of " + categoryType +  "(" + elements.size() + "/"+ Utils.totalVerbsNumber +")");
                break;
            case Constants.SENTENCE_NAME:
                elements = new ArrayList<>(getAllElementsOfCategory(Constants.SENTENCE_NAME, false).subList(0,Utils.allowedSentencesNumber));
                tvHeadTitleCategory.setText("Table of " + categoryType +  "(" + elements.size() + "/"+ Utils.totalSentencesNumber +")");
                tvHeadTitleCategory.setTextSize(22f);
                break;
            case Constants.PHRASAL_NAME:
                elements = new ArrayList<>(getAllElementsOfCategory(Constants.PHRASAL_NAME, true).subList(0,Utils.allowedPhrasalsNumber));
                tvHeadTitleCategory.setText("Table of " + categoryType +  "(" + elements.size() + "/"+ Utils.totalPhrasalsNumber +")");
                break;
            case Constants.NOUN_NAME:
                elements = new ArrayList<>(getAllElementsOfCategory(Constants.NOUN_NAME, true).subList(0,Utils.allowedNounsNumber));
                tvHeadTitleCategory.setText("Table of " + categoryType +  "(" + elements.size() + "/"+ Utils.totalNounsNumber +")");
                break;
            case Constants.ADJ_NAME:
                elements = new ArrayList<>(getAllElementsOfCategory(Constants.ADJ_NAME, true).subList(0,Utils.allowedAdjsNumber));
                tvHeadTitleCategory.setText("Table of " + categoryType +  "(" + elements.size() + "/"+ Utils.totalAdjsNumber +")");

                break;
            case Constants.ADV_NAME:
                elements = new ArrayList<>(getAllElementsOfCategory(Constants.ADV_NAME, true).subList(0,Utils.allowedAdvsNumber));
                tvHeadTitleCategory.setText("Table of " + categoryType +  "(" + elements.size() + "/"+ Utils.totalAdvsNumber +")");
                break;
            case Constants.IDIOM_NAME:
                elements = new ArrayList<>(getAllElementsOfCategory(Constants.IDIOM_NAME, false).subList(0,Utils.allowedIdiomsNumber));
                tvHeadTitleCategory.setText("Table of " + categoryType +  "(" + elements.size() + "/"+ Utils.totalIdiomsNumber +")");
                break;
        }
        close();

        return elements;
    }

}
