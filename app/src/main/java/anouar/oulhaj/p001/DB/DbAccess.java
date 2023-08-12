package anouar.oulhaj.p001.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.IslamicCalendar;

import java.util.ArrayList;
import java.util.Collections;

import anouar.oulhaj.p001.EnumCategory;
import anouar.oulhaj.p001.Utils;

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
            case "VERB":
                return sqLiteDB.rawQuery("SELECT * FROM " + MyDatabase.TABLE_VERBS, null);

            case "SENTENCE":
                return sqLiteDB.rawQuery("SELECT * FROM " + MyDatabase.TABLE_SENTENSES, null);

            case "PHRASAL":
                return sqLiteDB.rawQuery("SELECT * FROM " + MyDatabase.TABLE_PHRASAL, null);

            case "NOUN":
                return sqLiteDB.rawQuery("SELECT * FROM " + MyDatabase.TABLE_NOUNS, null);

            case "ADJECTIVE":
                return sqLiteDB.rawQuery("SELECT * FROM " + MyDatabase.TABLE_ADJECTIVES, null);

            case "ADVERB":
                return sqLiteDB.rawQuery("SELECT * FROM " + MyDatabase.TABLE_ADVERBS, null);

            case "IDIOM":
                return sqLiteDB.rawQuery("SELECT * FROM " + MyDatabase.TABLE_IDIOMS, null);
        }

        return sqLiteDB.rawQuery("SELECT * FROM " + MyDatabase.TABLE_IDIOMS, null);
    }

  /*  private Category setConstructorCategory(String categoryType, int id, String eng, String fr, String sp, String ar, String ex) {
        if (categoryType.equals("SENTENCE") || categoryType.equals("IDIOM")) {
            return new Category(id, eng, fr, sp, ar);
        } else {
            return new Category(id, eng, fr, sp, ar, ex);
        }
    }*/
}
