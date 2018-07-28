package com.example.minhquan.bflagclient.roomdata.utils

import android.os.AsyncTask
import android.util.Log
import com.example.minhquan.bflagclient.roomdata.database.BflagDatabase

import com.example.minhquan.bflagclient.roomdata.entity.RoomChat


object DatabaseInitializerRoomChat {

    private val TAG = DatabaseInitializerRoomChat::class.java.name
    lateinit var type: String

    /**
     * Rock Lee 28 07 2018
     * fun insert delete query RoomChat
     */


    fun insertRoomChatAysnc(db: BflagDatabase, roomChat: RoomChat) {
        type = "insert"
        val task = PopulateDbAsync(type, db, roomChat)
        task.execute()
    }
    //using main
    fun findRoomChatUser(db: BflagDatabase, mEmailUser: String?) : List<RoomChat> {
        return db.roomChatDao().findRoomForUser(mEmailUser!!)
    }

    fun deleteRoomChatAysnc(db: BflagDatabase, roomChat: RoomChat) {
        type = "delete"
        val task = PopulateDbAsync(type, db, roomChat)
        task.execute()
    }

    fun deleteAllRoomChatAysnc(db: BflagDatabase) {
        type = "deleteall"
        val task = PopulateDbAsync(type, db, null)
        task.execute()
    }

    private class PopulateDbAsync internal constructor(
            val type: String, private val mDb: BflagDatabase, val mRoomChat: RoomChat?) : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void): Void? {
            when (type) {
                "insert" -> addRoomChat(mDb, mRoomChat!!)
                "delete" -> deleteRoomChat(mDb, mRoomChat!!)
                "deleteall" -> deleteAll(mDb)
            }

            return null
        }
    }



    fun getRoomChat(db: BflagDatabase): List<RoomChat> {
        val roomChatList = db.roomChatDao().all
        roomChatList.forEach {
            Log.d(TAG, "Rows : " + it.id)
        }
        Log.d(TAG, "Rows count: " + roomChatList.size)
        return roomChatList
    }

    private fun addRoomChat(db: BflagDatabase, roomChat: RoomChat) {
        db.roomChatDao().insertAll(roomChat)
        getRoomChat(db)
    }

    private fun deleteRoomChat(db: BflagDatabase, roomChat: RoomChat) {
        db.roomChatDao().delete(roomChat)
        getRoomChat(db)
    }

    private fun deleteAll(db: BflagDatabase) {
        db.roomChatDao().deleteAll()
        getRoomChat(db)
    }

}
