package anouar.oulhaj.p001.DB;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MyDatabase extends SQLiteAssetHelper {

    public static final String DB_NAME = "english_learning.db";
    public static final int DB_VERSION = 1;

    //----Table of Verbs---------
    public static final String TABLE_VERBS = "table_verbs";
    public static final String VERB_ID = "id_verb";
    public static final String VERB_eng = "english_verb";
    public static final String VERB_fr = "french_verb";
    public static final String VERB_sp = "spanish_verb";
    public static final String VERB_ar = "arabic_verb";
    //----Table of Sentences---------
    public static final String TABLE_SENTENSES = "table_sentences";
    public static final String SENTENSES_ID = "id_sentence";
    public static final String SENTENSES_eng = "english_sentence";
    public static final String SENTENSES_fr = "french_sentence";
    public static final String SENTENSES_sp = "spanish_sentence";
    public static final String SENTENSES_ar = "arabic_sentence";
    //----Table of Phrasal Verbs---------
    public static final String TABLE_PHRASAL = "table_phrasals";
    public static final String PHRASAL_ID = "id_phrasal";
    public static final String PHRASAL_eng = "english_phrasal";
    public static final String PHRASAL_fr = "french_phrasal";
    public static final String PHRASAL_sp = "spanish_phrasal";
    public static final String PHRASAL_ar = "arabic_phrasal";

    public MyDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

}
