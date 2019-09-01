package com.repolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private final static String TAG = DatabaseHelper.class.getSimpleName();
    private static DatabaseHelper mInstance = null;

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "repolist";

    // Table Name
    private static final String TABLE_FAVS = "favorites_list";

    // Column names for TABLE_FAVS:
    private static final String KEY_ID = "_id";
    private static final String KEY_OWNER_ID = "owner_id";
    private static final String KEY_REPO_ID = "repo_id";

    // Table Create Statements:
    private static final String CREATE_TABLE_FAVS = "CREATE TABLE "
            + TABLE_FAVS   + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_OWNER_ID + " TEXT,"
            + KEY_REPO_ID  + " TEXT" + ");";

    public static DatabaseHelper getInstance(Context ctx) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (mInstance == null) {
            mInstance = new DatabaseHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_FAVS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVS);

        // create new tables
        onCreate(db);
    }

    public void addRepo(int owner_id, int repo_id) {
        // Create and/or open the database for writing:
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_OWNER_ID, owner_id);
            values.put(KEY_REPO_ID, repo_id);
            db.insertOrThrow(TABLE_FAVS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    // delete from table
    public void deleteRepo(String repoID){
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlDelete = "DELETE FROM " + TABLE_FAVS + " WHERE "
                + KEY_REPO_ID + " ='" + repoID + "'";
        db.execSQL(sqlDelete);
        db.close();
    }

    // get favorite repo ids of a certain user
    public ArrayList<String> getRepos(String ownerID) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();

        // Query parameters:
        String selectQuery = "SELECT * FROM " + TABLE_FAVS;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                arrayList.add(Integer.toString(c.getColumnIndex(KEY_REPO_ID)));
            } while (c.moveToNext());
        }
        Log.d(TAG, "*** ArrayList.size() " + arrayList.size());
        c.close();
        return arrayList;
    }

    // check whether repo already exists in favorites table
    public boolean checkFavorite(String repoID) {
        SQLiteDatabase db = getReadableDatabase();

        // query parameters
        String selectQuery = "SELECT * FROM " + TABLE_FAVS + " WHERE "
                + KEY_REPO_ID + " ='" + repoID + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }

        cursor.close();
        return true;
    }

    // Close DB connection:
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}