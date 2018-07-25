package com.example.minhquan.bflagclient.roomdata.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.roomdata.database.AppDatabase
import com.example.minhquan.bflagclient.roomdata.entity.User
import com.example.minhquan.bflagclient.roomdata.utils.DatabaseInitializer
import kotlinx.android.synthetic.main.activity_room.*

class RoomActivity : AppCompatActivity() {

    companion object {
        private val TAG = RoomActivity::class.java.name
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        findViewById<TextView>(R.id.btn_insert)

        val user1 = User()
        user1.uid = 39
        user1.firstName = "Ajay"
        user1.lastName = "Saini"
        user1.age = 25
        //txt_roomdata.text = user1.firstName + user1.lastName + user1.age

        btn_insert.setOnClickListener {
            DatabaseInitializer.insertUserAysnc(AppDatabase.getAppDatabase(this),user1)
        }
        btn_view.setOnClickListener {
            val userList: List<User> = DatabaseInitializer.getUser(AppDatabase.getAppDatabase(this))
            txt_roomdata.text = "firstNama: ${userList[0].firstName}\nuid: ${userList[0].uid}"
        }
        btn_delete.setOnClickListener{
            DatabaseInitializer.deleteUserAysnc(AppDatabase.getAppDatabase(this),user1)
            //AppDatabase.destroyInstance()
        }

    }
    

    override fun onDestroy() {
        AppDatabase.destroyInstance()
        super.onDestroy()
    }
}