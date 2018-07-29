package com.example.minhquan.bflagclient.roomdata.utils

import android.os.AsyncTask
import android.util.Log
import com.example.minhquan.bflagclient.roomdata.database.BflagDatabase
import com.example.minhquan.bflagclient.roomdata.entity.Chat


object DbInitializerChat {

    private val TAG = DbInitializerChat::class.java.name
    lateinit var type: String

    /**
     * Rock Lee 29 07 2018
     * fun insert delete query Chat in Room Chat
     */


    fun insertChatAysnc(db: BflagDatabase, chat: Chat) {
        type = "insert"
        val task = PopulateDbAsync(type, db, chat)
        task.execute()
    }
    //.....main
    fun getChatInRoom(db: BflagDatabase, mRoomId: Int?) : List<Chat> {
        return db.chatDao().getChatForRoom(mRoomId)
    }

    fun deleteChatAysnc(db: BflagDatabase, chat: Chat) {
        type = "delete"
        val task = PopulateDbAsync(type, db, chat)
        task.execute()
    }

    fun deleteAllChatAysnc(db: BflagDatabase) {
        type = "deleteall"
        val task = PopulateDbAsync(type, db, null)
        task.execute()
    }

    private class PopulateDbAsync internal constructor(
            val type: String, private val mDb: BflagDatabase, val mchat: Chat?) : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void): Void? {
            when (type) {
                "insert" -> addChat(mDb, mchat!!)
                "delete" -> deleteChat(mDb, mchat!!)
                "deleteall" -> deleteAll(mDb)
            }

            return null
        }
    }


    fun getChat(db: BflagDatabase): List<Chat> {
        val chatList = db.chatDao().all
        chatList.forEach {
            Log.d(TAG, "Rows : " + it.id)
        }
        Log.d(TAG, "Rows count: " + chatList.size)
        return chatList
    }

    private fun addChat(db: BflagDatabase, chat: Chat) {
        db.chatDao().insertAll(chat)
        getChat(db)
    }

    private fun deleteChat(db: BflagDatabase, chat: Chat) {
        db.chatDao().delete(chat)
        getChat(db)
    }

    private fun deleteAll(db: BflagDatabase) {
        db.chatDao().deleteAll()
        getChat(db)
    }

}
