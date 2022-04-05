package com.example.stdmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stdmanager.models.Student;

import java.lang.ref.WeakReference;

public class ClassroomIndividualActivity extends AppCompatActivity {

    public static WeakReference<ClassroomIndividualActivity> weakActivity;

    TextView studentFamilyName, studentFirstName, studentGradeName, studentBirthday, studentGender, contentAlert;
    Button buttonScore, buttonUpdate, buttonDelete, buttonAlertConfirmation, buttonAlertCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_individual);

        weakActivity = new WeakReference<>(ClassroomIndividualActivity.this);
        setControl();

        setScreen();
        setEvent();
    }

    public static ClassroomIndividualActivity getmInstanceActivity() {
        return weakActivity.get();
    }

    private void setControl()
    {
        studentFamilyName = findViewById(R.id.studentFamilyName);
        studentFirstName = findViewById(R.id.studentFirstName);

        studentGradeName = findViewById(R.id.gradeName);
        studentBirthday = findViewById(R.id.birthday);

        buttonScore = findViewById(R.id.individualButtonScore);
        buttonUpdate = findViewById(R.id.individualButtonUpdate);
        buttonDelete = findViewById(R.id.individualButtonDelete);

        studentGender = findViewById(R.id.gender);
    }

    /**
     * @author Phong-Kaster
     * Step 1: retrieve the 'student' object with condition that Student class implements Serializable
     * Step 2: set text for these textView
     * */
    private void setScreen()
    {
        /*Step 1*/
        Student student = (Student) getIntent().getSerializableExtra("student");

        String familyName = student.getFamilyName();
        String firstName = student.getFirstName();
        String birthday = student.getBirthday();
        String gradeName = student.getGradeName();

        /*Step 2*/
        studentFamilyName.setText(familyName);
        studentFirstName.setText(firstName);
        studentBirthday.setText(birthday);
        studentGradeName.setText(gradeName);

        if( student.getGender() == 0 )
        {
            studentGender.setText("Nam");
        }
        else
        {
            studentGender.setText("Nữ");
        }
    }

    private void setEvent()
    {

        Student student = (Student) getIntent().getSerializableExtra("student");


        buttonDelete.setOnClickListener(view -> triggerPopupWindow(view, student));
        buttonUpdate.setOnClickListener(view -> {
            Intent intent = new Intent(ClassroomIndividualActivity.this, ClassroomUpdateActivity.class);
            intent.putExtra("updatedStudent",student );
            startActivity((intent));
        });
    }

    /**
     * @author Phong-Kaster
     * @param view objects are used specifically for drawing content onto the screen of an Android device.
     * @param student is the object who is deleted
     *
     * Step 1: inflate the layout of the popup window
     * Step 2: mapping buttons with their id
     * Step 3: create the popup window
     * Step 4: show the popup window
     * Step 5: dismiss the popup window when touched
     * Step 6: listen event interacts with buttons
     * */
    @SuppressLint("ClickableViewAccessibility")
    private void triggerPopupWindow(View view, Student student)
    {
        /*Step 1*/
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.confirm_alert, null);

        /*Step 2*/
        buttonAlertConfirmation = popupView.findViewById(R.id.btnOK);
        buttonAlertCancel = popupView.findViewById(R.id.btnCancel);
        contentAlert = popupView.findViewById(R.id.msgText);
        contentAlert.setText(R.string.deleteStudent);

        /*Step 3*/
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);


        /*Step 4*/
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        /*Step 5*/
        popupView.setOnTouchListener((v, event) -> {
            popupWindow.dismiss();
            return true;
        });


        /*Step 6*/
        buttonAlertConfirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClassroomActivity.getmInstanceActivity().deleteStudent(student);
                finish();
            }
        });

        buttonAlertCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }


    /**
     * @author Phong-Kaster
     * update information
     * */
    public void updateStudent(Student student)
    {
        studentFamilyName.setText( student.getFamilyName() );
        studentFirstName.setText( student.getFirstName() );
        studentBirthday.setText( student.getBirthday() );
        studentGradeName.setText( student.getGradeName() );

        if( student.getGender() == 0 )
        {
            studentGender.setText("Nam");
        }
        else
        {
            studentGender.setText("Nữ");
        }
    }
}