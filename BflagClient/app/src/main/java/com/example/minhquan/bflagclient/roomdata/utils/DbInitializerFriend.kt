package com.example.minhquan.bflagclient.roomdata.utils

import android.os.AsyncTask
import android.util.Log
import com.example.minhquan.bflagclient.roomdata.database.BflagDatabase

import com.example.minhquan.bflagclient.roomdata.entity.Friend


object DbInitializerFriend {

    private val TAG = DbInitializerFriend::class.java.name
    lateinit var type: String

    /**
     * Rock Lee 26 07 2018
     * fun insert delete query Friend
     */


    fun insertFriendAysnc(db: BflagDatabase, friend: Friend) {
        type = "insert"
        val task = PopulateDbAsync(type, db, friend)
        task.execute()
    }

    fun getFriendUser(db: BflagDatabase, mEmailUser: String?) : List<Friend> {
        return db.friendDao().findFriendForUser(mEmailUser!!)
    }

    fun deleteFriendAysnc(db: BflagDatabase, friend: Friend) {
        type = "delete"
        val task = PopulateDbAsync(type, db, friend)
        task.execute()
    }

    fun deleteAllFriendAysnc(db: BflagDatabase) {
        type = "deleteall"
        val task = PopulateDbAsync(type, db, null)
        task.execute()
    }

    private class PopulateDbAsync internal constructor(
            val type: String, private val mDb: BflagDatabase, val mFriend: Friend?) : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void): Void? {
            when (type) {
                "insert" -> addFriend(mDb, mFriend!!)
                "delete" -> deleteFriend(mDb, mFriend!!)
                "deleteall" -> deleteAll(mDb)
            }

            return null
        }
    }


    fun getFriend(db: BflagDatabase): List<Friend> {
        val friendList = db.friendDao().all
        friendList.forEach {
            Log.d(DbInitializerFriend.TAG, "Rows : " + it.email)
        }
        Log.d(DbInitializerFriend.TAG, "Rows count: " + friendList.size)
        return friendList
    }

    private fun addFriend(db: BflagDatabase, friend: Friend) {
        db.friendDao().insertAll(friend)
        getFriend(db)
    }

    private fun deleteFriend(db: BflagDatabase, friend: Friend) {
        db.friendDao().delete(friend)
        getFriend(db)
    }

    private fun deleteAll(db: BflagDatabase) {
        db.friendDao().deleteAll()
        getFriend(db)
    }

}
