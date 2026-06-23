package com.fantasyworld.Fantasy;

import okhttp3.*;

// 网络请求工具类：全APP共用
public class HttpUtil {
    private static OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public static final String BASE_URL = "http://10.0.2.2:8080"; // 后端地址

    // 发送Post请求
    public static void post(String url, String json, Callback callback) {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    // 发送Get请求
    public static void get(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(BASE_URL + url)
                .get()
                .build();
        client.newCall(request).enqueue(callback);
    }
}