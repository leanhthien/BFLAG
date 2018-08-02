package com.example.minhquan.bflagclient.adapter

import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.chat.ChatActivity
import com.example.minhquan.bflagclient.model.Room

class RoomAdapter(private var context: ChatActivity) : RecyclerView.Adapter<RoomAdapter.ViewHolder>() {

    internal var data: MutableList<Room> = mutableListOf()
    private lateinit var room: Room

    fun setData(listNewFriend: MutableList<Room>) {
        this.data = listNewFriend
        notifyItemInserted(data.size - listNewFriend.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_friend, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        room = data[position]

        Glide.with(context)
                .load("")
                .apply(RequestOptions.circleCropTransform())
                .into(holder.imgFriendAvatar)
        holder.tvFriendName.text = room.name
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        internal var imgFriendAvatar: ImageView = itemView.findViewById(R.id.img_FriendAvatar)
        internal var tvFriendName: TextView = itemView.findViewById(R.id.txt_NameFriend)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Toast.makeText(context.applicationContext, "item Click ", Toast.LENGTH_SHORT).show()
            context.findViewById<ViewPager>(R.id.vpg_chat_friend).currentItem = layoutPosition
        }
    }

}