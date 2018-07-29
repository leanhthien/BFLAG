package com.example.minhquan.bflagclient.roomdata.database

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.migration.Migration
import android.content.Context
import com.example.minhquan.bflagclient.roomdata.dao.*

import com.example.minhquan.bflagclient.roomdata.entity.*
import io.reactivex.internal.operators.maybe.MaybeIgnoreElement

@Database(entities = [(User::class), (Friend::class), (RoomChat::class), (FriendInRoom::class), (Chat::class)], version = 1)
abstract class BflagDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun friendDao(): FriendDao
    abstract fun roomChatDao(): RoomChatDao
    abstract fun friendInRoomDao(): FriendInRoomDao
    abstract fun chatDao(): ChatDao

    companion object {

        private var INSTANCE: BflagDatabase? = null

        fun getDatabase(context: Context): BflagDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, BflagDatabase::class.java, "bflag_database")
                        // allow queries on the main thread.
                        // Don't do this on a real app! See PersistenceBasicSample for an example.
                        //.addMigrations(MIGRATION_1_2)
                        .allowMainThreadQueries()
                        .build()
            }
            INSTANCE!!.close()//........................................save data in device file explorer ------->
            return INSTANCE as BflagDatabase
        }



        fun destroyInstance() {
            INSTANCE = null
        }
    }
}