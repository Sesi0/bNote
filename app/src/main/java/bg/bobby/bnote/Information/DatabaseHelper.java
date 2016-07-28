package bg.bobby.bnote.Information;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bg.bobby.bnote.Information.Model.Note;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "notes";
    private static final int DATABASE_VERSION = 1;
    private static final String NOTE_TABLE = "notes_table";
    private static final String CREATE_TABLE = "create table " + NOTE_TABLE + "(title TEXT,note TEXT primary key)";

    Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + NOTE_TABLE);

        // Create tables again
        onCreate(db);
    }

    /* Insert into database*/
    public void insertIntoDB(String title, String note) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("note", note);


        // 3. insert
        db.insert(NOTE_TABLE, null, values);
        // 4. close
        db.close();
        Toast.makeText(context, "insert value", Toast.LENGTH_LONG);
        Log.i("insert into DB", "After insert");
    }

    /* Retrive  data from database */
    public List<Note> getDataFromDB() {
        List<Note> modelList = new ArrayList<Note>();
        String query = "select * from " + NOTE_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Note model = new Note();
                model.setTitle(cursor.getString(1));
                model.setNote(cursor.getString(0));

                modelList.add(model);
            } while (cursor.moveToNext());
        }


        return modelList;
    }


    /*delete a row from database*/

    public void deleteARow(String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NOTE_TABLE, "note" + " = ?", new String[]{note});
        db.close();
    }


}