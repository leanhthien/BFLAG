package com.example.minhquan.bflagclient.home.active

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.adapter.ActiveAdapter
import com.example.minhquan.bflagclient.home.HomeActivity
import com.example.minhquan.bflagclient.home.HomeContract
import com.example.minhquan.bflagclient.model.Friend
import com.example.minhquan.bflagclient.model.User
import com.example.minhquan.bflagclient.utils.*
import kotlinx.android.synthetic.main.fragment_active.*

const val EMPTY_USER = "There's no user can be shown here"

class ActiveFragment : Fragment(), ActiveContract.View, HomeContract.Listener{

    private lateinit var presenter: ActiveContract.Presenter
    private lateinit var activeAdapter: ActiveAdapter
    private lateinit var homeActivity: HomeActivity
    private lateinit var token: String
    private lateinit var user: User
    private lateinit var listFriends: List<Friend>


    var count  = 0
    private val emptyList = listOf(Friend(null,null, null))

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_active,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ActivePresenter(this)
        setupListener()
        setupView()
    }

    private fun setupListener() {
        homeActivity = activity as HomeActivity
        homeActivity.setListener(this)
    }

    private fun setupView() {

        activeAdapter = ActiveAdapter(context!!)
        rv_active.setHasFixedSize(true)
        rv_active.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        rv_active.adapter = activeAdapter

        val tokenReturn = SharedPreferenceHelper.getInstance(context!!).getToken()
        val userReturn =  SharedPreferenceHelper.getInstance(context!!).getUser()

        if (userReturn!= null)
            user = userReturn

        if (tokenReturn != null) {
            token = tokenReturn
            presenter.startGetOnlineUsers(token)
        }

    }

    override fun onFinishGetUser() {
        val tokenReturn = SharedPreferenceHelper.getInstance(context!!).getToken()
        val userReturn =  SharedPreferenceHelper.getInstance(context!!).getUser()

        if (userReturn!= null)
            user = userReturn

        if (tokenReturn != null) {
            token = tokenReturn
            presenter.startGetOnlineUsers(token)
        }
    }


    override fun onGetOnlineUsersSuccess(result: List<Friend>) {

        showProgress(false)

        if (result.isEmpty()) {
            tv_empty.visibility = View.VISIBLE
            tv_empty.text = EMPTY_USER
        }
        else {

            //listFriends = emptyList + result.filter { it -> it.email != user.email }.sortedBy { it -> it.username }
            listFriends = emptyList + result.sortedBy { it -> it.username }

            tv_empty.visibility = View.GONE
            activeAdapter.setData(listFriends)

        }
    }

    override fun showProgress(isShow: Boolean) {
        when (isShow) {
            true -> {
                loader_active.visibility = View.VISIBLE
                loader_active.playAnimation()
            }
            false -> {
                loader_active.visibility = View.GONE
                loader_active.pauseAnimation()
            }
        }
    }

    override fun setPresenter(presenter: ActiveContract.Presenter) {
        this.presenter = presenter
    }

    override fun showError(message: String) {
        Log.e("Error return", message)

        showProgress(false)

        count++
        if (count < MAX_RETRY)
            presenter.startGetOnlineUsers(token)
        else
            Snackbar.make(activity!!.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
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