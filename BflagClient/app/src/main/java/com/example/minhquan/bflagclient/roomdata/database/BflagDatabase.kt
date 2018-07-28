package com.example.minhquan.bflagclient.roomdata.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.minhquan.bflagclient.roomdata.dao.FriendDao
import com.example.minhquan.bflagclient.roomdata.dao.RoomChatDao

import com.example.minhquan.bflagclient.roomdata.dao.UserDao
import com.example.minhquan.bflagclient.roomdata.entity.Friend
import com.example.minhquan.bflagclient.roomdata.entity.RoomChat
import com.example.minhquan.bflagclient.roomdata.entity.User

@Database(entities = [(User::class), (Friend::class),(RoomChat::class)], version = 1)
abstract class BflagDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun friendDao(): FriendDao
    abstract fun roomChatDao(): RoomChatDao

    companion object {

        private var INSTANCE: BflagDatabase? = null

        fun getDatabase(context: Context): BflagDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, BflagDatabase::class.java, "bflag_database")
                        // allow queries on the main thread.
                        // Don't do this on a real app! See PersistenceBasicSample for an example.
                        .allowMainThreadQueries()
                        .build()
            }
            INSTANCE!!.close()//save
            return INSTANCE as BflagDatabase
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}