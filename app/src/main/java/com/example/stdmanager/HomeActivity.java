package com.example.stdmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.stdmanager.models.GiaoVien;

public class HomeActivity extends AppCompatActivity {

    TextView txtNameGV, txtIDGV;
    ImageButton btnStatistic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setControl();
        setEvent();
        loadData();
    }

    private void setControl(){
        btnStatistic = findViewById(R.id.btnStatistic);
        txtNameGV = findViewById(R.id.txtNameGV);
        txtIDGV = findViewById(R.id.txtIDGV);
    }

    private void loadData(){
        GiaoVien gv = ((App) this.getApplication()).getGiaoVien();
        txtNameGV.setText(gv.getName());
        txtIDGV.setText("Mã GV: " + gv.getId());
    }

    private void setEvent(){

        btnStatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, StatisticActivity.class);
                startActivity(intent);
            }
        });
    }
}