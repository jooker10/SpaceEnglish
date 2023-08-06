package anouar.oulhaj.p001.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

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
    // insert Verbs method
    public boolean InsertVerbs(Verb verb) {

        ContentValues values = new ContentValues();
        values.put(MyDatabase.VERB_fr, verb.getVerb_fr());
        values.put(MyDatabase.VERB_eng, verb.getVerb_eng());

        long result = sqLiteDB.insert(MyDatabase.TABLE_VERBS, null, values);
        return result != -1;
    }

    // update Verbs method
    public boolean UpdateVerbs(Verb verb) {
        ContentValues values = new ContentValues();
        values.put(MyDatabase.VERB_fr, verb.getVerb_fr());
        values.put(MyDatabase.VERB_eng, verb.getVerb_eng());

        String args[] = {String.valueOf(verb.getVerb_id())};
        long result = sqLiteDB.update(MyDatabase.TABLE_VERBS, values, "id=?", args);
        return result > 0;
    }

    // getAllVerbs
    public long getVerbsCount() {
        return DatabaseUtils.queryNumEntries(openHelper.getReadableDatabase(), MyDatabase.TABLE_VERBS);
    }


    // Delete Verb method
    public boolean DeleteVerbs(Verb verb) {

        String[] args = {String.valueOf(verb.getVerb_id())};

        int result = sqLiteDB.delete(MyDatabase.TABLE_VERBS, "id=?", args);
        return result > 0;
    }

    // Retrieve Verbs method
    public ArrayList<Verb> getAllVerbs() {
        ArrayList<Verb> verbsList = new ArrayList<>();

        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM " + MyDatabase.TABLE_VERBS, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String verb_fr = cursor.getString(1);
                String verb_eng = cursor.getString(2);

                Verb verb = new Verb(id, verb_fr, verb_eng);
                verbsList.add(verb);

            }
            while (cursor.moveToNext());
            cursor.close();
        }


        return verbsList;
    }

    //-------------------------------------Sentences Functions-------------------------------

    // insert Sentences method
    public boolean InsertSentence(Sentence sentence) {

        ContentValues values = new ContentValues();
        values.put(MyDatabase.SENTENSES_fr, sentence.getSentence_fr());
        values.put(MyDatabase.SENTENSES_eng, sentence.getSentence_eng());

        long result = sqLiteDB.insert(MyDatabase.TABLE_SENTENSES, null, values);
        return result != -1;
    }

    // update sentences method
    public boolean UpdateSentence(Sentence sentence) {
        ContentValues values = new ContentValues();
        values.put(MyDatabase.VERB_fr, sentence.getSentence_fr());
        values.put(MyDatabase.VERB_eng, sentence.getSentence_eng());

        String args[] = {String.valueOf(sentence.getSentence_id())};
        long result = sqLiteDB.update(MyDatabase.TABLE_SENTENSES, values, "id=?", args);
        return result > 0;
    }

    // getAllSentences
    public long getSentencesCount() {
        return DatabaseUtils.queryNumEntries(openHelper.getReadableDatabase(), MyDatabase.TABLE_SENTENSES);
    }


    // Delete sentences method
    public boolean DeleteSentences(Sentence sentence) {

        String[] args = {String.valueOf(sentence.getSentence_id())};

        int result = sqLiteDB.delete(MyDatabase.TABLE_SENTENSES, "id=?", args);
        return result > 0;
    }

    // Retrieve Sentences method
    public ArrayList<Sentence> getAllSentences() {
        ArrayList<Sentence> sentencesList = new ArrayList<>();

        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM " + MyDatabase.TABLE_SENTENSES, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String sentence_fr = cursor.getString(1);
                String sentence_eng = cursor.getString(2);

                Sentence sentence = new Sentence(id, sentence_fr, sentence_eng);
                sentencesList.add(sentence);

            }
            while (cursor.moveToNext());
            cursor.close();
        }


        return sentencesList;
    }

    //-------------------------------------Phrasal Functions-------------------------------

    // insert phrasal method
    public boolean InsertPhrasal(Phrasal phrasal) {

        ContentValues values = new ContentValues();
        values.put(MyDatabase.VERB_fr, phrasal.getPhrasal_fr());
        values.put(MyDatabase.VERB_eng, phrasal.getGetPhrasal_eng());

        long result = sqLiteDB.insert(MyDatabase.TABLE_PHRASAL, null, values);
        return result != -1;
    }

    // update phrasal method
    public boolean UpdatePhrasal(Phrasal phrasal) {
        ContentValues values = new ContentValues();
        values.put(MyDatabase.VERB_fr, phrasal.getPhrasal_fr());
        values.put(MyDatabase.VERB_eng, phrasal.getGetPhrasal_eng());

        String args[] = {String.valueOf(phrasal.getPhrasal_id())};
        long result = sqLiteDB.update(MyDatabase.TABLE_PHRASAL, values, "id=?", args);
        return result > 0;
    }

    // getAllPhrasal
    public long getPhrasalCount() {
        return DatabaseUtils.queryNumEntries(openHelper.getReadableDatabase(), MyDatabase.TABLE_PHRASAL);
    }


    // Delete phrasal method
    public boolean DeletePhrasal(Phrasal phrasal) {

        String[] args = {String.valueOf(phrasal.getPhrasal_id())};

        int result = sqLiteDB.delete(MyDatabase.TABLE_PHRASAL, "id=?", args);
        return result > 0;
    }

    // Retrieve Phrasal method
    public ArrayList<Phrasal> getAllPhrasal() {
        ArrayList<Phrasal> phrasalList = new ArrayList<>();

        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM " + MyDatabase.TABLE_PHRASAL, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String phrasal_fr = cursor.getString(1);
                String phrasal_eng = cursor.getString(2);

                Phrasal phrasal = new Phrasal(id, phrasal_fr, phrasal_eng);
                phrasalList.add(phrasal);

            }
            while (cursor.moveToNext());
            cursor.close();
        }


        return phrasalList;
    }


}
