package com.example.stdmanager.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.stdmanager.models.GiaoVien;

import java.util.ArrayList;
import java.util.List;

public class GiaoVienDBHelper extends  SQLiteOpenHelper {
    private static final String TABLE_NAME = "giaovien";
    private static final String TAG = "SQLite";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PASSWORD = "password";

    public GiaoVienDBHelper(Context context) {
        super(context, DBConfig.getDatabaseName(), null, DBConfig.getDatabaseVersion());
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                String.format(
                        "CREATE TABLE %s ( " +
                        "%s INTEGER PRIMARY KEY, " +
                        "%s TEXT, " +
                        "%s TEXT)", TABLE_NAME, COLUMN_ID, COLUMN_NAME, COLUMN_PASSWORD);
        db.execSQL(CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }


    // If GiaoVien table has no data
    // default, Insert 2 records.
    public void createDefaultGiaoViensIfNeed()  {
        int count = this.getGiaoViensCount();
        if(count == 0 ) {
            GiaoVien GiaoVien1 = new GiaoVien(1, "Nguyễn Bích Thủy", "123456");
            GiaoVien GiaoVien2 = new GiaoVien(2, "Lê Văn Hiền", "123456");
            GiaoVien GiaoVien3 = new GiaoVien(3, "Trần Huy Hoàng", "123456");
            this.addGiaoVien(GiaoVien1);
            this.addGiaoVien(GiaoVien2);
            this.addGiaoVien(GiaoVien3);
        }
    }


    public void addGiaoVien(GiaoVien GiaoVien) {
        Log.i(TAG, "GiaoVienDBHelper.addGiaoVien ... " + GiaoVien.getName());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, GiaoVien.getId());
        values.put(COLUMN_NAME, GiaoVien.getName());
        values.put(COLUMN_PASSWORD, GiaoVien.getPassword());

        // Inserting Row
        db.insert(TABLE_NAME, null, values);

        // Closing database connection
        db.close();
    }


    public GiaoVien getGiaoVien(int id) {
        Log.i(TAG, "GiaoVienDBHelper.getGiaoVien ... " + id);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_ID,
                        COLUMN_NAME, COLUMN_PASSWORD }, COLUMN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        try {
            Log.i(TAG, "GiaoVienDBHelper.getGiaoVien ... " + cursor.getString(2));
            GiaoVien GiaoVien = new GiaoVien(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
            return GiaoVien;
        } catch(Exception ex){
            return null;
        }

    }


    public List<GiaoVien> getAllGiaoViens() {
        Log.i(TAG, "GiaoVienDBHelper.getAllGiaoViens ... " );

        List<GiaoVien> GiaoVienList = new ArrayList<GiaoVien>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                GiaoVien GiaoVien = new GiaoVien();
                GiaoVien.setId(Integer.parseInt(cursor.getString(0)));
                GiaoVien.setName(cursor.getString(1));
                GiaoVien.setPassword(cursor.getString(2));
                // Adding GiaoVien to list
                GiaoVienList.add(GiaoVien);
            } while (cursor.moveToNext());
        }

        // return GiaoVien list
        return GiaoVienList;
    }

    public int getGiaoViensCount() {
        Log.i(TAG, "GiaoVienDBHelper.getGiaoViensCount ... " );

        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }


    public int updateGiaoVien(GiaoVien GiaoVien) {
        Log.i(TAG, "GiaoVienDBHelper.updateGiaoVien ... "  + GiaoVien.getName());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, GiaoVien.getId());
        values.put(COLUMN_NAME, GiaoVien.getName());
        values.put(COLUMN_PASSWORD, GiaoVien.getPassword());

        // updating row
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(GiaoVien.getId())});
    }

    public void deleteGiaoVien(GiaoVien GiaoVien) {
        Log.i(TAG, "GiaoVienDBHelper.updateGiaoVien ... " + GiaoVien.getName() );

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[] { String.valueOf(GiaoVien.getId()) });
        db.close();
    }


    public void deleteAndCreatTable() {

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        createDefaultGiaoViensIfNeed();
    }

}

