package com.example.stdmanager.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.stdmanager.models.Grade;
import com.example.stdmanager.models.Student;

import java.util.ArrayList;

public class StudentOpenHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "student";
    private static final  String REFERENCED_TABLE_NAME = "grade";
    private static final String TAG = "SQLite";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FAMILY_NAME = "familyName";

    private static final String COLUMN_FIRST_NAME = "firstName";
    private static final String COLUMN_GENDER = "gender";

    private static final String COLUMN_BIRTHDAY = "birthday";
    private static final String COLUMN_GRADE_ID = "gradeId";

    public StudentOpenHelper(@Nullable Context context) {
        super(context, DBConfig.getDatabaseName(), null, DBConfig.getDatabaseVersion());
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = String.format("CREATE TABLE %s" +
                "( %s INTEGER PRIMARY KEY AUTOINCREMENT," +
                "%s TEXT, " +
                "%s TEXT," +
                "%s INTEGER," +
                "%s TEXT, " +
                "%s INTEGER REFERENCES [%s](id) ON DELETE CASCADE NOT NULL )",
                TABLE_NAME, COLUMN_ID, COLUMN_FAMILY_NAME, COLUMN_FIRST_NAME, COLUMN_GENDER, COLUMN_BIRTHDAY, COLUMN_GRADE_ID, REFERENCED_TABLE_NAME);

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /**
     * @author Phong-Kaster
     * @param student | object | the grade which is added to TABLE_GRADE
     */
    public void create(Student student)
    {
        /*Step 1*/
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        /*Step 2*/
        String familyName = student.getFamilyName();
        String firstName = student.getFirstName();

        int gender = student.getGender();
        String birthday = student.getBirthday();
        int gradeId = student.getGradeId();

        /*Step 3*/
        contentValues.put(COLUMN_FAMILY_NAME, familyName);
        contentValues.put(COLUMN_FIRST_NAME, firstName);
        contentValues.put(COLUMN_GENDER, gender);
        contentValues.put(COLUMN_BIRTHDAY, birthday);
        contentValues.put(COLUMN_GRADE_ID, gradeId);


        /*Step 4*/
        sqLiteDatabase.insert(TABLE_NAME,null, contentValues);
        sqLiteDatabase.close();
    }

    /**
     * @author Phong-Kaster
     * @param student | object | the grade which is deleted from TABLE_GRADE
     */
    public void delete(Student student)
    {
        /*Step 1*/
        int id = student.getId();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        /*Step 2*/
        sqLiteDatabase.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[]{ String.valueOf(id)} );
        sqLiteDatabase.close();
    }

    public void update(Student student)
    {
        /*Step 1*/
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        /*Step 2*/
        int id = student.getId();
        String familyName = student.getFamilyName();
        String firstName = student.getFirstName();

        int gender = student.getGender();
        String birthday = student.getBirthday();
        int gradeId = student.getGradeId();


        /*Step 3*/
        ContentValues values = new ContentValues();
        values.put(COLUMN_FAMILY_NAME, familyName);
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_GENDER, gender);
        values.put(COLUMN_BIRTHDAY, birthday);
        values.put(COLUMN_GRADE_ID, gradeId);


        /*Step 4*/
        sqLiteDatabase.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[]{ String.valueOf(id) } );
    }

    /**
     * @author Phong-Kaster
     * retrieve all students from TABLE STUDENT
     * */
    public ArrayList<Student> retrieveAllStudents()
    {
        /*Step 1*/
        ArrayList<Student> objects = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();



        /*Step 2*/
        String query = String.format("SELECT s.*, g.name FROM student s INNER JOIN grade g ON s.gradeId = g.id");
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        /*Step 3*/
        if( cursor.moveToFirst() )
        {
            do
            {
                Student student = new Student();

                student.setId( Integer.parseInt( cursor.getString(0) ) );

                student.setFamilyName( cursor.getString(1));
                student.setFirstName(  cursor.getString(2));

                student.setGender(Integer.parseInt( cursor.getString(3) ));
                student.setBirthday(cursor.getString(4));

                student.setGradeId(Integer.parseInt( cursor.getString(5) ));
                student.setGradeName(cursor.getString(6));

                objects.add(student);
            }while( cursor.moveToNext() );
        }

        return objects;
    }


    public ArrayList<Student> retrieveStudentWithKeyword(String keyword)
    {
        /*Step 1*/
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<Student> objects = new ArrayList<>();

        /*Step 2*/
        String query = "SELECT s.*, g.name " +
                "FROM student s " +
                "INNER JOIN grade g " +
                "ON s.gradeId = g.id " +
                "WHERE s.familyName LIKE '"+ keyword+ "%' " +
                "OR s.firstName LIKE '"+ keyword+ "%' ";
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        /*Step 3*/
        if( cursor.moveToFirst() )
        {
            do
            {
                Student student = new Student();

                student.setId( Integer.parseInt( cursor.getString(0) ) );

                student.setFamilyName( cursor.getString(1));
                student.setFirstName(  cursor.getString(2));

                student.setGender(Integer.parseInt( cursor.getString(3) ));
                student.setBirthday(cursor.getString(4));

                student.setGradeId(Integer.parseInt( cursor.getString(5) ));
                student.setGradeName(cursor.getString(6));

                objects.add(student);
            }while( cursor.moveToNext() );
        }

        return objects;
    }

    /**
     * @author Phong-Kaster
     * retrieve number of grade
     * @return int quantity
     */
    private int count()
    {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        int quantity = cursor.getCount();
        cursor.close();
        sqLiteDatabase.close();
        return quantity;
    }

    /**
     * @author Phong-Kaster
     * create default records if there is nothing in Grade table
     */
    private void createDefaultRecords()
    {
        /*Step 1*/
        int studentQuantity = this.count();
        if( studentQuantity != 0)
            return;

        /*Step 2*/
        Student student1 = new Student("Nguyen","Phong",0,"01-05-2000", 1);
        Student student2 = new Student("Ho","Hau",0,"01-05-2000", 1);
        Student student3 = new Student("Luong","Khang",0,"01-05-2000", 1);

        /*Step 3*/
        this.create(student1);
        this.create(student2);
        this.create(student3);

    }

    public void deleteAndCreateTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        createDefaultRecords();
    }
}
