package com.sahil.mvvmnewsapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sahil.mvvmnewsapp.model.Article

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) //onconflict will replace the data if it is already exisits
    suspend fun upsert(article:Article):Long // this returns id that was inserted

    @Query("SELECT * FROM articles") // this sql query select all articles form tabels
    fun getAllarticles():LiveData<List<Article>> // this is not the suspend function because it will returns LiveData
    //whenever the article in te list changes the live data will tell its all subscirbers to changes of that data

    @Delete
    suspend fun deleteArticle(article:Article)
}