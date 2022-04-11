package com.example.stdmanager.Subject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.stdmanager.Classroom.ClassroomActivity;
import com.example.stdmanager.DB.GradeOpenHelper;
import com.example.stdmanager.DB.StudentOpenHelper;
import com.example.stdmanager.DB.SubjectDBHelper;
import com.example.stdmanager.R;
import com.example.stdmanager.listViewModels.ClassroomListViewModel;
import com.example.stdmanager.listViewModels.SubjectAdapter;
import com.example.stdmanager.models.Grade;
import com.example.stdmanager.models.Session;
import com.example.stdmanager.models.Student;
import com.example.stdmanager.models.Subject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class SubjectActivity extends AppCompatActivity {

    public static WeakReference<SubjectActivity> weakActivity;
    Session session;

    ListView listView;
    ArrayList<Subject> objects = new ArrayList<>();
    SubjectAdapter listViewModel;
    SubjectDBHelper subjectDB = new SubjectDBHelper(this);
    EditText searchBar;


    AppCompatButton buttonCreation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        subjectDB.open();
        weakActivity = new WeakReference<>(SubjectActivity.this);
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
        buttonCreation = findViewById(R.id.subjectButtonCreation);

    }

    public void updateSubject(Subject subject)
    {
        if(subjectDB.update(subject)) {
            for (Subject item: objects){
                if(item.getMaMH() == subject.getMaMH())
                {
                    item.setTenMH(subject.getTenMH());
                    item.setHocKy(subject.getHocKy());
                    item.setHeSo(subject.getHeSo());
                    item.setNamHoc(subject.getNamHoc());
                }
            }
            listViewModel.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(getApplicationContext(),"Xảy ra lỗi",Toast.LENGTH_SHORT).show();

    }

    private void setEvent()
    {
        listViewModel = new SubjectAdapter(this, R.layout.activity_subject_element, objects);
        listView.setAdapter(listViewModel);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    public static SubjectActivity getmInstanceActivity() {
        return weakActivity.get();
    }

}