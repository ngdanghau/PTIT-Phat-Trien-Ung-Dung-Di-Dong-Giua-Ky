package com.example.stdmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.stdmanager.DB.GradeOpenHelper;
import com.example.stdmanager.DB.StudentOpenHelper;
import com.example.stdmanager.DB.SubjectDBHelper;
import com.example.stdmanager.listViewModels.ClassroomListViewModel;
import com.example.stdmanager.listViewModels.SubjectAdapter;
import com.example.stdmanager.models.Grade;
import com.example.stdmanager.models.Session;
import com.example.stdmanager.models.Student;
import com.example.stdmanager.models.Subject;

import java.util.ArrayList;

public class SubjectActivity extends AppCompatActivity {


    Session session;

    ListView listView;
    ArrayList<Subject> objects = new ArrayList<>();
    SubjectAdapter listViewModel;
    SubjectDBHelper subjectDB = new SubjectDBHelper(this);
    EditText searchBar;
    ImageView buttonHome;
    private ImageView btnEdit;

    AppCompatButton buttonCreation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        subjectDB.deleteAndCreateTable();

        /*The command line belows that make sure that keyboard only pops up only if user clicks into EditText */
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

//        step 2


        objects = subjectDB.getAllSubjects();

        /*Step 3*/
        setControl();


        /*Step 4*/
        setEvent();

    }

    private void setControl()
    {
        listView = findViewById(R.id.subjectListView);
        searchBar = findViewById(R.id.subjectSearchBar);
        buttonHome = findViewById(R.id.subjectButtonHome);
        buttonCreation = findViewById(R.id.subjectButtonCreation);
        btnEdit=findViewById(R.id.btn_edit);

    }

    private void setEvent()
    {
        listViewModel = new SubjectAdapter(this, R.layout.activity_subject_element, objects);
        listView.setAdapter(listViewModel);
//        btnEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
 //       });
    }

}