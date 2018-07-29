package com.example.minhquan.bflagclient.roomdata.utils

import android.os.AsyncTask
import android.util.Log
import com.example.minhquan.bflagclient.roomdata.database.BflagDatabase

import com.example.minhquan.bflagclient.roomdata.entity.FriendInRoom


object DbInitializerFriendsInRoom {

    private val TAG = DbInitializerFriendsInRoom::class.java.name
    lateinit var type: String

    /**
     * Rock Lee 29 07 2018
     * fun insert delete query Friend In Room Chat
     */


    fun insertFriendAysnc(db: BflagDatabase, friendInRoom: FriendInRoom) {
        type = "insert"
        val task = PopulateDbAsync(type, db, friendInRoom)
        task.execute()
    }

    fun getFriendInRoom(db: BflagDatabase, mRoomId: Int?) : List<FriendInRoom> {
        return db.friendInRoomDao().getFriendForRoom(mRoomId)
    }

    fun deleteFriendAysnc(db: BflagDatabase, friendInRoom: FriendInRoom) {
        type = "delete"
        val task = PopulateDbAsync(type, db, friendInRoom)
        task.execute()
    }

    fun deleteAllFriendAysnc(db: BflagDatabase) {
        type = "deleteall"
        val task = PopulateDbAsync(type, db, null)
        task.execute()
    }

    private class PopulateDbAsync internal constructor(
            val type: String, private val mDb: BflagDatabase, val mFriendInRoom: FriendInRoom?) : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void): Void? {
            when (type) {
                "insert" -> addFriend(mDb, mFriendInRoom!!)
                "delete" -> deleteFriend(mDb, mFriendInRoom!!)
                "deleteall" -> deleteAll(mDb)
            }

            return null
        }
    }


    fun getFriend(db: BflagDatabase): List<FriendInRoom> {
        val friendList = db.friendInRoomDao().all
        friendList.forEach {
            Log.d(TAG, "Rows : " + it.email)
        }
        Log.d(TAG, "Rows count: " + friendList.size)
        return friendList
    }

    private fun addFriend(db: BflagDatabase, friendInRoom: FriendInRoom) {
        db.friendInRoomDao().insertAll(friendInRoom)
        getFriend(db)
    }

    private fun deleteFriend(db: BflagDatabase, friendInRoom: FriendInRoom) {
        db.friendInRoomDao().delete(friendInRoom)
        getFriend(db)
    }

    private fun deleteAll(db: BflagDatabase) {
        db.friendInRoomDao().deleteAll()
        getFriend(db)
    }

}
