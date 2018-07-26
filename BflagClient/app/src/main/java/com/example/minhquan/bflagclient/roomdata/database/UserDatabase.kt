package com.example.minhquan.bflagclient.roomdata.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

import com.example.minhquan.bflagclient.roomdata.dao.UserDao
import com.example.minhquan.bflagclient.roomdata.entity.User

@Database(entities = arrayOf(User::class), version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        private var INSTANCE : UserDatabase? = null

        fun getUserDatabase(context: Context): UserDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, UserDatabase::class.java, "user-database")
                        // allow queries on the main thread.
                        // Don't do this on a real app! See PersistenceBasicSample for an example.
                        .allowMainThreadQueries()
                        .build()
            }
            return INSTANCE as UserDatabase
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}