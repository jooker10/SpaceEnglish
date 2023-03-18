package anouar.oulhaj.p001.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MyDatabase extends SQLiteAssetHelper {

    public static final String DB_NAME = "db_learning.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_VERBS = "table_verbs";
    public static final String VERB_ID = "verb_id";
    public static final String VERB_fr = "verb_fr";
    public static final String VERB_eng = "verb_eng";

    public MyDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

}
