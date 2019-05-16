package com.marlon.retrofitclentdemo.di.component;

import android.app.Activity;

import com.marlon.retrofitclentdemo.MvpMainActivity;
import com.marlon.retrofitclentdemo.di.module.ActivityModule;
import com.marlon.retrofitclentdemo.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(MvpMainActivity mainActivity);


}
