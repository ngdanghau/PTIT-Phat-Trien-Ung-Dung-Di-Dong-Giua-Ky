package com.example.stdmanager.Statistic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.stdmanager.DB.GradeOpenHelper;
import com.example.stdmanager.DB.StudentOpenHelper;
import com.example.stdmanager.R;
import com.example.stdmanager.models.ReportTotal;
import com.example.stdmanager.models.Statistic;

import java.util.ArrayList;
import java.util.List;

public class GenderStatsActivity extends AppCompatActivity {

    TextView title;
    Statistic item;
    AppCompatButton btnExport;

    AnyChartView anyChartView;

    StudentOpenHelper studentOpenHelper = new StudentOpenHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender_stats);

        item = (Statistic) getIntent().getSerializableExtra("detail");

        setControl();
        setData();
        setEvent();

        setupPieChart();
    }

    private void setData() {
        title.setText("Thống kê " + item.getTitle());
    }

    private void setEvent() {
    }

    private void setControl() {
        title = findViewById(R.id.title);
        btnExport = findViewById(R.id.btnExport);
        anyChartView = findViewById(R.id.any_chart_view);
    }

    private void setupPieChart(){
        Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();
        ArrayList<ReportTotal> listData = studentOpenHelper.countByGender();

        for (int i = 0; i < listData.size(); i++){
            data.add(new ValueDataEntry(listData.get(i).getName(), listData.get(i).getValue()));
        }

        pie.data(data);
        pie.palette(new String[]{"#ffcc80", "#aed581"});
        pie.title(item.getTitle());
        pie.labels().position("outside");

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        anyChartView.setChart(pie);
    }

}