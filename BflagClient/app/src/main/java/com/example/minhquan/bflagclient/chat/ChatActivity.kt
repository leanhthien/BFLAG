package com.example.minhquan.bflagclient.chat

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.adapter.PagerAdapterChatRoom
import kotlinx.android.synthetic.main.activity_chat.*
import com.example.minhquan.bflagclient.adapter.RoomAdapter
import com.example.minhquan.bflagclient.home.HomeActivity
import com.example.minhquan.bflagclient.model.Room

class ChatActivity : FragmentActivity() {

    private lateinit var listener: ChatContract.Listener
    private lateinit var roomAdapter: RoomAdapter
    private lateinit var pagerAdapterRoom: PagerAdapterChatRoom
    private lateinit var listRooms : List<Room>
    private lateinit var listRoomIds: List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val bundle = intent.getBundleExtra("roomBundle")

        listRooms = bundle.getParcelableArrayList("listRooms")
        listRoomIds = listRooms.map { it -> it.id!! }



        setupView()
    }

    private fun setupView() {

        // Set up adapter for list item_group chat room on top
        roomAdapter = RoomAdapter(this)
        roomAdapter.setData(listRooms)
        rv_friend.adapter = roomAdapter

        // Set up Pager chat room
        pagerAdapterRoom = PagerAdapterChatRoom(supportFragmentManager)
        pagerAdapterRoom.setFragment(listRoomIds)
        vpg_chat_friend.adapter = pagerAdapterRoom

        img_back.setOnClickListener {
            onBackPressed()
        }

        img_more.setOnClickListener {
            listener.onOpenSetting()
        }
    }

    fun setListener(listener: ChatContract.Listener) {
        this.listener = listener
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

}
