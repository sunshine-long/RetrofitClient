package com.marlon.retrofitclentdemo.di.component;


import com.marlon.retrofitclentdemo.app.App;
import com.marlon.retrofitclentdemo.di.module.AppModule;
import com.marlon.retrofitclentdemo.di.module.HttpModule;
import com.marlon.retrofitclentdemo.http.ApiService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

    App getContext();  // 提供App的Context

    ApiService retrofitHelper();  //提供http的帮助类

}
