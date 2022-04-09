package com.example.stdmanager.Statistic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stdmanager.App;
import com.example.stdmanager.R;
import com.example.stdmanager.listViewModels.StatisticListViewModel;
import com.example.stdmanager.models.Statistic;
import com.example.stdmanager.models.Teacher;

import java.util.ArrayList;

public class StatisticActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Statistic> data = new ArrayList<>();
    StatisticListViewModel listViewModel;

    TextView txtNameGV, txtIDGV;
    ImageButton btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        setControl();

        setEvent();

        loadData();
    }

    private void loadData(){
        Teacher gv = ((App) this.getApplication()).getTeacher();
        txtNameGV.setText(gv.getName());
        txtIDGV.setText("Mã GV: " + gv.getId());
    }

    private void setControl(){
        listView = findViewById(R.id.lvListStatistic);
        View topbarView = (View)findViewById(R.id.topBar);
        btnHome = topbarView.findViewById(R.id.btnHome);


        txtNameGV = findViewById(R.id.txtNameGV);
        txtIDGV = findViewById(R.id.txtIDGV);
    }

    private void setEvent(){

        data.add(new Statistic(1,"Xếp loại","Xem thống kê một lớp ở học kỳ nhất định có bao nhiêu học sinh giỏi, khá,... theo bảng điểm đã có "));
        data.add(new Statistic(2,"Phổ điểm tổng kết","Xem phổ điểm của lớp học nào đó trong học kỳ nhất định"));
        data.add(new Statistic(3,"Giới tính","Thống kê giới tính của lớp theo từng học kỳ "));

        listViewModel = new StatisticListViewModel(this, R.layout.activity_statistic_row, data);
        listView.setAdapter(listViewModel);


        btnHome.setOnClickListener(view -> finish());

    }
}