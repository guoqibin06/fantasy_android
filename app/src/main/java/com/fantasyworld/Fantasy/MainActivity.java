package com.fantasyworld.Fantasy;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private EditText etPhone, etPassword, etNickname;
    private Button btnRegister;
    private OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPhone = findViewById(R.id.et_phone);
        etPassword = findViewById(R.id.et_password);
        etNickname = findViewById(R.id.et_nickname);
        btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = etPhone.getText().toString();
                String password = etPassword.getText().toString();
                String nickname = etNickname.getText().toString();

                if (phone.isEmpty() || password.isEmpty() || nickname.isEmpty()) {
                    Toast.makeText(MainActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                    return;
                }

                register(phone, password, nickname);
            }
        });
    }

    private void register(String phone, String password, String nickname) {
        JSONObject json = new JSONObject();
        try {
            json.put("phone", phone);
            json.put("password", password);
            json.put("nickname", nickname);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(json.toString(), JSON);
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/api/user/register") // 注意：安卓模拟器访问本地后端用10.0.2.2
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "网络错误", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                runOnUiThread(() -> Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show());
            }
        });
    }
}