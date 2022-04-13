package com.example.stdmanager.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.stdmanager.R;

public class SettingsAccountActivity extends AppCompatActivity {

    LinearLayout familyName;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_account);
        familyName = findViewById(R.id.linearLayoutName);

        familyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingsAccountActivity.this, "YOOO!", Toast.LENGTH_LONG).show();
            }
        });
    }
}