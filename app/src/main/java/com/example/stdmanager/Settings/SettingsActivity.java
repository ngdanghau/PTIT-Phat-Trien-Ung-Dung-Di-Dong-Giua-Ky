package com.example.stdmanager.Settings;

import static com.itextpdf.kernel.pdf.PdfName.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.example.stdmanager.R;

public class SettingsActivity extends TabActivity {

    TabHost tabHost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setControl();
        setEvent();

    }

    /**
     * @author Phong-Kaster
     * find view by id for each component
     *
     * instanciate tab host
     * */
    private void setControl() {
        tabHost = (TabHost) getTabHost();
    }

    /**
     * @author Phong-Kaster
     * find view by id for each component
     *
     * Step 1: instanciate the host
     * Step 2: create tabSpec tai khoai
     * Step 3: create tabSpec ung dung
     * */
    private void setEvent() {
        tabHost.setup();

        TabHost.TabSpec specAccount = tabHost.newTabSpec("Tài khoản");
        Intent accountIntent = new Intent(SettingsActivity.this, SettingsAccountActivity.class);
        specAccount.setIndicator("Tài khoản");
        specAccount.setContent(accountIntent);
        tabHost.addTab(specAccount);

        TabHost.TabSpec spec = tabHost.newTabSpec("Ứng dụng");
        Intent appicationIntent = new Intent(SettingsActivity.this, SettingsApplicationActivity.class);
        spec.setIndicator("Ứng dụng");
        spec.setContent(appicationIntent);
        tabHost.addTab(spec);
    }


}