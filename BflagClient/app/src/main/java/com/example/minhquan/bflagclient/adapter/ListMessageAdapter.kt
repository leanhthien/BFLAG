package com.example.minhquan.bflagclient.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.minhquan.bflagclient.R
import android.view.animation.AnimationUtils.loadAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.AlphaAnimation
import android.view.animation.DecelerateInterpolator

class ListMessageAdapter(var context: Context) : RecyclerView.Adapter<ListMessageAdapter.ViewHolder>() {
    //val data = List<Message>
    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View? = if (viewType == 0 ) {
            LayoutInflater.from(parent.context).inflate(R.layout.temp, parent, false)
        }
        else {
            LayoutInflater.from(parent.context).inflate(R.layout.message, parent, false)
        }
        return ViewHolder(view!!)
    }

    override fun getItemCount(): Int {
        return 30
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0) 0 else 1
     }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {

        val animation = AnimationUtils.loadAnimation(context,
                if (position > lastPosition)
                    R.anim.up_from_bottom
                else
                    R.anim.down_from_top)
        holder.itemView.startAnimation(animation)
        lastPosition = position

        //setFadeAnimation(holder.itemView)
    }

//    private fun setFadeAnimation(view: View) {
//        val anim = AlphaAnimation(0.0f, 1.0f)
//        anim.duration = 500
//        view.startAnimation(anim)
//    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {


        init {

            itemView.setOnClickListener(this)

        }

        override fun onClick(p0: View?) {
            Toast.makeText(context, "Clicked!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.clearAnimation()
    }

}