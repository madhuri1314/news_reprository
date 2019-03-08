package com.test.news.view.ui;

import android.os.Bundle;

import com.test.news.R;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class NewsActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        if (savedInstanceState == null) {
            NewsListFragment fragment = new NewsListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, NewsListFragment.TAG).commit();
        }
    }

    public void show(String url) {
        NewsFragment newsFragment = NewsFragment.forProject(url);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("news")
                .replace(R.id.fragment_container,
                        newsFragment, null).commit();
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
