package com.test.news.di;

import android.app.Application;

import com.test.news.service.repository.Service;
import com.test.news.service.repository.local.ArticleDao;
import com.test.news.service.repository.local.ArticleDatabase;
import com.test.news.viewmodel.NewsViewModelFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.room.RoomDatabase.JournalMode.TRUNCATE;

@Module(subcomponents = com.test.news.di.ViewModelSubComponent.class)
class AppModule {

    private static final int TIME_OUT = 1;

    @Singleton
    @Provides
    Service provideGithubService(OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(Service.MAIN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Service.class);
    }

    @Singleton
    @Provides
    ViewModelProvider.Factory provideViewModelFactory(
            com.test.news.di.ViewModelSubComponent.Builder viewModelSubComponent) {

        return new NewsViewModelFactory(viewModelSubComponent.build());
    }

    @Provides
    @Singleton
    ArticleDatabase provideArticleDatabase(Application application) {
        return Room.databaseBuilder(application, ArticleDatabase.class, "articles.db").setJournalMode(TRUNCATE).build();
    }

    @Provides
    @Singleton
    ArticleDao provideArticleDao(ArticleDatabase articleDatabase) {
        return articleDatabase.ArticleDao();
    }

    @Provides
    @Singleton
    OkHttpClient provideHttpClient(HttpLoggingInterceptor logger) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.addInterceptor(logger);
        builder.readTimeout(TIME_OUT, TimeUnit.MINUTES);
        builder.writeTimeout(TIME_OUT, TimeUnit.MINUTES);
        builder.connectTimeout(TIME_OUT, TimeUnit.MINUTES);
        return builder.build();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

}
