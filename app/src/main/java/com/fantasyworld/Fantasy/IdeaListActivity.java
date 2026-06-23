package com.fantasyworld.Fantasy;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class IdeaListActivity extends AppCompatActivity {
    private TextView tvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_list);
        tvList = findViewById(R.id.tv_list);
        getList(); // 加载列表
    }

    // 获取文案列表
    private void getList() {
        HttpUtil.get("/api/idea/list", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(IdeaListActivity.this, "加载失败", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                runOnUiThread(() -> {
                    try {
                        JSONArray array = new JSONArray(data);
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            sb.append("用户：").append(obj.getString("nickname")).append("\n");
                            sb.append("幻想：").append(obj.getString("content")).append("\n\n");
                        }
                        tvList.setText(sb.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }
}