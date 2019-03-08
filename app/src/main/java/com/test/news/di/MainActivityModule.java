package com.test.news.di;

import com.test.news.view.ui.NewsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract NewsActivity contributeMainActivity();
}
