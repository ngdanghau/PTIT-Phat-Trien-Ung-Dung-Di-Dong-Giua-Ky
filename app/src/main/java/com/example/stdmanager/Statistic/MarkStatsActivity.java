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
import com.example.stdmanager.R;
import com.example.stdmanager.models.Statistic;

import java.util.ArrayList;
import java.util.List;

public class MarkStatsActivity extends AppCompatActivity {

    TextView title;
    Statistic item;
    AppCompatButton btnExport;
    AnyChartView anyChartView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_stats);

        item = (Statistic) getIntent().getSerializableExtra("detail");

        setControl();
        setData();
        setEvent();

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
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

    }
}