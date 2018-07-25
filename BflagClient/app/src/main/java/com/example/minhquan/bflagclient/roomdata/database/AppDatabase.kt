package com.example.minhquan.bflagclient.roomdata.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.util.Log

import com.example.minhquan.bflagclient.roomdata.dao.UserDao
import com.example.minhquan.bflagclient.roomdata.entity.User

@Database(entities = arrayOf(User::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        private var INSTANCE : AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, "user-database")
                        // allow queries on the main thread.
                        // Don't do this on a real app! See PersistenceBasicSample for an example.
                        .allowMainThreadQueries()
                        .build()
            }
            return INSTANCE as AppDatabase
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}