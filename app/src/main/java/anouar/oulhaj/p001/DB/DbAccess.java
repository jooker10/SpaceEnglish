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


    // Retrieve Verbs method    ----important-----
    public ArrayList<Verb> getAllVerbs() {
        ArrayList<Verb> verbsList = new ArrayList<>();

        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM " + MyDatabase.TABLE_VERBS, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String verb_eng = cursor.getString(1);
                String verb_fr = cursor.getString(2);
                String verb_sp = cursor.getString(3);
                String verb_ar = cursor.getString(4);
                String verb_example = cursor.getString(5);
                Verb verb = new Verb(id, verb_eng, verb_fr, verb_sp, verb_ar,verb_example);
                verbsList.add(verb);
            }
            while (cursor.moveToNext());
            cursor.close();
        }
        return verbsList;
    }

    //-------------------------------------Sentences Functions-------------------------------


    // Retrieve Sentences method
    public ArrayList<Sentence> getAllSentences() {
        ArrayList<Sentence> sentencesList = new ArrayList<>();

        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM " + MyDatabase.TABLE_SENTENSES, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String sentence_eng = cursor.getString(1);
                String sentence_fr = cursor.getString(2);
                String sentence_sp = cursor.getString(3);
                String sentence_ar= cursor.getString(4);


                Sentence sentence = new Sentence(id, sentence_eng, sentence_fr,sentence_sp,sentence_ar);
                sentencesList.add(sentence);

            }
            while (cursor.moveToNext());
            cursor.close();
        }


        return sentencesList;
    }

    //-------------------------------------Phrasal Functions-------------------------------


    // Retrieve Phrasal method
    public ArrayList<Phrasal> getAllPhrasal() {
        ArrayList<Phrasal> phrasalList = new ArrayList<>();

        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM " + MyDatabase.TABLE_PHRASAL, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String phrasal_eng = cursor.getString(1);
                String phrasal_fr = cursor.getString(2);
                String phrasal_sp = cursor.getString(3);
                String phrasal_ar = cursor.getString(4);
                String phrasal_example = cursor.getString(5);

                Phrasal phrasal = new Phrasal(id, phrasal_eng, phrasal_fr,phrasal_sp,phrasal_ar,phrasal_example);
                phrasalList.add(phrasal);

            }
            while (cursor.moveToNext());
            cursor.close();
        }


        return phrasalList;
    }

    // Retrieve Nouns method    ----important-----
    public ArrayList<Noun> getAllNouns() {
        ArrayList<Noun> nounsList = new ArrayList<>();

        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM " + MyDatabase.TABLE_NOUNS, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String noun_eng = cursor.getString(1);
                String noun_fr = cursor.getString(2);
                String noun_sp = cursor.getString(3);
                String noun_ar = cursor.getString(4);
                String noun_example = cursor.getString(5);
                Noun noun = new Noun(id, noun_eng, noun_fr, noun_sp, noun_ar,noun_example);
                nounsList.add(noun);
            }
            while (cursor.moveToNext());
            cursor.close();
        }
        return nounsList;
    }
    // Retrieve Adjs method    ----important-----
    public ArrayList<Adjective> getAllAdjs() {
        ArrayList<Adjective> adjsList = new ArrayList<>();

        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM " + MyDatabase.TABLE_ADJECTIVES, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String adj_eng = cursor.getString(1);
                String adj_fr = cursor.getString(2);
                String adj_sp = cursor.getString(3);
                String adj_ar = cursor.getString(4);
                String adj_example = cursor.getString(5);
                Adjective adj = new Adjective(id, adj_eng, adj_fr, adj_sp, adj_ar,adj_example);
                adjsList.add(adj);
            }
            while (cursor.moveToNext());
            cursor.close();
        }
        return adjsList;
    }
    // Retrieve Advs method    ----important-----
    public ArrayList<Adverb> getAllAdverbs() {
        ArrayList<Adverb> advsList = new ArrayList<>();

        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM " + MyDatabase.TABLE_ADVERBS, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String adv_eng = cursor.getString(1);
                String adv_fr = cursor.getString(2);
                String adv_sp = cursor.getString(3);
                String adv_ar = cursor.getString(4);
                String adv_example = cursor.getString(5);
                Adverb adv = new Adverb(id, adv_eng, adv_fr, adv_sp, adv_ar,adv_example);
                advsList.add(adv);
            }
            while (cursor.moveToNext());
            cursor.close();
        }
        return advsList;
    }
    // Retrieve Idioms method    ----important-----
    public ArrayList<Idiom> getAllIdioms() {
        ArrayList<Idiom> idiomsList = new ArrayList<>();

        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM " + MyDatabase.TABLE_IDIOMS, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String idiom_eng = cursor.getString(1);
                String idiom_fr = cursor.getString(2);
                String idiom_sp = cursor.getString(3);
                String idiom_ar = cursor.getString(4);

                Idiom idiom = new Idiom(id, idiom_eng, idiom_fr, idiom_sp, idiom_ar);
                idiomsList.add(idiom);
            }
            while (cursor.moveToNext());
            cursor.close();
        }
        return idiomsList;
    }
}
