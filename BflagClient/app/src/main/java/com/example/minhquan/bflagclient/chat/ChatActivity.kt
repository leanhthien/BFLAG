package com.example.minhquan.bflagclient.chat

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.adapter.FriendAdapter
import com.example.minhquan.bflagclient.adapter.PagerAdapterChatRoom
import com.example.minhquan.bflagclient.model.Friend
import kotlinx.android.synthetic.main.activity_chat.*
import android.support.design.widget.Snackbar
import android.util.Log
import com.example.minhquan.bflagclient.adapter.RoomAdapter
import com.example.minhquan.bflagclient.model.Room
import com.example.minhquan.bflagclient.utils.*


const val PAGE_LIMIT = 5

class ChatActivity : FragmentActivity(), ChatContract.View {

    private lateinit var presenter: ChatContract.Presenter
    private var count = 0

    private lateinit var friendAdapter: FriendAdapter
    private lateinit var pagerAdapterFriend: PagerAdapterChatRoom
    private lateinit var listFriends : MutableList<Friend>

    private lateinit var roomAdapter: RoomAdapter
    private lateinit var pagerAdapterRoom: PagerAdapterChatRoom
    private var listRooms : MutableList<Room> = mutableListOf()

    private lateinit var listRoomId: List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // TODO: Get bundle from HomeActivity include: room_id, listChatRoom

        ChatPresenter(this)

        setupView()
    }

    private fun setupView() {

        listRoomId = listOf(1,2,3,4,5)

        //listFriends = mutableListOf()
        //createListFriend(listFriends)

        // Set up adapter for list friend chat room on top
        //friendAdapter = FriendAdapter(this)
        //friendAdapter.setData(listFriends)
        //rv_friend.adapter = friendAdapter

        // Set up adapter for list group chat room on top
        roomAdapter = RoomAdapter(this)
        roomAdapter.setData(listRooms.createListRoom())
        rv_friend.adapter = roomAdapter

        // Set up Pager chat room
        pagerAdapterFriend = PagerAdapterChatRoom(supportFragmentManager)
        pagerAdapterFriend.setFragment(listRoomId)
        vpg_chat_friend.adapter = pagerAdapterFriend
        vpg_chat_friend.offscreenPageLimit = PAGE_LIMIT /* save page in viewpager */
    }

    private fun createListFriend(listFriends: MutableList<Friend>) {
        listFriends.add(Friend(null, "Friend1", "data:image/jpeg;base64,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"))
        listFriends.add(Friend(null, "Friend2", "https://i.pinimg.com/736x/bb/16/5c/bb165c8fcecf107962691450d7505dd3--world-cutest-dog-cutest-dogs.jpg"))
        listFriends.add(Friend(null, "Friend3", "https://wallpaper-house.com/data/out/8/wallpaper2you_256339.jpg"))
        listFriends.add(Friend(null, "Friend4", "https://images-eu.ssl-images-amazon.com/images/G/31/IN-hq/2017/img/Pets%20Products/XCM_1070839_Manual_750x475_Dogjpg_PetsHome_ShopByCategory_Dog_Birds._V515025031_.jpg"))
        listFriends.add(Friend(null, "Friend5", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSwMFoeG86Nz19Ve8Cf2FqpdiMd9Q8r6cYS1lgmPKZCxJfvb7kK"))
    }

    private fun MutableList<Room>.createListRoom(): MutableList<Room> {

        add(Room(1,"Hello",null,null))
        add(Room(2,"My",null,null))
        add(Room(3,"Name",null,null))
        add(Room(4,"Is",null,null))
        add(Room(5,"Bflag",null,null))

        return this
    }




    override fun showProgress(isShow: Boolean) {

    }


    override fun setPresenter(presenter: ChatContract.Presenter) {
        this.presenter = presenter
    }

    override fun showError(message: String) {
        Log.e("Error return", message)

        val error = if (message == TIME_OUT || message == NETWORK_ERROR) message else UNKNOWN_ERROR

        count++
        if (count < MAX_RETRY)
            Snackbar.make(this.window.decorView.findViewById(android.R.id.content), error, Snackbar.LENGTH_INDEFINITE)
                    .setAction(RETRY) {

                    }
                    .show()
        else
            Snackbar.make(this.window.decorView.findViewById(android.R.id.content), error, Snackbar.LENGTH_LONG)
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

    /*public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }*/

}
