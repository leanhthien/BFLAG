package com.example.minhquan.bflagclient.rockleeadapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.rockleeutil.Friend

class FriendAdapter(internal var context: Context) : RecyclerView.Adapter<FriendAdapter.ViewHolder>() {
    internal var data: List<Friend>
    lateinit var friend: Friend

    init {
        data = ArrayList()
    }

    fun setData(data: List<Friend>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_friend, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        friend = data[position]

        Glide.with(context).load(friend.urlAvatar).apply(RequestOptions.circleCropTransform())
                .into(holder.imgFriendAvatar)
        holder.tvFriendName.text = friend.name

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var imgFriendAvatar: ImageView = itemView.findViewById(R.id.img_FriendAvatar)
        internal var tvFriendName: TextView = itemView.findViewById(R.id.txt_NameFriend)
    }
}