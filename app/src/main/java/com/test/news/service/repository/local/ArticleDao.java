package com.test.news.service.repository.local;

import com.test.news.service.model.Article;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ArticleDao {
    @Query("SELECT * FROM articles")
    List<Article> getArticle();

    @Insert
    void saveArticles(List<Article> article);

    @Query("DELETE FROM articles")
    public void nukeTable();

}
