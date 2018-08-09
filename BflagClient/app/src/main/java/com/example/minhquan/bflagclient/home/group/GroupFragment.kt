package com.example.minhquan.bflagclient.home.group

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.adapter.GroupAdapter
import com.example.minhquan.bflagclient.adapter.SUBSCRIBED_ROOM
import com.example.minhquan.bflagclient.home.HomeActivity
import com.example.minhquan.bflagclient.home.HomeContract
import com.example.minhquan.bflagclient.model.Room
import com.example.minhquan.bflagclient.utils.*
import kotlinx.android.synthetic.main.fragment_group.*


const val EMPTY_ROOM = "There's no room can be shown here"


class GroupFragment : Fragment(), GroupContract.View, HomeContract.Listener {

    private lateinit var presenter: GroupContract.Presenter
    private lateinit var homeActivity: HomeActivity
    private lateinit var token: String
    private lateinit var groupAdapter: GroupAdapter
    private lateinit var listRooms: List<Room>

    var count = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_group,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GroupPresenter(this)
        setupListener()
        setupView()
    }

    private fun setupListener() {
        homeActivity = activity as HomeActivity
        homeActivity.setListener(this)
    }

    private fun setupView() {

        groupAdapter = GroupAdapter(this.context!!, SUBSCRIBED_ROOM)
        rv_room.setHasFixedSize(true)
        rv_room.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        rv_room.adapter = groupAdapter

        val tokenReturn = SharedPreferenceHelper.getInstance(context!!).getToken()

        if (tokenReturn != null) {
            token = tokenReturn
            presenter.startGetSubscribedRooms(token)
        }
    }

    override fun onFinishGetUser() {

        val tokenReturn = SharedPreferenceHelper.getInstance(context!!).getToken()

        if (tokenReturn != null) {
            token = tokenReturn
            presenter.startGetSubscribedRooms(token)
        }
    }

    override fun onGetSubscribedRoomsSuccess(result: List<Room>) {

        if (result.isEmpty()) {
            tv_empty.visibility = View.VISIBLE
            tv_empty.text = EMPTY_ROOM
        }
        else {
            listRooms = result.sort(HOME)

            Log.d("Subscribed room", listRooms.toString())

            tv_empty.visibility = View.GONE
            groupAdapter.setData(listRooms)

        }

    }

    override fun showProgress(isShow: Boolean) {
        when (isShow) {
            true -> {
                loader_group.visibility = View.VISIBLE
                loader_group.playAnimation()
            }
            false -> {
                loader_group.visibility = View.GONE
                loader_group.pauseAnimation()
            }
        }
    }

    override fun setPresenter(presenter: GroupContract.Presenter) {
        this.presenter = presenter
    }

    override fun showError(message: String) {
        Log.e("Error return", message)

        val error = if (message == TIME_OUT || message == NETWORK_ERROR) message else UNKNOWN_ERROR

        count++
        if (count < MAX_RETRY)
            presenter.startGetSubscribedRooms(token)
        else
            Snackbar.make(activity!!.findViewById(android.R.id.content), error, Snackbar.LENGTH_LONG)
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
        return ConnectivityUtil.isConnected(context!!)
    }

}