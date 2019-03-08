package com.test.news.service.repository;

import android.util.Log;

import com.test.news.service.model.Article;
import com.test.news.service.model.NewsModel;
import com.test.news.service.repository.local.ArticleDao;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class Repository {
    private Service service;
    private ArticleDao dao;
    
    @Inject
    public Repository(ArticleDao dao, Service service) {
        this.service = service;
        this.dao = dao;
    }

    final MutableLiveData<List<Article>> data = new MutableLiveData<>();

    public LiveData<List<Article>> getNewsList() {


        fetchArticle(dao).observeOn(AndroidSchedulers.mainThread()).subscribe((result) -> {

            if (result != null && result.size() > 0) {
                data.setValue(result);
            }

            service.getNewsList().enqueue(new Callback<NewsModel>() {
                @Override
                public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                    data.setValue(response.body().getArticles());

                    insertArticle(dao, response.body().getArticles()).observeOn(AndroidSchedulers.mainThread()).subscribe((result) -> {
                    }, e -> {
                        Log.d("error", "Error");

                    });
                }


                @Override
                public void onFailure(Call<NewsModel> call, Throwable t) {
                    //data.setValue(null);
                }
            });

        }, e -> {
            Log.d("error", "Error");
        });


        return data;
    }


    public Single<List<Article>> fetchArticle(ArticleDao dao) {
        return createAsyncSingle(() -> dao.getArticle())
                .subscribeOn(Schedulers.io());
    }


    public Single<Integer> insertArticle(ArticleDao dao, List<Article> list) {
        return createAsyncSingle(() -> {
            dao.nukeTable();
            dao.saveArticles(list);
            return 1;
        })
                .subscribeOn(Schedulers.io());
    }


    protected <T> Single<T> createAsyncSingle(final Callable<T> func) {
        return Single.create(emitter -> {
            try {
                T result = func.call();
                if (result != null) {
                    emitter.onSuccess(result);
                } else {
                    emitter.onError(new NullPointerException("Empty result from DB"));
                }
            } catch (Exception ex) {
                Log.e("TAG", "Error operation with DB");
            }
        });
    }

}
