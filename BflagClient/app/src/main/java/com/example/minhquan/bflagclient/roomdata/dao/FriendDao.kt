package com.example.minhquan.bflagclient.roomdata.dao

import android.arch.persistence.room.*
import com.example.minhquan.bflagclient.roomdata.entity.Friend
import com.example.minhquan.bflagclient.roomdata.entity.User


@Dao
interface FriendDao {

    @get:Query("SELECT * FROM Friend")
    val all: List<Friend>

    @Query("DELETE FROM Friend")
    fun deleteAll()

    @Delete
    fun delete(friend: Friend)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg friend: Friend)

    @Query("SELECT * FROM friend WHERE email=:emailUser")
    fun findFriendForUser(emailUser: Int): List<Friend>

}
