package com.example.minhquan.bflagclient.roomdata.utils

import android.os.AsyncTask
import android.util.Log


import com.example.minhquan.bflagclient.roomdata.database.AppDatabase
import com.example.minhquan.bflagclient.roomdata.entity.User

object DatabaseInitializer {

    private val TAG = DatabaseInitializer::class.java.name
    lateinit var type: String

    /**
     * Rock Lee 25 07 2018
     * fun insert delete query User
     */
    fun insertUserAysnc(db: AppDatabase, user: User) {
        type = "insert"
        val task = PopulateDbAsync(type, db, user)
        task.execute()
    }

    fun deleteUserAysnc(db: AppDatabase, user: User) {
        type = "delete"
        val task = PopulateDbAsync(type, db, user)
        task.execute()
    }

    fun deleteAllUserAysnc(db: AppDatabase) {
        type = "deleteall"
        val task = PopulateDbAsync(type, db, null)
        task.execute()
    }

    private class PopulateDbAsync internal constructor(
            val type: String, private val mDb: AppDatabase, val mUser: User?) : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void): Void? {
            when (type) {
                "insert" -> addUser(mDb, mUser!!)
                "delete" -> deleteUser(mDb, mUser!!)
                "deleteall" -> deleteAll(mDb)
            }

            return null
        }

    }

    fun getUser(db: AppDatabase): List<User> {
        val userList = db.userDao().all
        userList.forEach {

            Log.d(DatabaseInitializer.TAG, "Rows Count: " + it.uid)
        }
        return userList
    }

    private fun addUser(db: AppDatabase, user: User) {
        db.userDao().insertAll(user)
        getUser(db)
    }

    private fun deleteUser(db: AppDatabase, user: User) {
        db.userDao().delete(user)
        getUser(db)
    }

    private fun deleteAll(db: AppDatabase) {
        db.userDao().deleteAll()
        getUser(db)
    }


    /*
    fun populateAsync(db: AppDatabase) {
        val task = PopulateDbAsync(db)
        task.execute()
    }

    fun populateSync(db: AppDatabase) {
        populateWithTestData(db)
    }

    private fun populateWithTestData(db: AppDatabase) {
        val user = User()
        user.firstName = "Ajay"
        user.lastName = "Saini"
        user.age = 25
        addUser(db, user)

        val userList = db.userDao().all
        Log.d(DatabaseInitializer.TAG, "Rows Count: " + userList.size)

    }

    private class PopulateDbAsync internal constructor(private val mDb: AppDatabase) : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void): Void? {
            populateWithTestData(mDb)
            return null
        }

    }*/
}