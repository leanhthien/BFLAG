package com.example.minhquan.bflagclient.roomdata.dao

import android.arch.persistence.room.*
import com.example.minhquan.bflagclient.roomdata.entity.User


@Dao
interface UserDao {

    @get:Query("SELECT * FROM user")
    val all: List<User>

    @Query("SELECT * FROM user where first_name LIKE  :firstName AND last_name LIKE :lastName")
    fun findByName(firstName: String, lastName: String): User

    @Query("SELECT COUNT(*) from user")
    fun countUsers(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: User)

    @Query("DELETE FROM user")
    fun deleteAll()

    @Delete
    fun delete(user: User)
}