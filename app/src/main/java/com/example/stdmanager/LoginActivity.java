package com.example.stdmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stdmanager.DB.GiaoVienDBHelper;
import com.example.stdmanager.models.GiaoVien;

public class LoginActivity extends AppCompatActivity {

    EditText txtUsername, txtPassword;
    Button btnSignIn;
    GiaoVienDBHelper db = new GiaoVienDBHelper(this);
    Boolean isLogin = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkAuth();
        setControl();
        setEvent();
    }

    private void checkAuth(){
        GiaoVien gv = ((App) LoginActivity.this.getApplication()).getGiaoVien();
        if(gv == null) return;
        gotoHome();
    }

    private void setControl(){
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
    }

    private void setEvent(){
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy input
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
//               db.deleteAndCreatTable();

                // kiểm tra input
                if(username.isEmpty()){
                    txtUsername.setError( "Hãy nhập username!" );
                    return;
                } else if(password.isEmpty()){
                    txtPassword.setError( "Hãy nhập password!" );
                    return;
                }
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                // hiện popup
                View popupView = inflater.inflate(R.layout.normal_alert, null);
                TextView msgText = popupView.findViewById(R.id.msgText);
                ImageView iconAlert = popupView.findViewById(R.id.iconAlert);
                Button btnOK = popupView.findViewById(R.id.btnOK);

                // lấy Data từ csdl dựa trên input
                GiaoVien gv = db.getGiaoVien(Integer.parseInt(username));

                // Kiểm tra login
                if(gv == null){
                    isLogin = false;
                    msgText.setText("Tài khoản không tồn tại!");
                    iconAlert.setBackgroundResource(R.drawable.info_icon);
                }else if(!gv.getPassword().equals(password)){
                    isLogin = false;
                    msgText.setText("Sai mật khẩu!");
                    iconAlert.setBackgroundResource(R.drawable.info_icon);
                }else{
                    isLogin = true;
                    // set biến toàn cục
                    ((App) LoginActivity.this.getApplication()).setGiaoVien(gv);

                    // Set Control
                    msgText.setText("Đăng nhập thành công!");
                    iconAlert.setBackgroundResource(R.drawable.check_icon);
                }

                // tạo popup
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(isLogin){
                            gotoHome();
                        }
                        popupWindow.dismiss();
                    }
                });


            }
        });
    }
    private void gotoHome(){
        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
        LoginActivity.this.startActivity(i);
    }
}