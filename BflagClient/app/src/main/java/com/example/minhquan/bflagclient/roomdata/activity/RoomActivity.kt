package com.example.minhquan.bflagclient.roomdata.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.roomdata.database.BflagDatabase
import com.example.minhquan.bflagclient.roomdata.entity.*
import com.example.minhquan.bflagclient.roomdata.utils.*
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
        val friendInRoom1 = FriendInRoom()
        demoCreateListFriendInRoom(friendInRoom1)
        val chat1 = Chat()
        demoCreateChatInRoom(chat1)



        btn_insert.setOnClickListener {
            DbInitializerUser.insertUserAysnc(BflagDatabase.getDatabase(this),user1)
            //DbInitializerFriend.insertFriendAysnc(BflagDatabase.getDatabase(this), friend1)
            //DbInitializerRoomChat.insertRoomChatAysnc(BflagDatabase.getDatabase(this), roomchat1)
            //DbInitializerFriendsInRoom.insertFriendAysnc(BflagDatabase.getDatabase(this), friendInRoom1)
            //DbInitializerChat.insertChatAysnc(BflagDatabase.getDatabase(this),chat1)
        }

        btn_view.setOnClickListener {
            val chatlist = DbInitializerChat.getChatInRoom(BflagDatabase.getDatabase(this), 1)
            if (chatlist.isEmpty())
                txt_roomdata.text = null
            else
                txt_roomdata.text =
                        "roomId: ${chatlist[0].roomid}\n " +
                        "emailUser: ${chatlist[0].email}\n "
        }

        btn_delete.setOnClickListener {
            //DbInitializerUser.deleteUserAysnc(BflagDatabase.getDatabase(this), user1)
            //DbInitializerFriend.deleteAllFriendAysnc(FriendDatabase.getFriendDatabase(this))

        }

    }

    private fun demoCreateListUser(user1: User) {
        user1.email = "a@example.com"
        user1.password = "123456"
        user1.username = "Test"
        user1.firstname = "A"
    }

    private fun demoCreateListFriend(friend1: Friend) {
        friend1.email = "friend3@example.com"
        friend1.name = "Friend3"
        friend1.emailuser = "a@example.com"
    }

    private fun demoCreateListRoomChat(roomChat1: RoomChat) {
        roomChat1.id = 2
        roomChat1.emailuser = "a@example.com"
    }

    private fun demoCreateListFriendInRoom(friendInRoom: FriendInRoom) {
        friendInRoom.email = "z@example.com"
        friendInRoom.roomid = 2
        friendInRoom.username = "Z"
    }

    private fun demoCreateChatInRoom(chat: Chat) {
        chat.roomid = 2
        chat.email = "z@example.com"
        chat.username = "Z"
        chat.content = "hello world"
    }


    override fun onDestroy() {
        BflagDatabase.destroyInstance()
        super.onDestroy()
    }
}