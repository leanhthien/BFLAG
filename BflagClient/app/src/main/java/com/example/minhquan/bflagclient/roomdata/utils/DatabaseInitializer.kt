package com.example.minhquan.bflagclient.roomdata.utils

import android.os.AsyncTask
import android.util.Log


import com.example.minhquan.bflagclient.roomdata.database.UserDatabase
import com.example.minhquan.bflagclient.roomdata.entity.User

object DatabaseInitializer {

    private val TAG = DatabaseInitializer::class.java.name
    lateinit var type: String

    /**
     * Rock Lee 25 07 2018
     * fun insert delete query User
     */
    fun insertUserAysnc(db: UserDatabase, user: User) {
        type = "insert"
        val task = PopulateDbAsync(type, db, user)
        task.execute()
    }

    fun deleteUserAysnc(db: UserDatabase, user: User) {
        type = "delete"
        val task = PopulateDbAsync(type, db, user)
        task.execute()
    }

    fun deleteAllUserAysnc(db: UserDatabase) {
        type = "deleteall"
        val task = PopulateDbAsync(type, db, null)
        task.execute()
    }

    private class PopulateDbAsync internal constructor(
            val type: String, private val mDb: UserDatabase, val mUser: User?) : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void): Void? {
            when (type) {
                "insert" -> addUser(mDb, mUser!!)
                "delete" -> deleteUser(mDb, mUser!!)
                "deleteall" -> deleteAll(mDb)
            }

            return null
        }

    }

    fun getUser(db: UserDatabase): List<User> {
        val userList = db.userDao().all
        userList.forEach {
            Log.d(DatabaseInitializer.TAG, "Rows : " + it.email)
        }
        Log.d(DatabaseInitializer.TAG, "Rows count: " + userList.size)
        return userList
    }

    private fun addUser(db: UserDatabase, user: User) {
        db.userDao().insertAll(user)
        getUser(db)
    }

    private fun deleteUser(db: UserDatabase, user: User) {
        db.userDao().delete(user)
        getUser(db)
    }

    private fun deleteAll(db: UserDatabase) {
        db.userDao().deleteAll()
        getUser(db)
    }


    /*
    fun populateAsync(db: UserDatabase) {
        val task = PopulateDbAsync(db)
        task.execute()
    }

    fun populateSync(db: UserDatabase) {
        populateWithTestData(db)
    }

    private fun populateWithTestData(db: UserDatabase) {
        val user = User()
        user.firstName = "Ajay"
        user.lastName = "Saini"
        user.age = 25
        addUser(db, user)

        val userList = db.userDao().all
        Log.d(DatabaseInitializer.TAG, "Rows Count: " + userList.size)

    }

    private class PopulateDbAsync internal constructor(private val mDb: UserDatabase) : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void): Void? {
            populateWithTestData(mDb)
            return null
        }

    }*/
}