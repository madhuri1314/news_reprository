package com.test.news.service.repository.local;

import com.test.news.service.model.Article;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Article.class}, version = 1)
public abstract class ArticleDatabase extends RoomDatabase {
    public abstract ArticleDao ArticleDao();
}
