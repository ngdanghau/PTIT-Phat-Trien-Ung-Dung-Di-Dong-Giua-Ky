package com.example.stdmanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;

import com.example.stdmanager.Classroom.ClassroomActivity;
import com.example.stdmanager.Statistic.StatisticActivity;
import com.example.stdmanager.models.Teacher;


public class HomeActivity extends AppCompatActivity {

    TextView txtNameGV, txtIDGV;
    ImageButton buttonHomeStatistic,
                buttonHomeClassroom,
                buttonHomeSubject,
                buttonHomeEvent,
                buttonHomeScore,
                buttonHomeAccount,
                buttonHomeMenu;
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

        View topbarView = (View) findViewById(R.id.topBar);
        buttonHomeMenu = topbarView.findViewById(R.id.btnHomeMenu);
    }

    private void loadData(){
        Teacher gv = ((App) this.getApplication()).getTeacher();
        txtNameGV.setText(gv.getName());
        txtIDGV.setText("Mã GV: " + gv.getId());
    }


    @SuppressLint("RestrictedApi")
    private void setEvent(){

        /*Step 1*/
        buttonHomeStatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, StatisticActivity.class);
                startActivity(intent);
            }
        });

        /*Step 2*/
        buttonHomeClassroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ClassroomActivity.class);
                startActivity(intent);
            }
        });

        /*Step 3*/
        MenuBuilder menuBuilder = new MenuBuilder(HomeActivity.this);
        MenuInflater inflater = new MenuInflater(HomeActivity.this);

        inflater.inflate(R.menu.menu_home_sidebar, menuBuilder);

        buttonHomeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 MenuPopupHelper menuElement = new MenuPopupHelper(HomeActivity.this,menuBuilder, view);
                 menuElement.setForceShowIcon(true);

                 menuBuilder.setCallback(new MenuBuilder.Callback() {
                     @SuppressLint("NonConstantResourceId")
                     @Override
                     public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
                         Intent intent;
                         switch (item.getItemId())
                         {
                             case R.id.classroom:
                                 intent = new Intent(HomeActivity.this, ClassroomActivity.class);
                                 startActivity(intent);
                                 return true;
                             case R.id.subject:
                                 return true;
                             case R.id.event:
                                 return true;
                             case R.id.mark:
                                 return true;
                             case R.id.statistics:
                                 return true;
                             case R.id.account:
                                 return true;
                             default:
                                 throw new IllegalStateException("Unexpected value: " + item.getItemId());
                         }
                     }

                     @Override
                     public void onMenuModeChange(@NonNull MenuBuilder menu) {

                     }
                 });

                 menuElement.show();
            }


        });
    }
}