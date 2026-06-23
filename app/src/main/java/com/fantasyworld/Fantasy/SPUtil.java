package com.fantasyworld.Fantasy;

import android.content.Context;
import android.content.SharedPreferences;

// 本地存储工具类：存登录状态、用户信息
public class SPUtil {
    private static final String NAME = "user_info";
    private static SharedPreferences sp;

    // 初始化
    private static SharedPreferences getSP(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        }
        return sp;
    }

    // 存字符串
    public static void putString(Context context, String key, String value) {
        getSP(context).edit().putString(key, value).apply();
    }

    // 取字符串
    public static String getString(Context context, String key) {
        return getSP(context).getString(key, "");
    }

    // 存登录状态
    public static void putLogin(Context context, boolean isLogin) {
        getSP(context).edit().putBoolean("is_login", isLogin).apply();
    }

    // 判断是否登录
    public static boolean isLogin(Context context) {
        return getSP(context).getBoolean("is_login", false);
    }

    // 退出登录：清空数据
    public static void clear(Context context) {
        getSP(context).edit().clear().apply();
    }
}