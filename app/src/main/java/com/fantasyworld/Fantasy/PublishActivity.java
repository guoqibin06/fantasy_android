package com.fantasyworld.Fantasy;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.json.JSONObject;
import java.io.IOException;

public class PublishActivity extends AppCompatActivity {
    private EditText etContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        etContent = findViewById(R.id.et_content);

        findViewById(R.id.btn_submit).setOnClickListener(v -> {
            String content = etContent.getText().toString().trim();
            if (content.isEmpty()) {
                Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
                return;
            }
            publish(content);
        });
    }

    private void publish(String content) {
        try {
            JSONObject json = new JSONObject();
            json.put("content", content);
            json.put("nickname", SPUtil.getString(this, "nickname"));

            HttpUtil.post("/api/idea/publish", json.toString(), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() ->
                            Toast.makeText(PublishActivity.this, "网络错误："+e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(() -> {
                        Toast.makeText(PublishActivity.this, "发布成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "内容格式错误", Toast.LENGTH_SHORT).show();
        }
    }
}