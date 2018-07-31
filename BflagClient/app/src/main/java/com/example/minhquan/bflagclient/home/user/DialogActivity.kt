package com.example.minhquan.bflagclient.home.user

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.widget.Toast
import com.example.minhquan.bflagclient.R
import kotlinx.android.synthetic.main.activity_dialog.*

class DialogActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_dialog)
        this.setFinishOnTouchOutside(true)

        // handle click for item of circle menu
        circleMenu.setOnItemClickListener {
            when(it.id){
                R.id.home -> Toast.makeText(this@DialogActivity,"Home!!", Toast.LENGTH_SHORT).show()
                R.id.search-> Toast.makeText(this@DialogActivity,"Seach!!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}