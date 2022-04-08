package com.example.stdmanager.Classroom;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.stdmanager.DB.StudentOpenHelper;
import com.example.stdmanager.R;
import com.example.stdmanager.models.Student;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class ClassroomExportActivity extends AppCompatActivity {

    ArrayList<Student> objects = new ArrayList<>();
    StudentOpenHelper studentOpenHelper = new StudentOpenHelper(this);
    TableLayout mytable;
    TextView textView1, textView2, textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_export);

        /*Pop up a notice that user must accept request READ | WRITE EXTERNAL STORAGE*/
        ActivityCompat.requestPermissions(ClassroomExportActivity.this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, PackageManager.PERMISSION_GRANTED);

        objects = studentOpenHelper.retrieveAllStudents();


        mytable = findViewById(R.id.myTable);

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

            mytable.addView(row);
        }
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

//    /**
//     * @author Phong-Kaster
//     * this function uses iText library to create and export PDF file: https://kb.itextpdf.com/home/it7kb
//     * */
//    private void createPDFfile() throws FileNotFoundException {
//        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
//        Log.d("filePath", filePath);
//        File file = new File(filePath,"Danhsachlop.pdf");
//        OutputStream outputStream = new FileOutputStream(file);
//
//        PdfWriter writer = new PdfWriter(file);
//        PdfDocument pdfDocument = new PdfDocument(writer);
//        Document document = new Document(pdfDocument);
//
//        Paragraph title = new Paragraph("Danh Sach Lop");
//        document.add(title);
//        document.close();
//        Toast.makeText(ClassroomExportActivity.this, "Xuat PDF file thanh cong !", Toast.LENGTH_LONG).show();
//    }
}