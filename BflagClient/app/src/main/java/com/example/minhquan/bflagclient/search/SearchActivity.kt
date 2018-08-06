package com.example.minhquan.bflagclient.search

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.adapter.GroupAdapter
import com.example.minhquan.bflagclient.adapter.SEARCH_ROOM
import com.example.minhquan.bflagclient.model.Friend
import com.example.minhquan.bflagclient.model.Room
import com.example.minhquan.bflagclient.utils.*
import kotlinx.android.synthetic.main.activity_search.*


const val EMPTY_RESULT = "There's no result"

class SearchActivity : AppCompatActivity(), SearchContract.View{


    private lateinit var presenter: SearchContract.Presenter
    private lateinit var token: String
    private lateinit var groupAdapter: GroupAdapter
    private lateinit var listRooms: List<Room>
    private lateinit var query : String

    var count  = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setupView()
    }

    private fun setupView() {

        groupAdapter = GroupAdapter(this, SEARCH_ROOM)
        rv_search_room.setHasFixedSize(true)
        rv_search_room.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        rv_search_room.adapter = groupAdapter

        val tokenReturn = SharedPreferenceHelper.getInstance(this).getToken()

        if (tokenReturn != null) {
            token = tokenReturn
        }

        edtSearch.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                query = edtSearch.text.toString()
                presenter.startSearchRoom(token, query)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })


        tvCancel.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onSearchRoomSuccess(result: List<Room>) {

        if (result.isEmpty()) {
            tv_empty.visibility = View.VISIBLE
            tv_empty.text = EMPTY_RESULT
        }
        else {

            listRooms = result.sort()

            Log.d("Searched room", listRooms.toString())

            tv_empty.visibility = View.GONE
            groupAdapter.setData(listRooms)

        }
    }

    override fun onSearchUserSuccess(result: List<Friend>) {

    }

    override fun showProgress(isShow: Boolean) {

    }

    override fun setPresenter(presenter: SearchContract.Presenter) {
        this.presenter = presenter
    }

    override fun showError(message: String) {
        Log.e("Error return", message)

        showProgress(false)

        count++
        if (count < MAX_RETRY)
            Snackbar.make(this.window.decorView.findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE)
                    .setAction(RETRY) {
                        //presenter.startGetUser(token)
                    }
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
}