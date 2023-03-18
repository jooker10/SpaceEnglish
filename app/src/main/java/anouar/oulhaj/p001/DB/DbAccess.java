package anouar.oulhaj.p001.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DbAccess {

    private SQLiteDatabase sqLiteDB;
    private SQLiteOpenHelper openHelper;
    private static DbAccess instance;

    private DbAccess(Context context){
        this.openHelper = new MyDatabase(context);
    }

    public static DbAccess getInstance(Context context){
        if(instance == null){
            instance = new DbAccess(context);
        }
        return instance;
    }

    public void open_to_read(){
        this.sqLiteDB = this.openHelper.getReadableDatabase();
    }
    public void open_to_write(){
        this.sqLiteDB = this.openHelper.getWritableDatabase();
    }
    public void close(){if(sqLiteDB != null) sqLiteDB.close();}



    // insert method
    public boolean InsertVerbs(Verb verb){

        ContentValues values = new ContentValues();
        values.put(MyDatabase.VERB_fr,verb.getVerb_fr());
        values.put(MyDatabase.VERB_eng,verb.getVerb_eng());

        long result = sqLiteDB.insert(MyDatabase.TABLE_VERBS,null,values);
        return result!=-1;
    }

    // update method
    public boolean UpdateVerbs(Verb verb){
        ContentValues values = new ContentValues();
        values.put(MyDatabase.VERB_fr,verb.getVerb_fr());
        values.put(MyDatabase.VERB_eng,verb.getVerb_eng());

        String args[] = {String.valueOf(verb.getVerb_id())};
        long result = sqLiteDB.update(MyDatabase.TABLE_VERBS,values,"id=?",args);
        return result>0;
    }

    // getAllPersons
    public long getVerbsCount(){
        return DatabaseUtils.queryNumEntries(openHelper.getReadableDatabase(),MyDatabase.TABLE_VERBS);
    }


    // Delete method
    public boolean DeleteVerbs(Verb verb){

        String[] args = {String.valueOf(verb.getVerb_id())};

        int result =  sqLiteDB.delete(MyDatabase.TABLE_VERBS,"id=?",args);
        return result > 0;
    }

    // Retrieve method
    public ArrayList<Verb> getAllVerbs(){
        ArrayList<Verb> verbsList = new ArrayList<>();

        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+MyDatabase.TABLE_VERBS,null);

        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String verb_fr = cursor.getString(1);
                String verb_eng = cursor.getString(2);

                Verb verb = new Verb(id,verb_fr,verb_eng);
                verbsList.add(verb);

            }
            while (cursor.moveToNext());
            cursor.close();
        }


        return verbsList;
    }


}
