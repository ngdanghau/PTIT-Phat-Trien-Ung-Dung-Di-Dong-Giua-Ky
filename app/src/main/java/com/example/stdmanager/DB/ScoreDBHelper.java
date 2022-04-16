package com.example.stdmanager.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.stdmanager.models.Score;

import java.util.ArrayList;

public class ScoreDBHelper extends SQLiteOpenHelper
{
    public static final String TABLE_NAME = "SCORES";
    public static final String TAG = "SCORE SQLite";
    public static final String COLUMN_mahs = "MAHS";
    public static final String COLUMN_mamh = "MAMH";
    public static final String COLUMN_diem = "DIEM";

    public ScoreDBHelper(Context context)
    {
        super(context, DBConfig.getDatabaseName(), null, DBConfig.getDatabaseVersion());
    }

    // Creating Tables
    /*
    1 . MAMHS   int
    2 . MAMH  text
    3 . DIEM  int
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                String.format(
                        "CREATE TABLE %s ( %s INTEGER, %s INTEGER , %s INTEGER  )",
                                TABLE_NAME, COLUMN_mahs, COLUMN_mamh,COLUMN_diem);
        db.execSQL(CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void add(Score score) {
        Log.i(TAG, "AddScore HS:" + score.getMaHS() + "|| MH: " + score.getMaMH());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_mahs, score.getMaHS());
        values.put(COLUMN_mamh, score.getMaMH());
        values.put(COLUMN_diem, score.getDiem());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public void update(int student, int subject, double score) {

        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(COLUMN_mahs, student);
        values.put(COLUMN_mamh, subject);
        values.put(COLUMN_diem, score);

        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        db.update(TABLE_NAME, values, "MAHS=? and MAMH = ?", new String[]{String.valueOf(student), String.valueOf(subject)});
        db.close();
    }
//    public void create()  {
//        String countQuery = "SELECT  * FROM " + TABLE_NAME;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//
//        int count = cursor.getCount();
//        db.close();
//        if(count == 0 ) {
//
//        }
////        Score s1 = new Score(1, 1, 5);
////        Score s2 = new Score(2, 1, 4);
////        Score s3 = new Score(3, 1, 8);
////        Score s4 = new Score(4, 1, 9);
////        Score s5 = new Score(1, 2, 10);
////        Score s6 = new Score(1, 2, 5);
////        Score s7 = new Score(2, 2, 4);
////        Score s8 = new Score(3, 2, 8);
////        Score s9 = new Score(4, 2, 9);
////
////        this.add(s1);
////        this.add(s2);
////        this.add(s3);
////        this.add(s4);
////        this.add(s5);
////        this.add(s6);
////        this.add(s7);
////        this.add(s8);
////        this.add(s9);
//    }
    public ArrayList<Score> getByStudent(String student) {
        Log.i(TAG, "Score.getAll ... " );

        ArrayList<Score> scores = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                try{
                    Score score = new Score();
                    score.setMaHS(Integer.parseInt(cursor.getString(0)));
                    score.setMaMH(Integer.parseInt(cursor.getString(1)));
                    score.setDiem(Integer.parseInt(cursor.getString(2)));
                    scores.add(score);
                }catch (Exception exception)
                {
                    Log.i(TAG,  "ScoreDHelper.getAll error ");
                }

            } while (cursor.moveToNext());
        }
        return scores;
    }
    public ArrayList<Score> getAll() {
        Log.i(TAG, "Score.getAll ... " );

            ArrayList<Score> scores = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                try{
                    Score score = new Score();
                    score.setMaHS(Integer.parseInt(cursor.getString(0)));
                    score.setMaMH(Integer.parseInt(cursor.getString(1)));
                    score.setDiem(Integer.parseInt(cursor.getString(2)));
                    scores.add(score);
                }catch (Exception exception)
                {
                    Log.i(TAG,  "ScoreDHelper.getAll error ");
                }

            } while (cursor.moveToNext());
        }
        return scores;
    } public ArrayList<Score> getStudentBySubject(String subject) {
    Log.i(TAG, "Score.getAll ... " );

    ArrayList<com.example.stdmanager.models.Score> scores = new ArrayList<>();
    // Select All Query
    String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE MAMH = '" + subject + "'";

    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);

    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
        do {
            try{
                com.example.stdmanager.models.Score score = new com.example.stdmanager.models.Score();
                score.setMaHS(Integer.parseInt(cursor.getString(0)));
                score.setMaMH(Integer.parseInt(cursor.getString(1)));
                score.setDiem(Integer.parseInt(cursor.getString(2)));
                scores.add(score);
            }catch (Exception exception)
            {
                Log.i(TAG,  "ScoreDHelper.getAll error ");
            }

        } while (cursor.moveToNext());
    }
    return scores;
}

    public ArrayList<Score> getStudentAndSubject(String student, String subject) {
        Log.i(TAG, "Score.getAll ... " );

        ArrayList<com.example.stdmanager.models.Score> scores = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE MAHS = '" + student + "' AND MAMH = '" + subject + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                try{
                    com.example.stdmanager.models.Score score = new com.example.stdmanager.models.Score();
                    score.setMaHS(Integer.parseInt(cursor.getString(0)));
                    score.setMaMH(Integer.parseInt(cursor.getString(1)));
                    score.setDiem(Integer.parseInt(cursor.getString(2)));
                    scores.add(score);
                }catch (Exception exception)
                {
                    Log.i(TAG,  "ScoreDHelper.getAll error ");
                }

            } while (cursor.moveToNext());
        }
        return scores;
    }
    public ArrayList<Score> getStudentScore(String id) {
    Log.i(TAG, "Score.getAll ... " );

    ArrayList<com.example.stdmanager.models.Score> scores = new ArrayList<>();
    // Select All Query
    String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE MAHS = '" + id + "'";

    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);

    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
        do {
            try{
                com.example.stdmanager.models.Score score = new com.example.stdmanager.models.Score();
                score.setMaHS(Integer.parseInt(cursor.getString(0)));
                score.setMaMH(Integer.parseInt(cursor.getString(1)));
                score.setDiem(Integer.parseInt(cursor.getString(2)));
                scores.add(score);
            }catch (Exception exception)
            {
                Log.i(TAG,  "ScoreDHelper.getAll error ");
            }

        } while (cursor.moveToNext());
    }
    return scores;
}
    public SQLiteDatabase open()
    {
        return this.getWritableDatabase();
    }
    public void deleteAndCreateTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
//        create();
    }




}
