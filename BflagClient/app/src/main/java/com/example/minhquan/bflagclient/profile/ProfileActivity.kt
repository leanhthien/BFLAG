package com.example.minhquan.bflagclient.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.model.User
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.activity_profile.*
import android.view.*
import com.bumptech.glide.Glide
import com.example.minhquan.bflagclient.home.HomeActivity
import com.example.minhquan.bflagclient.profile.dialog.DialogActivity
import com.example.minhquan.bflagclient.profile.editprofile.ChangePasswordActivity
import com.example.minhquan.bflagclient.profile.editprofile.EditProfileActivity
import com.example.minhquan.bflagclient.utils.*


class ProfileActivity : AppCompatActivity(){
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        setUpStatusBar()

       fabMenu.setOnClickListener {
           val intent = Intent(this, DialogActivity::class.java)
           val p1 = Pair.create<View, String>(fabMenu, "menuCircle")
           val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1)

           Blurry.with(this).radius(25).sampling(2).onto(coordinator as ViewGroup)
           startActivity(intent, options.toBundle())
       }


        user =  SharedPreferenceHelper.getInstance(this).getUser()!!

        Glide.with(this).load(user.profileImage).into(circle_image_view)
        collapsing.title = user.username
        tvFullname.text = user.firstName + " " + user.lastName
        tvGender.text = user.gender
        tvBirthday.text = user.birthday
        tvEmail.text = user.email

        btnEditInfo.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, EditProfileActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
            finish()
        }

        tvChangePassword.setOnClickListener {
            startActivity(Intent(this,ChangePasswordActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        Blurry.delete(coordinator)
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun setUpStatusBar() {
        val window = this.window

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,HomeActivity::class.java))
        finish()
    }

}