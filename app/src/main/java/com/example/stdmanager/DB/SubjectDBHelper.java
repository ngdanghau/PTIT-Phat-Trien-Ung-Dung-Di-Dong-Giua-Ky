package com.example.stdmanager.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.stdmanager.models.Subject;
import com.example.stdmanager.models.Teacher;

import java.util.ArrayList;
import java.util.List;

public class SubjectDBHelper extends SQLiteOpenHelper
{
    private static final String TABLE_NAME = "subject";
    private static final String TAG = "SQLite";
    private static final String COLUMN_mamh = "MAMH";
    private static final String COLUMN_tenmh = "TENMH";
    private static final String COLUMN_hocky = "HOCKY";
    private static final String COLUMN_namhoc = "NAMHOC";
    private static final String COLUMN_heso = "HESO";

    public SubjectDBHelper(Context context)
    {
        super(context, DBConfig.getDatabaseName(), null, DBConfig.getDatabaseVersion());
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                String.format(
                        "CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s INTEGER, %s TEXT, %s TEXT , %s INTEGER  )",
                                TABLE_NAME, COLUMN_mamh,                       COLUMN_tenmh,COLUMN_hocky,COLUMN_namhoc,COLUMN_heso);
        db.execSQL(CREATE_TABLE);
        createDefaultSubject();
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
//        onCreate(db);
    }

    public void AddSubject(Subject subject) {
        Log.i(TAG, "AddSubject " + subject.getTenMH());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_tenmh, subject.getTenMH());
        values.put(COLUMN_heso, subject.getHeSo());
        values.put(COLUMN_hocky, subject.getHocKy());
        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        // Closing database connection
        db.close();
    }

    // If Subject table has no data
    // default, Insert 6 records.
    public void createDefaultSubject()  {
//        String countQuery = "SELECT  * FROM " + TABLE_NAME;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//
//        int count = cursor.getCount();
//        db.close();
//        if(count == 0 ) {
//
//        }
            Subject subject1 = new Subject(1,"Toán",1,2,"2021-2022");
            Subject subject2 = new Subject(2,"Văn",1,2,"2021-2022");
            Subject subject3 = new Subject(3,"Anh",1,2,"2021-2022");
            this.AddSubject(subject1);
            this.AddSubject(subject2);
            this.AddSubject(subject3);


    }

    public List<Teacher> getAllSubjects() {
        Log.i(TAG, "TeacherDBHelper.getAllTeachers ... " );

        List<Teacher> TeacherList = new ArrayList<Teacher>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Teacher Teacher = new Teacher();
                Teacher.setId(Integer.parseInt(cursor.getString(0)));
                Teacher.setName(cursor.getString(1));
                Teacher.setPassword(cursor.getString(2));
                // Adding Teacher to list
                TeacherList.add(Teacher);
            } while (cursor.moveToNext());
        }

        // return Teacher list
        return TeacherList;
    }

    public SQLiteDatabase open()
    {
        return this.getWritableDatabase();
    }





}
