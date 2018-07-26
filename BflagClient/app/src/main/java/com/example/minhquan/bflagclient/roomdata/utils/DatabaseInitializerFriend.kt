package com.example.minhquan.bflagclient.roomdata.utils

import android.os.AsyncTask
import android.util.Log
import com.example.minhquan.bflagclient.roomdata.database.FriendDatabase

import com.example.minhquan.bflagclient.roomdata.entity.Friend


object DatabaseInitializerFriend {

    private val TAG = DatabaseInitializerFriend::class.java.name
    lateinit var type: String

    /**
     * Rock Lee 26 07 2018
     * fun insert delete query Friend
     */
    fun insertFriendAysnc(db: FriendDatabase, friend: Friend) {
        type = "insert"
        val task = PopulateDbAsync(type, db, friend)
        task.execute()
    }

    fun deleteFriendAysnc(db: FriendDatabase, friend: Friend) {
        type = "delete"
        val task = PopulateDbAsync(type, db, friend)
        task.execute()
    }

    fun deleteAllFriendAysnc(db: FriendDatabase) {
        type = "deleteall"
        val task = PopulateDbAsync(type, db, null)
        task.execute()
    }

    private class PopulateDbAsync internal constructor(
            val type: String, private val mDb: FriendDatabase, val mFriend: Friend?) : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void): Void? {
            when (type) {
                "insert" -> addFriend(mDb, mFriend!!)
                "delete" -> deleteFriend(mDb, mFriend!!)
                "deleteall" -> deleteAll(mDb)
            }

            return null
        }
    }

    fun getFriend(db: FriendDatabase): List<Friend> {
        val friendList = db.friendDao().all
        friendList.forEach {
            Log.d(DatabaseInitializerFriend.TAG, "Rows : " + it.email)
        }
        Log.d(DatabaseInitializerFriend.TAG, "Rows count: " + friendList.size)
        return friendList
    }

    private fun addFriend(db: FriendDatabase, friend: Friend) {
        db.friendDao().insertAll(friend)
        getFriend(db)
    }

    private fun deleteFriend(db: FriendDatabase, friend: Friend) {
        db.friendDao().delete(friend)
        getFriend(db)
    }

    private fun deleteAll(db: FriendDatabase) {
        db.friendDao().deleteAll()
        getFriend(db)
    }

}