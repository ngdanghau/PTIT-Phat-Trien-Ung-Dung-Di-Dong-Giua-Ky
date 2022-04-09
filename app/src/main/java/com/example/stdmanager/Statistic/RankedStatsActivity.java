package com.example.stdmanager.Statistic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.widget.TextView;

import com.example.stdmanager.R;
import com.example.stdmanager.models.Statistic;

public class RankedStatsActivity extends AppCompatActivity {

    TextView title;
    Statistic item;
    AppCompatButton btnExport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranked_stats);

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
    }
}