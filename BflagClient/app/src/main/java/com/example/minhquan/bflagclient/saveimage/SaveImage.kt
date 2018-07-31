package com.example.minhquan.bflagclient.saveimage

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.minhquan.bflagclient.R
import android.graphics.Bitmap
import kotlinx.android.synthetic.main.activity_save_image.*
import android.content.ContextWrapper
import android.util.Log
import android.graphics.BitmapFactory
import com.bumptech.glide.Glide
import com.example.minhquan.bflagclient.utils.savePhoto
import java.io.*
import android.os.Looper
import android.os.AsyncTask
import java.util.concurrent.ExecutionException
import com.bumptech.glide.request.target.SimpleTarget
import android.R.attr.path
import android.R.attr.path
import android.support.transition.Transition
import android.widget.Toast
import com.bumptech.glide.request.target.BitmapImageViewTarget








@Suppress("DEPRECATION")
class SaveImage : AppCompatActivity() {
    var theBitmap: Bitmap? = null
    companion object {
        val URL_DEMO = "https://cdn.pixabay.com/photo/2013/04/06/11/50/image-editing-101040_960_720.jpg"
        val TAG = SaveImage::class.java.name
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_image)

        Glide.with(this)
                .asBitmap()
                .load(URL_DEMO)
                .into(object : SimpleTarget<Bitmap>(200, 200) {
                    override fun onResourceReady(resource: Bitmap, transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?) {
                        theBitmap = resource
                        Log.e(TAG,"Loaded ")
                    }
                })
        btn_save_image.setOnClickListener {
            if(theBitmap != null) {
                val a = this.savePhoto(theBitmap!!)
                Log.e(TAG,a)
                img_Avatar.setImageBitmap(theBitmap)
            }


        }

    }


}