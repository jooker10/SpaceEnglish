package anouar.oulhaj.p001.DB;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MyDatabase extends SQLiteAssetHelper {

    public static final String DB_NAME = "english.db";
    public static final int DB_VERSION = 1;

    //----Table of Verbs---------
    public static final String TABLE_VERBS = "table_verbs";
    public static final String VERB_ID = "verb_id";
    public static final String VERB_fr = "verb_fr";
    public static final String VERB_eng = "verb_eng";
    //----Table of Sentences---------
    public static final String TABLE_SENTENSES = "table_sentences";
    public static final String SENTENSES_ID = "sentence_id";
    public static final String SENTENSES_fr = "sentence_fr";
    public static final String SENTENSES_eng = "sentence_eng";
    //----Table of Phrasal Verbs---------
    public static final String TABLE_PHRASAL = "table_phrasal";
    public static final String PHRASAL_ID = "phrasal_id";
    public static final String PHRASAL_fr = "phrasal_fr";
    public static final String PHRASAL_eng = "phrasal_eng";

    public MyDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

}
