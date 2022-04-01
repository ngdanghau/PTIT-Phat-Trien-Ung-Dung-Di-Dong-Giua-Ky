package com.example.stdmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.stdmanager.models.Teacher;

public class HomeActivity extends AppCompatActivity {

    TextView txtNameGV, txtIDGV;
    ImageButton buttonHomeStatistic,
                buttonHomeClassroom,
                buttonHomeSubject,
                buttonHomeEvent,
                buttonHomeScore,
                buttonHomeAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setControl();
        setEvent();
        loadData();
    }

    private void setControl()
    {
        buttonHomeStatistic = findViewById(R.id.buttonHomeStatistic);
        buttonHomeClassroom = findViewById(R.id.buttonHomeClassroom);

        buttonHomeSubject = findViewById(R.id.buttonHomeSubject);
        buttonHomeEvent = findViewById(R.id.buttonHomeEvent);

        buttonHomeScore = findViewById(R.id.buttonHomeScore);
        buttonHomeAccount = findViewById(R.id.buttonHomeAccount);

        txtNameGV = findViewById(R.id.txtNameGV);
        txtIDGV = findViewById(R.id.txtIDGV);
    }

    private void loadData(){
        Teacher gv = ((App) this.getApplication()).getTeacher();
        txtNameGV.setText(gv.getName());
        txtIDGV.setText("Mã GV: " + gv.getId());
    }

    private void setEvent(){

        buttonHomeStatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, StatisticActivity.class);
                startActivity(intent);
            }
        });

        buttonHomeClassroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ClassroomActivity.class);
                startActivity(intent);
            }
        });
    }
}