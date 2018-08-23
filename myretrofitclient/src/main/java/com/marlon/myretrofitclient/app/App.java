package com.marlon.myretrofitclient.app;

import android.app.Application;

/**
 * Created by KangLong on 2017/7/10.
 */

public class App extends Application {
    private static App instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
    public static synchronized App getInstance() {
        return instance;
    }

}
