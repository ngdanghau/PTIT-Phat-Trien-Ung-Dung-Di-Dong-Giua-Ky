package com.example.stdmanager.Score;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.stdmanager.DB.GradeOpenHelper;
import com.example.stdmanager.DB.ScoreDBHelper;
import com.example.stdmanager.DB.StudentOpenHelper;
import com.example.stdmanager.R;
import com.example.stdmanager.listViewModels.ScoreStudentAdapter;
import com.example.stdmanager.models.Score;
import com.example.stdmanager.models.ScoreInfo;
import com.example.stdmanager.models.Session;
import com.example.stdmanager.models.Student;
import com.example.stdmanager.models.Subject;

import java.util.ArrayList;

public class ScoreStudentActivity extends AppCompatActivity {

    Session session;

    ListView listView;
    Subject subject;
    ArrayList<Score> objects = new ArrayList<>();
    ArrayList<ScoreInfo> scores = new ArrayList<>();
    ScoreStudentAdapter listViewModel;

    GradeOpenHelper gradeOpenHelper = new GradeOpenHelper(this);
    ScoreDBHelper scoreDBHelper = new ScoreDBHelper(this);
    StudentOpenHelper studentDB = new StudentOpenHelper(this);
    TextView tvSubject;
    SearchView scoreSearch;
    private ImageView btnEdit;
    String teacher;
    String grade;
    AppCompatButton buttonCreation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new Session(ScoreStudentActivity.this);
        teacher = session.get("teacherId");
        grade = gradeOpenHelper.retriveIdByTeachId(teacher);
        setContentView(R.layout.activity_score_student);
        subject = (Subject) getIntent().getSerializableExtra("subject");

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        objects = scoreDBHelper.getAll();
        setControl();
        setEvent();
        inItSearchWidgets();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setControl();
        setEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void addStudentScore()
    {
        ArrayList<Student> students = studentDB.getStudentInGrade(grade);
        for(Student student: students)
        {
            ArrayList<Score> sc = scoreDBHelper.getStudentAndSubject(String.valueOf(student.getId()), String.valueOf(subject.getMaMH()));
            if(sc.size() >0 ) continue;;
            Score score = new Score(student.getId(), subject.getMaMH(), 0);
            scoreDBHelper.add(score);
        }
    }
    private ArrayList<ScoreInfo> getData()
    {
        ArrayList<ScoreInfo> scores = new ArrayList<>();
        ArrayList<Student> students = studentDB.getStudentInGrade(grade);
        for(Student student: students)
        {
            ArrayList<Score> sc = scoreDBHelper.getStudentAndSubject(String.valueOf(student.getId()), String.valueOf(subject.getMaMH()));
            if(sc.size() <= 0)
            {
                return null;
            }
            Score i = sc.get(0);
            ScoreInfo sci = new ScoreInfo(i.getMaHS(), i.getMaMH(), i.getDiem(),
                    student.getFamilyName() + " " + student.getFirstName(),
                    subject.getTenMH());
            scores.add(sci);
        }
        return scores;
    }

    @SuppressLint("SetTextI18n")
    private void setControl()
    {
        listView = findViewById(R.id.score_student_list_view);
        scoreSearch = findViewById(R.id.scoreStudentSearch);
        tvSubject = findViewById(R.id.score_student_subject_name);
        tvSubject.setText("Môn học: " + subject.getTenMH());
    }
    private void setEvent()
    {
        addStudentScore();
        try {
            scores = getData();
            scores.size();
            listViewModel = new ScoreStudentAdapter(this, R.layout.activity_score_student_element, scores);
            listView.setAdapter(listViewModel);
        }catch (NullPointerException ex)
        {
            finish();
            Log.e("ScoreStudentActivity", "lỗi lấy danh sách sinh viên bị rỗng");
        }

    }

    private void inItSearchWidgets(){
        scoreSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<ScoreInfo> filteredScore = new ArrayList<ScoreInfo>();

                for (ScoreInfo score: scores) {
                    if(score.getStudentFullName().toLowerCase().trim().contains(s.toLowerCase().trim()))
                    {
                        filteredScore.add(score);
                    }

                }
                setFilteredSubject(filteredScore);
                return false;
            }
        });
    }

    private void setFilteredSubject(ArrayList<ScoreInfo> filtered)
    {
        ScoreStudentAdapter scoreAdapter = new ScoreStudentAdapter(this, R.layout.activity_score_student_element, filtered);
        listView.setAdapter(scoreAdapter);
    }



}
