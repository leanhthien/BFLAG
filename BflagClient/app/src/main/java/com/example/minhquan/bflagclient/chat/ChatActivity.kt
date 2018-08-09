package com.example.minhquan.bflagclient.chat

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.adapter.PagerAdapterChatRoom
import kotlinx.android.synthetic.main.activity_chat.*
import android.support.design.widget.Snackbar
import android.util.Log
import com.example.minhquan.bflagclient.adapter.RoomAdapter
import com.example.minhquan.bflagclient.home.HomeActivity
import com.example.minhquan.bflagclient.model.Room
import com.example.minhquan.bflagclient.utils.*

class ChatActivity : FragmentActivity(), ChatContract.View {

    private lateinit var presenter: ChatContract.Presenter
    private lateinit var listener: ChatContract.Listener
    private lateinit var roomAdapter: RoomAdapter
    private lateinit var pagerAdapterRoom: PagerAdapterChatRoom
    private lateinit var listRooms : List<Room>
    private lateinit var listRoomIds: List<Int>

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val bundle = intent.getBundleExtra("roomBundle")

        listRooms = bundle.getParcelableArrayList("listRooms")
        listRoomIds = listRooms.map { it -> it.id!! }

        ChatPresenter(this)

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

    override fun showProgress(isShow: Boolean) {

    }

    override fun setPresenter(presenter: ChatContract.Presenter) {
        this.presenter = presenter
    }

    override fun showError(message: String) {
        Log.e("Error return", message)

        count++
        if (count < MAX_RETRY)
            Snackbar.make(this.window.decorView.findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE)
                    .setAction(RETRY) {}
                    .show()
        else
            Snackbar.make(this.window.decorView.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                    .show()
    }

    override fun onUnknownError(error: String) {
        showError(error)
    }

    override fun onTimeout() {
        showError(TIME_OUT)
    }

    override fun onNetworkError() {
        showError(NETWORK_ERROR)
    }

    override fun isNetworkConnected(): Boolean {
        return ConnectivityUtil.isConnected(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

}
