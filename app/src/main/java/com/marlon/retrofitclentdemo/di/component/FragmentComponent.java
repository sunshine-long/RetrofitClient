package com.marlon.retrofitclentdemo.di.component;

import android.app.Activity;

import com.marlon.retrofitclentdemo.di.module.FragmentModule;
import com.marlon.retrofitclentdemo.di.scope.FragmentScope;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();


}
