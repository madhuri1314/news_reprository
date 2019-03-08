package com.test.news.di;

import com.test.news.viewmodel.NewsListViewModel;
import com.test.news.viewmodel.NewsViewModel;

import dagger.Subcomponent;

@Subcomponent
public interface ViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        ViewModelSubComponent build();
    }

    NewsListViewModel projectListViewModel();
    NewsViewModel projectViewModel();
}
