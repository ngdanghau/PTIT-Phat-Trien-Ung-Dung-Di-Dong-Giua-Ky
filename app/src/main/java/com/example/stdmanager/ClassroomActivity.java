package com.example.stdmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ListView;

import com.example.stdmanager.DB.GradeOpenHelper;
import com.example.stdmanager.DB.StudentOpenHelper;
import com.example.stdmanager.listViewModels.ClassroomListViewModel;
import com.example.stdmanager.models.Grade;
import com.example.stdmanager.models.Student;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

public class ClassroomActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Student> objects = new ArrayList<>();
    ClassroomListViewModel listViewModel;

    ArrayList<Grade> gradeObjects;
    GradeOpenHelper gradeOpenHelper = new GradeOpenHelper(this);
    StudentOpenHelper studentOpenHelper = new StudentOpenHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom);

        /*The command line belows that make sure that keyboard only pops up only if user clicks into EditText */
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);



//        gradeOpenHelper.deleteAndCreatTable();
//        gradeObjects = gradeOpenHelper.retrieveAllGrades();
//
//        for( int i = 0 ; i < gradeObjects.size(); i++)
//        {
//            Log.d("Dtext", gradeObjects.get(i).getName());
//        }

        studentOpenHelper.deleteAndCreateTable();
        objects = studentOpenHelper.retrieveAllStudents();

        setControl();
        Log.d("text","Hello");
        for( int i = 0 ; i < objects.size() ; i++)
        {
            Student student = objects.get(i);
            String message = student.getId() + " " +
                    student.getFamilyName() + " " +
                    student.getFirstName() + " " +
                    student.getGender() + " " +
                    student.getBirthday() + " " +
                    student.getGradeId();
            Log.d("text", message );
        }

        setEvent();
    }

    private void setControl()
    {
        listView = findViewById(R.id.classroomListView);
    }

    /**
     * this function is used to create default records in order to check
     * classroomListViewModel works properly or not ?
     * */
    private void initiativeDataExample()
    {
        /*Step 1*/
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date today = new Date();
        String birthday = simpleDateFormat.format(today);

        /*Step 2*/
        Student student1 = new Student("Nguyen Thanh", "Phong", birthday);
        Student student2 = new Student("Nguyen Van", "Chung" , birthday);
        Student student3 = new Student("Nguyen Dang", "Hau", birthday);

        /*Step 3*/
        objects.add(student1);
        objects.add(student2);
        objects.add(student3);
    }

    private void setEvent()
    {
        //initiativeDataExample();
        listViewModel = new ClassroomListViewModel(this, R.layout.activity_classroom_element, objects);
        listView.setAdapter(listViewModel);
    }
}