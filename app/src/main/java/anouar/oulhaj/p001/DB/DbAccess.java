package anouar.oulhaj.p001.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import anouar.oulhaj.p001._Main.Constants;
import anouar.oulhaj.p001._Main.Utils;

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

    public void fillDataFromDBWithSomeInitialization() {

        open_to_read();
        Utils.verbsList = new ArrayList<>(getAllElementsOfCategory(Constants.VERB_NAME, true).subList(0,Utils.allowedVerbsNumber));
        Utils.sentencesList = new ArrayList<>(getAllElementsOfCategory(Constants.SENTENCE_NAME, false).subList(0,Utils.allowedSentencesNumber));
        Utils.phrasalsList = new ArrayList<>(getAllElementsOfCategory(Constants.PHRASAL_NAME, true).subList(0,Utils.allowedPhrasalsNumber));
        Utils.nounsList = new ArrayList<>(getAllElementsOfCategory(Constants.NOUN_NAME, true).subList(0,Utils.allowedNounsNumber));
        Utils.adjsList = new ArrayList<>(getAllElementsOfCategory(Constants.ADJ_NAME, true).subList(0,Utils.allowedAdjsNumber));
        Utils.advsList = new ArrayList<>(getAllElementsOfCategory(Constants.ADV_NAME, true).subList(0,Utils.allowedAdvsNumber));
        Utils.idiomsList = new ArrayList<>(getAllElementsOfCategory(Constants.IDIOM_NAME, false).subList(0,Utils.allowedIdiomsNumber));
        close();

        // initialize the max of each category
        Utils.allVerbsNumber = Utils.verbsList.size();
        Utils.allSentencesNumber = Utils.sentencesList.size();
        Utils.allPhrasalsNumber = Utils.phrasalsList.size();
        Utils.allNounsNumber = Utils.nounsList.size();
        Utils.allAdjsNumber = Utils.adjsList.size();
        Utils.allAdvsNumber= Utils.advsList.size();
        Utils.allIdiomsNumber= Utils.idiomsList.size();
    }

}
