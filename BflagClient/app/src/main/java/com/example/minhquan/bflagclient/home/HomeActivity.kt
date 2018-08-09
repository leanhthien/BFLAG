package com.example.minhquan.bflagclient.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.adapter.PagerHomeAdapter
import kotlinx.android.synthetic.main.activity_home.*
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.support.v4.util.Pair
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.minhquan.bflagclient.chat.ChatActivity
import com.example.minhquan.bflagclient.profile.ProfileActivity
import com.example.minhquan.bflagclient.model.User
import com.example.minhquan.bflagclient.search.SearchActivity
import com.example.minhquan.bflagclient.utils.*
import com.afollestad.materialdialogs.MaterialDialog
import com.example.minhquan.bflagclient.model.Room
import com.google.gson.JsonObject


const val EMPTY_ERROR = "Name can not be empty!"

class HomeActivity : AppCompatActivity(), HomeContract.View {

    private lateinit var presenter: HomeContract.Presenter
    private lateinit var listener: HomeContract.Listener
    private lateinit var token: String
    private var count: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        HomePresenter(this)



        setUpView()

    }

    private fun setUpView() {

        // set up adapter for view pager
        val adapter = PagerHomeAdapter(this, supportFragmentManager)
        viewPager.adapter = adapter

        tabLayout.setupWithViewPager(viewPager)

        // animation shared element when click search
        edtSearch.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            val p1 = Pair.create<View, String>(edtSearch, "search")
            val p2 = Pair.create<View, String>(tvLine, "line")
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1, p2)
            startActivity(intent, options.toBundle())
            finish()
        }

        setUpStatusBar()

        // get height of navigation bottom bar
        val height = getHeightNavigationBottom()
        // and set for view blur
        blurNav.layoutParams.height = height


        val userReturn = SharedPreferenceHelper.getInstance(this).getUser()
        val tokenReturn =  SharedPreferenceHelper.getInstance(this).getToken()

        if (tokenReturn != null) {
            token = tokenReturn
        }
        if (userReturn != null) {

            if (!isNetworkConnected())
                Snackbar.make(this.window.decorView.findViewById(android.R.id.content), WORKING_OFFLINE, Snackbar.LENGTH_LONG)
                        .show()
            setUser(userReturn)

        }
        else {
            presenter.startGetUser(token)
        }
    }

    private fun setUser(user: User) {

        // animation shared element when click image profile
        //imgProfile.setImageResource(user.profileImage)

        if (isNetworkConnected())
            Glide.with(this)
                .load(user.profileImage)
                .apply(RequestOptions
                        .circleCropTransform())
                .into(imgProfile)


        imgProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            val p1 = Pair.create<View, String>(imgProfile, "profile")
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1)
            startActivity(intent, options.toBundle())
            finish()
        }

        img_add.setOnClickListener {

            val wrapInScrollView = true
            val builder = MaterialDialog.Builder(this)
                    .customView(R.layout.popup_creat_room, wrapInScrollView)

            val dialog = builder.build()
            val view = dialog.customView

            dialog.show()

            view!!.findViewById<Button>(R.id.btn_ok).setOnClickListener {

                val edtName = view.findViewById<EditText>(R.id.edt_name)
                if(TextUtils.isEmpty(edtName.text.toString()))
                    edtName.error = EMPTY_ERROR
                else {
                    val body = JsonObject().buildCreateRoom(edtName.text.toString())
                    presenter.startCreateRoom(token,body)
                    dialog.dismiss()
                }
            }

            view.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
                dialog.dismiss()
            }
        }
    }

    /**
     * Function set up status bar and navigation bottom
     */
    private fun setUpStatusBar() {
        val window = this.window

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = getWindow().decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        // make layout no limit
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        // show and translucent navigation bottom bar
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)

        // finally change the color
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorWhite)

        val height = getHeightNavigationBottom()

        blurNav.layoutParams.height = height
    }

    /**
     * Function get height of navigation bottom
     */
    private fun getHeightNavigationBottom(): Int {

        val resources = this.resources
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else 0
    }

    override fun onGetUserSuccess(result: User) {
        showProgress(false)
        SharedPreferenceHelper.getInstance(this).setUser(result)
        setUser(result)
        listener.onFinishGetUser()
    }

    override fun onCreateRoomSuccess(result: Room) {
        showProgress(false)
        Toast.makeText(this, "New chat room has been created", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, ChatActivity::class.java)
        val bundle = Bundle()
        val listRooms = arrayListOf(result)

        bundle.putParcelableArrayList("listRooms", listRooms)
        intent.putExtra("roomBundle", bundle)
        this.startActivity(intent)
        finish()
    }

    override fun showProgress(isShow: Boolean) {
        when (isShow) {
            true -> {
                loader_home.visibility = View.VISIBLE
                loader_home.playAnimation()
            }
            false -> {
                loader_home.visibility = View.GONE
                loader_home.pauseAnimation()
            }
        }
    }

    override fun setPresenter(presenter: HomeContract.Presenter) {
        this.presenter = presenter
    }

    override fun showError(message: String) {
        Log.e("Error return", message)

        showProgress(false)

        count++
        if (count < MAX_RETRY)
            Snackbar.make(this.window.decorView.findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE)
                    .setAction(RETRY) {
                        presenter.startGetUser(token)
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

    fun setListener(listener: HomeContract.Listener) {
        this.listener = listener
    }
}