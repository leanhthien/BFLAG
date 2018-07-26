package com.example.minhquan.bflagclient.roomdata.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.roomdata.database.FriendDatabase
import com.example.minhquan.bflagclient.roomdata.database.UserDatabase
import com.example.minhquan.bflagclient.roomdata.entity.Friend
import com.example.minhquan.bflagclient.roomdata.entity.User
import com.example.minhquan.bflagclient.roomdata.utils.DatabaseInitializer
import com.example.minhquan.bflagclient.roomdata.utils.DatabaseInitializerFriend
import kotlinx.android.synthetic.main.activity_room.*

class RoomActivity : AppCompatActivity() {

    companion object {
        private val TAG = RoomActivity::class.java.name
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        val user1 = User()
        demoCreateListUser(user1)
        val friend1 = Friend()
        demoCreateListFriend(friend1)

        btn_insert.setOnClickListener {
            //DatabaseInitializer.insertUserAysnc(UserDatabase.getUserDatabase(this), user1)
            DatabaseInitializerFriend.insertFriendAysnc(FriendDatabase.getFriendDatabase(this),friend1)
        }

        btn_view.setOnClickListener {
            val friendlist: List<Friend> = DatabaseInitializerFriend.getFriend(FriendDatabase.getFriendDatabase(this))
            if (friendlist.isEmpty())
                txt_roomdata.text = null
            else
                txt_roomdata.text =
                        "friendName: ${friendlist[0].name}\n " +
                        "email: ${friendlist[0].email}\n "
        }

        btn_delete.setOnClickListener {
            //DatabaseInitializer.deleteUserAysnc(UserDatabase.getUserDatabase(this), user1)
            DatabaseInitializerFriend.deleteAllFriendAysnc(FriendDatabase.getFriendDatabase(this))
        }

    }

    private fun demoCreateListUser(user1: User) {
        user1.email = "a@example.com"
        user1.password = "123456"
        user1.username = "Test"
        user1.firstname = "A"
    }
    private fun demoCreateListFriend(friend1: Friend) {
        friend1.email = "a1@example.com"
        friend1.name = "Test"
    }


    override fun onDestroy() {
        FriendDatabase.destroyInstance()
        super.onDestroy()
    }
}