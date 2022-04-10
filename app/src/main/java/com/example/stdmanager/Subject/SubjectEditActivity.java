package com.example.stdmanager.Subject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.stdmanager.R;
import com.example.stdmanager.models.Student;
import com.example.stdmanager.models.Subject;

public class SubjectEditActivity extends AppCompatActivity {

    EditText txt_tenMH,txt_heSo;
    CheckBox cb_HK1,cb_HK2;
    AppCompatButton btn_save,btn_cancel;
    Subject subject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_edit);

        setControl();
        setEvent();
    }

    public void setControl()
    {
        txt_tenMH = findViewById(R.id.txt_tenMH);
        txt_heSo = findViewById(R.id.txt_HeSo);
        cb_HK1 = findViewById(R.id.cb_HK1);
        cb_HK2 = findViewById(R.id.cb_HK2);
//        END SET

        subject = (Subject) getIntent().getSerializableExtra("Subject");
        txt_tenMH.setText(subject.getTenMH());
        txt_heSo.setText(String.valueOf(subject.getHeSo()));
        if(subject.getHeSo()==1)
            cb_HK1.setChecked(true);
        if(subject.getHeSo()==2)
            cb_HK2.setChecked(true);



    }

    public void setEvent()
    {


        cb_HK1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cb_HK2.isChecked()&&cb_HK1.isChecked())
                    cb_HK2.setChecked(false);
            }
        });
        cb_HK2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cb_HK1.isChecked()&&cb_HK2.isChecked())
                    cb_HK1.setChecked(false);
            }
        });
    }
}