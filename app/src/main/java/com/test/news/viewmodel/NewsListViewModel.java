package com.test.news.viewmodel;

import android.app.Application;

import com.test.news.service.model.Article;
import com.test.news.service.repository.Repository;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class NewsListViewModel extends AndroidViewModel {

    private LiveData<List<Article>> newsListObservable;

    @Inject
    public NewsListViewModel(@NonNull Repository repository, @NonNull Application application) {
        super(application);
        newsListObservable = repository.getNewsList();
    }

    public LiveData<List<Article>> getNewsListObservable() {
        return newsListObservable;
    }

}
