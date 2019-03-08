package com.test.news.di;

import com.test.news.view.ui.NewsFragment;
import com.test.news.view.ui.NewsListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract NewsFragment contributeProjectFragment();

    @ContributesAndroidInjector
    abstract NewsListFragment contributeProjectListFragment();
}
