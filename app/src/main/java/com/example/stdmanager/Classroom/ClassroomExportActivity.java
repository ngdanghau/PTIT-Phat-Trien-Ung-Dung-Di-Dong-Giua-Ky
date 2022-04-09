package com.example.stdmanager.Classroom;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import com.example.stdmanager.DB.StudentOpenHelper;
import com.example.stdmanager.R;
import com.example.stdmanager.models.Student;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class ClassroomExportActivity extends AppCompatActivity {

    ArrayList<Student> objects = new ArrayList<>();
    StudentOpenHelper studentOpenHelper = new StudentOpenHelper(this);
    TableLayout table;

    AppCompatButton buttonPhoto, buttonGoBack, buttonPDF;
    LinearLayout linearLayout;

    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_export);

        /*Pop up a notice that user must accept request READ | WRITE EXTERNAL STORAGE*/
        ActivityCompat.requestPermissions(ClassroomExportActivity.this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, PackageManager.PERMISSION_GRANTED);

        setControl();
        setScreen();
        setEvent();



    }

    private void setControl()
    {
        table = findViewById(R.id.classroomTable);
        linearLayout = findViewById(R.id.classroomExportLayout);
        buttonPhoto = findViewById(R.id.classroomExportButtonPhoto);
        buttonGoBack = findViewById(R.id.classroomExportButtonGoBack);
        buttonPDF = findViewById(R.id.classroomExportButtonPDF);
    }

    private void setScreen()
    {
        objects = studentOpenHelper.retrieveAllStudents();

        for( int i = 0 ; i < objects.size() ; i++)
        {
            TableRow row = new TableRow(this);


            TextView tv1 = new TextView(this);
            tv1.setText(objects.get(i).getFamilyName());
            tv1.setPadding(100, 5,5,5);
            tv1.setTextColor(Color.BLACK);
            tv1.setWidth(250);

            TextView tv2 = new TextView(this);
            tv2.setText(objects.get(i).getFirstName());
            tv2.setPadding(100, 5,5,5);
            tv2.setTextColor(Color.BLACK);

            TextView tv3 = new TextView(this);
            String gender = objects.get(i).getGender() == 0 ? "Nam" : "Nữ";
            tv3.setText(gender);
            tv3.setPadding(100, 5,5,5);
            tv3.setTextColor(Color.BLACK);

            TextView tv4 = new TextView(this);
            tv4.setText(objects.get(i).getBirthday());
            tv4.setPadding(150, 5,5,5);
            tv4.setTextColor(Color.BLACK);

            row.addView(tv1);
            row.addView(tv2);
            row.addView(tv3);
            row.addView(tv4);

            table.addView(row);
        }
    }

    private void setEvent()
    {
        buttonPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewGroup viewGroup = table;
                screenshotToPhoto(viewGroup, "result");
                Toast.makeText(ClassroomExportActivity.this, "Xuất hình ảnh thành công ! Vui lòng kiểm tra trong phần bộ nhớ di động", Toast.LENGTH_LONG).show();
            }
        });

        buttonPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ViewGroup viewGroup = table;
//                screenshotToPDF(viewGroup, "result");
                Toast.makeText(ClassroomExportActivity.this, "Tính năng này đang trong giai đoạn phát triển", Toast.LENGTH_LONG).show();
            }
        });

        buttonGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * @author Phong-Kaster
     * This funtion uses bitmap library to capture screen and store the photo into root directory
     * To check, open "View -> Tool Window -> Device File Explorer -> sdCard". We will see the stored photo
     * */
    private static File screenshotToPhoto(View view, String filename) {
        /*Step 1*/
        Date date = new Date();


        CharSequence format = android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", date);
        try {
            /*Step 2*/
            String dirpath = Environment.getExternalStorageDirectory() + "";
            File file = new File(dirpath);
            if (!file.exists()) {
                boolean mkdir = file.mkdir();
            }

            // File name
            String path = dirpath + "/" + filename + "-" + format + ".jpeg";

            /*Step 3*/
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);
            File imageurl = new File(path);

            /*Step 4*/
            FileOutputStream outputStream = new FileOutputStream(imageurl);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);

            /*Step 5*/
            outputStream.flush();
            outputStream.close();
            return imageurl;

        } catch (IOException io) {
            io.printStackTrace();
        }
        return null;
    }


    /**
     * @author Phong-Kaster
     * */
    private static File screenshotToPDF(View view, String filename )
    {
        try {
            // Initialising the directory of storage
            String dirpath = Environment.getExternalStorageDirectory() + "";
            File file = new File(dirpath);
            if (!file.exists()) {
                boolean mkdir = file.mkdir();
            }



            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);


            PdfDocument pdfDocument = new PdfDocument();
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(100, 100, 1).create();

            PdfDocument.Page page = pdfDocument.startPage(pageInfo);

            Canvas canvas = page.getCanvas();
            canvas.drawBitmap(bitmap,0,0,null);
            pdfDocument.finishPage(page);



            // File name
            String path = dirpath + "/" + filename + ".pdf";
            File PDFfile = new File(path);
            FileOutputStream outputStream = new FileOutputStream(PDFfile);

            pdfDocument.writeTo(outputStream);
            pdfDocument.close();
            return PDFfile;

        } catch (Exception io) {
            io.printStackTrace();
        }

        return null;
    }

    private void exportPDFFile()
    {
        PdfDocument pdfDocument = new PdfDocument();
        Point windowSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(windowSize);


        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(windowSize.x, windowSize.y,1 ).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);


        Paint paint = new Paint();


        String content = "Danh sach sinh vien lop \r \n";
        for(int i = 0; i < objects.size(); i++)
        {
            Student element = objects.get(i);
            content += i + ". " + element.getFamilyName() + "\t\t" + element.getFirstName() + "\t\t" + element.getBirthday() +"\r \n";
        }

        int x = 10, y =25;
        page.getCanvas().drawText(content, x, y,paint);
        pdfDocument.finishPage(page);

        String filePath = Environment.getExternalStorageDirectory().getPath() + "/Classroom-PDF-File.pdf";
        File file = new File(filePath);

        try
        {
            FileOutputStream fout = new FileOutputStream(file);
            pdfDocument.writeTo(fout);
            Log.d("fileText", filePath);
            Toast.makeText(ClassroomExportActivity.this, "Xuat file thanh cong: " + filePath, Toast.LENGTH_LONG).show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        pdfDocument.close();
        finish();
    }

}