package com.example.stdmanager.Classroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.example.stdmanager.R;
import com.example.stdmanager.models.Student;

import java.util.Calendar;

public class ClassroomUpdateActivity extends AppCompatActivity {

    EditText familyName, firstName, birthday;
    RadioButton male, female;

    ImageButton buttonBirthday;
    AppCompatButton buttonConfirm, buttonCancel;

    private final Calendar cal = Calendar.getInstance();
    private final int year = cal.get(Calendar.YEAR);
    private final int month = cal.get(Calendar.MONTH) + 1;
    private final int day = cal.get(Calendar.DAY_OF_MONTH);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_update);
        setControl();
        setScreen();
        setEvent();
    }

    private void setControl()
    {
        buttonBirthday = findViewById(R.id.classroomUpdateButtonBirthday);
        buttonConfirm = findViewById(R.id.classroomUpdateButtonConfirm);
        buttonCancel = findViewById(R.id.classroomUpdateButtonGoBack);

        familyName = findViewById(R.id.classroomUpdateFamilyName);
        firstName = findViewById(R.id.classroomUpdateFirstName);
        birthday = findViewById(R.id.classroomUpdateBirthday);


        male = findViewById(R.id.classroomUpdateRadioButtonMale);
        female = findViewById(R.id.classroomUpdateRadioButtonFemale);
    }

    private void setScreen()
    {
        /*Step 1*/
        Student student = (Student) getIntent().getSerializableExtra("updatedStudent");



        /*Step 2*/
        familyName.setText(student.getFamilyName());
        firstName.setText(student.getFirstName());

        if (student.getGender() == 0)
        {
            male.setChecked(true);
        }
        else
        {
            female.setChecked(true);
        }

        birthday.setText( student.getBirthday() );
    }

    public void openDatePicker(View view)
    {
        /*Step 1*/
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            String birthdayValue = day + "/" + month + "/" + year;
            birthday.setText(birthdayValue);
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
        /*Step 1*/
        Student student = (Student) getIntent().getSerializableExtra("updatedStudent");

        /*Step 2*/
        Student myStudent = new Student();



        /*Step 3*/
        birthday.setOnClickListener(this::openDatePicker);
        buttonConfirm.setOnClickListener(view ->
        {
            int id = student.getId();
            int gradeId = student.getGradeId();

            String gradeName = student.getGradeName();
            String family = familyName.getText().toString();

            String first = firstName.getText().toString();
            int gender = male.isChecked() ? 0 : 1;
            String birth = birthday.getText().toString();

            myStudent.setId( id );
            myStudent.setFamilyName( family );

            myStudent.setFirstName( first );
            myStudent.setGender( gender );

            myStudent.setBirthday( birth  );
            myStudent.setGradeId( gradeId );
            myStudent.setGradeName( gradeName );

            ClassroomActivity.getmInstanceActivity().updateStudent(myStudent);
            ClassroomIndividualActivity.getmInstanceActivity().updateStudent(myStudent);
        });
        buttonCancel.setOnClickListener(view -> finish());
    }
}