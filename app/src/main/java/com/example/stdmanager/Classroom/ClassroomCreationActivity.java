package com.example.stdmanager.Classroom;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;


import com.example.stdmanager.DB.StudentOpenHelper;
import com.example.stdmanager.R;
import com.example.stdmanager.listViewModels.ClassroomListViewModel;
import com.example.stdmanager.models.Session;
import com.example.stdmanager.models.Student;

import java.util.Calendar;

public class ClassroomCreationActivity extends AppCompatActivity {

    Session session;

    EditText familyName, firstName;
    RadioButton male, female;
    Button buttonBirthday, buttonConfirm;


    private final Calendar cal = Calendar.getInstance();
    private final int year = cal.get(Calendar.YEAR);
    private final int month = cal.get(Calendar.MONTH) + 1;
    private final int day = cal.get(Calendar.DAY_OF_MONTH);


    StudentOpenHelper studentOpenHelper = new StudentOpenHelper(ClassroomCreationActivity.this);
    ClassroomListViewModel listViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_creation);
        session = new Session(ClassroomCreationActivity.this);
        setControl();
        setEvent();

        String today = day + "/" + month + "/" + year;
        buttonBirthday.setText(today);
    }

    private void setControl()
    {
        buttonBirthday = findViewById(R.id.classroomCreationButtonBirthday);
        buttonConfirm = findViewById(R.id.classroomCreationButtonConfirm);

        familyName = findViewById(R.id.classroomCreationFamilyName);
        firstName = findViewById(R.id.classroomCreationFirstName);

        male = findViewById(R.id.classroomCreationRadioButtonMale);
        female = findViewById(R.id.classroomCreationRadioButtonFemale);
    }


    public void openDatePicker(View view)
    {
        /*Step 1*/
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String birthday = day + "/" + month + "/" + year;
                buttonBirthday.setText(birthday);
            }
        };

        /*Step 2*/
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;

        /*Step 3*/
        DatePickerDialog datePicker = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePicker.show();
    }

    private void setEvent()
    {
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int gender = male.isChecked() ? 0 : 1;
                int gradeId = Integer.parseInt( session.get("gradeId"));

                Student student = new Student();
                student.setFamilyName( familyName.getText().toString() );
                student.setFirstName( firstName.getText().toString() );
                student.setGender(gender);
                student.setGradeId(gradeId);

                ClassroomActivity.getmInstanceActivity().createStudent(student);

                finish();
            }
        });
    }
}