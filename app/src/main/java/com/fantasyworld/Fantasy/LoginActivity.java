package com.fantasyworld.Fantasy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.json.JSONObject;
import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    private EditText etPhone, etPassword;
    private Button btnLogin, btnToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 【第4天自动登录逻辑】打开APP先判断：已经登录直接进首页
        if (SPUtil.isLogin(this)) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            return;
        }

        etPhone = findViewById(R.id.et_login_phone);
        etPassword = findViewById(R.id.et_login_pwd);
        btnLogin = findViewById(R.id.btn_login);
        btnToRegister = findViewById(R.id.btn_to_register);

        // 去注册页面
        btnToRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        });

        // 登录按钮
        btnLogin.setOnClickListener(v -> {
            String phone = etPhone.getText().toString().trim();
            String pwd = etPassword.getText().toString().trim();

            if (phone.isEmpty() || pwd.isEmpty()) {
                Toast.makeText(this, "账号密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            loginRequest(phone, pwd);
        });
    }

    // 对接后端登录接口
    private void loginRequest(String phone, String pwd) {
        try {
            JSONObject json = new JSONObject();
            json.put("phone", phone);
            json.put("password", pwd);

            HttpUtil.post("/api/user/login", json.toString(), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = response.body().string();
                    runOnUiThread(() -> {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

                        // 登录成功，保存信息到本地SP
                        SPUtil.putLogin(LoginActivity.this, true);
                        SPUtil.putString(LoginActivity.this, "phone", phone);

                        // 跳转到首页
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}