package com.example.android.devbyteviewer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseVideo::class], version = 1)
abstract class VideoDatabase : RoomDatabase() {
    abstract val videoDao: VideoDao

    companion object {
        @Volatile
        private var INSTANCE: VideoDatabase? = null
        fun getInstance(context: Context): VideoDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            VideoDatabase::class.java,
                            "video_database"
                    )
//                            .fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }
                return instance
            }

        }
    }

}