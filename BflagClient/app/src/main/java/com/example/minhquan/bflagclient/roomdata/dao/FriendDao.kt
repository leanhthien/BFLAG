package com.example.minhquan.bflagclient.roomdata.dao

import android.arch.persistence.room.*
import com.example.minhquan.bflagclient.roomdata.entity.Friend
import com.example.minhquan.bflagclient.roomdata.entity.User


@Dao
interface FriendDao {

    @get:Query("SELECT * FROM friend")
    val all: List<Friend>

    @Query("DELETE FROM friend")
    fun deleteAll()

    @Delete
    fun delete(friend: Friend)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg friend: Friend)

    @Query("SELECT * FROM friend WHERE email_user = :emailUser")
    fun findFriendForUser(emailUser: String): List<Friend>

}
