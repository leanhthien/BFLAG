package com.example.minhquan.bflagclient.roomdata.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

import com.example.minhquan.bflagclient.roomdata.dao.FriendDao
import com.example.minhquan.bflagclient.roomdata.entity.Friend

@Database(entities = arrayOf(Friend::class), version = 1)
abstract class FriendDatabase : RoomDatabase() {

    abstract fun friendDao(): FriendDao

    companion object {

        private var INSTANCE : FriendDatabase? = null

        fun getFriendDatabase(context: Context): FriendDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, FriendDatabase::class.java, "friend-database")
                        // allow queries on the main thread.
                        // Don't do this on a real app! See PersistenceBasicSample for an example.
                        .allowMainThreadQueries()
                        .build()
            }
            return INSTANCE as FriendDatabase
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}