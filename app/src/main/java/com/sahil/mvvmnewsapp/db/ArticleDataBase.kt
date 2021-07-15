package com.sahil.mvvmnewsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sahil.mvvmnewsapp.model.Article


@Database(
    entities = [Article::class],
    version = 1
)
@TypeConverters(Convertors::class)
abstract class ArticleDataBase:RoomDatabase() {

    abstract fun getArticleDao():ArticleDao // it return article dao

    companion object {
        //creating instance of article database
        @Volatile // by this other threats can immediately see when threat changes its instances
        private var instance: ArticleDataBase? = null
        private val LOCK = Any()


        // this func is called whenever we call our database
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDataBase::class.java,
                "article_db.db"
            ).build()
    }
}