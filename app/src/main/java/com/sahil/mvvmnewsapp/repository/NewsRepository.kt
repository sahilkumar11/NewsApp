package com.sahil.mvvmnewsapp.repository

import com.sahil.mvvmnewsapp.api.RetrofitInstance
import com.sahil.mvvmnewsapp.db.ArticleDataBase
import com.sahil.mvvmnewsapp.model.Article

class NewsRepository(
    val db:ArticleDataBase     // for acessing the functions of database
) {

    suspend fun getBreakingNews(countryCode:String , pageNumber:Int) =
        RetrofitInstance.api.getBreakingNews(countryCode,pageNumber) // which calling from from retrofit instance

    // this func call the api search function which calling from retrofit instsnce
    suspend fun searchNews(searchQuery:String , pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery,pageNumber)

    //for adding and deleting items from  db
    suspend fun upsert(article:Article)  = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAllarticles()

    suspend fun  deleteArticle(article:Article) = db.getArticleDao().deleteArticle(article)
}