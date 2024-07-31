package com.example.news360.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.news360.common.DB_NAME
import com.example.news360.data.models.Article

@Database(
    entities = [Article::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ArticleDatabase:RoomDatabase() {
    abstract fun getArticleDao():ArticleDao
    companion object{
        @Volatile  //other thread can see when one thread change the instances
        private var instance:ArticleDatabase? = null
        private val  LOCK = Any()

        operator fun invoke(context:Context) = instance?: synchronized(LOCK){
            instance ?:createDatabase(context).also{ instance=it}
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context,
            ArticleDatabase::class.java,
            DB_NAME,
        ).build()
    }
}