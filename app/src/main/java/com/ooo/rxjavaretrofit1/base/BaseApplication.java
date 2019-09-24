package com.ooo.rxjavaretrofit1.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by Dongdd on 2019/9/24 0024 10:11.
 */
public class BaseApplication extends Application {

    public static Context context = null;
    /**
     * 定义全局单例模式的系统对象
     *
     * @return
     */
    public static synchronized Context context() {
        return (Context) context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        BaseApplication.context = getApplicationContext();
    }

}
