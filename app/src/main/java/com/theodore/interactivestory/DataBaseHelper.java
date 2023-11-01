package com.theodore.interactivestory;
import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLData;

public class DataBaseHelper extends SQLiteOpenHelper {

    // Database version & name
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "storyDatabase";

    //StoryNode table
    private static final String TABLE_STORYNODE = "storyNode";
    private static final String KEY_NODEID = "nodeID";
    private static final String KEY_NARRATIVE = "narrative";

    //Choice table

    private static final String TABLE_CHOICE = "Choice";
    private static final String KEY_CHOICEID = "choiceID";
    private static final String KEY_CHOICETEXT = "ChoiceText";
    private static final String KEY_NEXTCHOICEID = "nextNodeID";

    public DataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        /*
         Create tables
         ... SQL CREATE TABLE commands here ...
        */
        String CREATE_STORYNODE_TABLE = "CREATE TABLE " + TABLE_STORYNODE + " (" +
                KEY_NODEID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_NARRATIVE + " TEXT NOT NULL" +
                ")";
        db.execSQL(CREATE_STORYNODE_TABLE);

        String CREATE_CHOICES_TABLE = "CREATE TABLE " + TABLE_CHOICE + " (" +
                KEY_CHOICEID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_NODEID + " INTEGER," +
                KEY_CHOICETEXT + " TEXT NOT NULL," +
                KEY_NEXTCHOICEID + " INTEGER," +
                "FOREIGN KEY(" + KEY_NODEID + ") REFERENCES " + TABLE_STORYNODE + "(" + KEY_NODEID + ")," +
                "FOREIGN KEY(" + KEY_NEXTCHOICEID + ") REFERENCES " + TABLE_STORYNODE + "(" + KEY_NODEID + ")" +
                ")";
        db.execSQL(CREATE_CHOICES_TABLE);

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Handle database upgrades if needed
        // For now, simply drop the old table and create a new one
        // ... SQL DROP TABLE commands here ...
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHOICE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORYNODE);

        // Create tables again
        onCreate(db);

    }
    public StoryNode getStoryNode(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        StoryNode storyNode = null;

        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + id;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            String narrative = cursor.getString(cursor.getColumnIndex(COLUMN_NARRATIVE));

            // TODO: You also need to fetch associated choices here

            storyNode = new StoryNode(narrative);
            cursor.close();
        }

        db.close();
        return storyNode;
    }


}
