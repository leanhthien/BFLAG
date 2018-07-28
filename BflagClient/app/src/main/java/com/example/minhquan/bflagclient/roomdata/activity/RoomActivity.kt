package com.example.minhquan.bflagclient.roomdata.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.roomdata.database.BflagDatabase
import com.example.minhquan.bflagclient.roomdata.entity.Friend
import com.example.minhquan.bflagclient.roomdata.entity.RoomChat
import com.example.minhquan.bflagclient.roomdata.entity.User
import com.example.minhquan.bflagclient.roomdata.utils.DatabaseInitializerFriend
import com.example.minhquan.bflagclient.roomdata.utils.DatabaseInitializerRoomChat
import com.example.minhquan.bflagclient.roomdata.utils.DatabaseInitializerUser
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
        val roomchat1 = RoomChat()
        demoCreateListRoomChat(roomchat1)



        btn_insert.setOnClickListener {
            //DatabaseInitializerUser.insertUserAysnc(BflagDatabase.getDatabase(this),user1)
            DatabaseInitializerFriend.insertFriendAysnc(BflagDatabase.getDatabase(this),friend1)
            //DatabaseInitializerRoomChat.insertRoomChatAysnc(BflagDatabase.getDatabase(this), roomchat1)
        }

        btn_view.setOnClickListener {
            val roomchatlist = DatabaseInitializerRoomChat.findRoomChatUser(BflagDatabase.getDatabase(this), user1.email)
            if (roomchatlist.isEmpty())
                txt_roomdata.text = null
            else
                txt_roomdata.text =
                        "friendName: ${roomchatlist[0].id}\n " +
                        "emailUser: ${roomchatlist[0].emailuser}\n "
        }

        btn_delete.setOnClickListener {
            //DatabaseInitializerUser.deleteUserAysnc(BflagDatabase.getDatabase(this), user1)
            //DatabaseInitializerFriend.deleteAllFriendAysnc(FriendDatabase.getFriendDatabase(this))
        }

    }

    private fun demoCreateListUser(user1: User) {
        user1.email = "a@example.com"
        user1.password = "123456"
        user1.username = "Test"
        user1.firstname = "a"
    }

    private fun demoCreateListFriend(friend1: Friend) {
        friend1.email = "friend4@example.com"
        friend1.name = "Friend4"
        friend1.emailuser = "a@example.com"
    }

    private fun demoCreateListRoomChat(roomChat1: RoomChat) {
        roomChat1.id = 3
        roomChat1.emailuser = "a@example.com"
    }


    override fun onDestroy() {
        BflagDatabase.destroyInstance()
        super.onDestroy()
    }
}