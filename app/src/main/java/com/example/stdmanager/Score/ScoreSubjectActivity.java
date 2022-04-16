package com.example.stdmanager.Score;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stdmanager.DB.ScoreDBHelper;
import com.example.stdmanager.DB.SubjectDBHelper;
import com.example.stdmanager.R;
import com.example.stdmanager.listViewModels.ScoreSubjectAdapter;
import com.example.stdmanager.models.Session;
import com.example.stdmanager.models.Subject;

import java.util.ArrayList;

public class ScoreSubjectActivity extends AppCompatActivity {

    Session session;

    ListView listView;
    ArrayList<Subject> objects = new ArrayList<>();
    ScoreSubjectAdapter listViewModel;
    SubjectDBHelper subjectDB = new SubjectDBHelper(this);
    ScoreDBHelper scroreDB = new ScoreDBHelper(this);
    EditText searchBar;
    //    private ImageView btnEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_subject);
        objects = subjectDB.getAllSubjects();
        setControl();
        setEvent();
    }

    private void setControl()
    {
        listView = findViewById(R.id.score_subject_list_view);
        searchBar = findViewById(R.id.score_subject_search_bar);

    }
    private void setEvent()
    {
        listViewModel = new ScoreSubjectAdapter(this, R.layout.activity_score_subject_element, objects);
        listView.setAdapter(listViewModel);
    }
}
