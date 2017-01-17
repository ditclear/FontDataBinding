package com.ditclear.sample;

import android.app.Application;

/**
 * 页面描述：App
 * <p>
 * Created by ditclear on 2017/1/17.
 */

public class FontApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }

    public static FontApp instance;
    public static Application getInstance(){
        return instance;
    }
}
