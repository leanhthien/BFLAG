package com.example.minhquan.bflagclient.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.minhquan.bflagclient.R
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.minhquan.bflagclient.model.Friend

class ActiveAdapter(var context: Context) : RecyclerView.Adapter<ActiveAdapter.ViewHolder>() {

    internal var data: List<Friend> = listOf()
    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View? = if (viewType == 0 ) {
            LayoutInflater.from(parent.context).inflate(R.layout.item_empty, parent, false)
        }
        else {
            LayoutInflater.from(parent.context).inflate(R.layout.item_active, parent, false)
        }
        return ViewHolder(view!!)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0) 0 else 1
    }

    fun setData(data: List<Friend>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {

        val friend = this.data[position]

        val animation = AnimationUtils.loadAnimation(context,
                if (position > lastPosition)
                    R.anim.up_from_bottom
                else
                    R.anim.down_from_top)
        holder.itemView.startAnimation(animation)
        lastPosition = position

        if (position == 0) return

        holder.tvUsername.text = friend.username
        Glide.with(context)
                .load(friend.profileImage)
                .apply(RequestOptions
                        .circleCropTransform())
                .into(holder.imgProfile)

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        internal var tvUsername = itemView.findViewById<TextView>(R.id.tv_username)
        internal var imgProfile = itemView.findViewById<ImageView>(R.id.img_left)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            //TODO: Show friend detail information

        }
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.clearAnimation()
    }

}