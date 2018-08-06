package com.example.minhquan.bflagclient.adapter

import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
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
import com.example.minhquan.bflagclient.utils.DEFAULT_PROFILE_IMAGE

class RoomAdapter(private var context: ChatActivity) : RecyclerView.Adapter<RoomAdapter.ViewHolder>() {

    internal var data: List<Room>
    private lateinit var room: Room
    private var chosenRoom = 0

    init {
        data = listOf()
    }

    fun setData(listNewFriend: List<Room>) {
        this.data = listNewFriend
        notifyDataSetChanged()
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

        if (position == chosenRoom) {
            holder.cvBgActive.visibility = View.VISIBLE
            holder.cvBgActive.background = ResourcesCompat.getDrawable(context.resources,
                                                        R.drawable.background_rounded_logo_facebook,
                                                        null)

            holder.tvFriendName.setTextColor(ContextCompat.getColor(context, R.color.colorBlueFacebookLight))
        }
        else {
            holder.cvBgActive.visibility = View.INVISIBLE
            holder.tvFriendName.setTextColor(ContextCompat.getColor(context, R.color.colorGreyFacebook))
        }

        if (room.lastMessage != null)
            Glide.with(context)
                    .load(room.lastMessage!!.friend!!.profileImage)
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.imgFriendAvatar)
        else
            Glide.with(context)
                    .load(DEFAULT_PROFILE_IMAGE)
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.imgFriendAvatar)


        var name = room.name

        if (name!!.length > 7) {
            name = name.substring(0, 7) + ".."
            holder.tvFriendName.text = name
        }
        else
            holder.tvFriendName.text = name

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        internal var cvBgActive: CardView = itemView.findViewById(R.id.cv_bg_active)
        internal var imgFriendAvatar: ImageView = itemView.findViewById(R.id.img_avatar)
        internal var tvFriendName: TextView = itemView.findViewById(R.id.tv_username)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            context.findViewById<ViewPager>(R.id.vpg_chat_friend).currentItem = layoutPosition
            chosenRoom = layoutPosition

            setData(data)

        }
    }

}