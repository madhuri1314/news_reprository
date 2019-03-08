package com.test.news.service.repository;

import com.test.news.service.model.NewsModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {
    String MAIN_URL = "https://newsapi.org/v2/";

    @GET("top-headlines?country=us&apiKey=5329fe1fb4f04b058eaf018cc87b9c67")
    Call<NewsModel> getNewsList();

}
