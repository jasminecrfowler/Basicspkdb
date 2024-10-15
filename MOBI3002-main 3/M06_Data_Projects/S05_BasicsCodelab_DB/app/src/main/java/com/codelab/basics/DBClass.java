package com.codelab.basics;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBClass extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "TEST_DB.db";

    public static final String TABLE_NAME = "sample_table";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "str_col";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_ACCESS_COUNT = "access_count";

    public DBClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Save_v03", "DB onCreate()");

        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                COLUMN_NAME + " VARCHAR(256), " +
                COLUMN_DESCRIPTION + " VARCHAR(256), " +
                COLUMN_ACCESS_COUNT + " INTEGER DEFAULT 0" +
                ")");


        db.execSQL("INSERT INTO " + TABLE_NAME + "(" + COLUMN_NAME + ", " + COLUMN_DESCRIPTION + ") VALUES('Pikachu', 'Power Level: 100, An Electric-type Pokémon')");
        db.execSQL("INSERT INTO " + TABLE_NAME + "(" + COLUMN_NAME + ", " + COLUMN_DESCRIPTION + ") VALUES('Charmander', 'Power Level: 600, A Fire-type Pokémon')");
        db.execSQL("INSERT INTO " + TABLE_NAME + "(" + COLUMN_NAME + ", " + COLUMN_DESCRIPTION + ") VALUES('Bulbasaur', 'Power Level: 400, A Grass/Poison-type Pokémon')");
        db.execSQL("INSERT INTO " + TABLE_NAME + "(" + COLUMN_NAME + ", " + COLUMN_DESCRIPTION + ") VALUES('Squirtle', 'Power Level: 800, A Water-type Pokémon')");
        db.execSQL("INSERT INTO " + TABLE_NAME + "(" + COLUMN_NAME + ", " + COLUMN_DESCRIPTION + ") VALUES('Eevee', 'Power Level: 300, A Normal-type Pokémon')");
        db.execSQL("INSERT INTO " + TABLE_NAME + "(" + COLUMN_NAME + ", " + COLUMN_DESCRIPTION + ") VALUES('Mewtwo', 'Power Level: 200, A Psychic-type Pokémon')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        Log.d("Save_v03", "DB onUpgrade() to version " + DATABASE_VERSION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public void incrementAccessCount(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET " + COLUMN_ACCESS_COUNT + " = " + COLUMN_ACCESS_COUNT + " + 1 WHERE " + COLUMN_NAME + " = ?", new String[]{name});
    }


    public String getFavoritePokemon() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + COLUMN_NAME + " FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ACCESS_COUNT + " DESC LIMIT 1", null);
        if (c.moveToFirst()) {
            return c.getString(0);
        }
        return "No favorite yet!";
    }

    public String[][] get2DRecords() {
        Log.d("DBClass.get2DRecords", "in get2DRecords");

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (c.getCount() == 0) {
            return new String[0][0];
        }

        String[][] arr = new String[c.getCount()][2];

        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            arr[i][0] = c.getString(1);
            arr[i][1] = c.getString(2);
            incrementAccessCount(arr[i][0]);
            c.moveToNext();
        }
        c.close();

        return arr;
    }
}
