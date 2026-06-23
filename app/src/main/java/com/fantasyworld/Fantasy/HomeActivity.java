package com.fantasyworld.Fantasy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    private Button btnPublish, btnList, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnPublish = findViewById(R.id.btn_publish);
        btnList = findViewById(R.id.btn_list);
        btnLogout = findViewById(R.id.btn_logout);

        // 发布文案
        btnPublish.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, PublishActivity.class));
        });

        // 查看列表
        btnList.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, IdeaListActivity.class));
        });

        // 退出登录
        btnLogout.setOnClickListener(v -> {
            SPUtil.clear(HomeActivity.this);
            Toast.makeText(HomeActivity.this, "退出成功", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        });
    }
}