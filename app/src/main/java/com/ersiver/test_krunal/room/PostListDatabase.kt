package com.ersiver.test_krunal.room


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ersiver.test_krunal.model.PostOfficeModel


@Database(entities = arrayOf(PostOfficeModel::class), version = 1, exportSchema = false)

abstract class PostListDatabase : RoomDatabase(){

    abstract fun getPostListDao():PostDao

    companion object{
        @Volatile
        private var INSTANCE: PostListDatabase? = null

        fun getDatabase(context: Context): PostListDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PostListDatabase::class.java,
                    "post_office_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }

}
