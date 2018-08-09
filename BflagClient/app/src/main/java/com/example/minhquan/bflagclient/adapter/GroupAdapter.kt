package com.example.minhquan.bflagclient.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import com.example.minhquan.bflagclient.chat.ChatActivity
import com.example.minhquan.bflagclient.model.Room
import com.example.minhquan.bflagclient.utils.*

const val ROOM_AMOUNT = 4
const val PHOTO_SHARE = " shared an photo"
const val SUBSCRIBED_ROOM = 0
const val SEARCH_ROOM = 1

class GroupAdapter(var context: Context, val type: Int) : RecyclerView.Adapter<GroupAdapter.ViewHolder>() {

    internal var data: List<Room>
    private var lastPosition = -1

    init {
        data = listOf()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View? = when (viewType) {
            0 -> LayoutInflater.from(parent.context).inflate(R.layout.item_empty, parent, false)
            1 -> LayoutInflater.from(parent.context).inflate(R.layout.item_empty_search, parent, false)
            else -> LayoutInflater.from(parent.context).inflate(R.layout.item_group, parent, false)
        }
        return ViewHolder(view!!)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 && data[position].id == HOME -> 0
            position == 0 && data[position].id == SEARCH -> 1
            else -> 2
        }
    }

    fun setData(data: List<Room>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {

        val room = data[position]

        val animation = AnimationUtils.loadAnimation(context,
                if (position > lastPosition)
                    R.anim.up_from_bottom
                else
                    R.anim.down_from_top)
        holder.itemView.startAnimation(animation)
        lastPosition = position


        if (position == 0) return

            holder.tvRoomName.text = room.name

            if (room.lastMessage != null) {
                if (room.lastMessage.message != null) {
                    if ( room.lastMessage.message.content != null) {
                        val message = room.lastMessage.friend!!.username + ": " + room.lastMessage.message.content
                        holder.tvLastMessage.text = message
                    }
                    else {
                        val message = room.lastMessage.friend!!.username + PHOTO_SHARE
                        holder.tvLastMessage.text = message
                    }
                    holder.tvLastAccess.text = room.lastMessage.time!!.makePrettyDate()
                }
            }
            else {
                holder.tvLastAccess.text = UNKNOWN
                holder.tvLastMessage.text = UNKNOWN
            }

            if (room.listFriends!!.isNotEmpty())
                Glide.with(context)
                        .load(room.listFriends[0].profileImage)
                        .apply(RequestOptions
                                .circleCropTransform())
                        .into(holder.imgLeft)
            else
                Glide.with(context)
                        .load(DEFAULT_PROFILE_IMAGE)
                        .apply(RequestOptions
                                .circleCropTransform())
                        .into(holder.imgLeft)


    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        internal var tvRoomName = itemView.findViewById<TextView>(R.id.tv_room_name)
        internal var tvLastMessage = itemView.findViewById<TextView>(R.id.tv_last_message)
        internal var tvLastAccess = itemView.findViewById<TextView>(R.id.tv_last_access)
        internal var imgLeft = itemView.findViewById<ImageView>(R.id.img_left)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {

            val listRooms: ArrayList<Room>
            val intent = Intent(context, ChatActivity::class.java)
            val bundle = Bundle()

            listRooms = if (type == SUBSCRIBED_ROOM){
                val leftRooms = data
                        .filter { it -> it.name != data[layoutPosition-1].name }
                        .filter { it -> it.id != null }

                (listOf(data[layoutPosition-1])
                                + leftRooms.take(
                                if (leftRooms.size < ROOM_AMOUNT) leftRooms.size else ROOM_AMOUNT))
                        .toCollection(ArrayList())

            } else {
                arrayListOf(data[layoutPosition-1])
            }

            bundle.putParcelableArrayList("listRooms", listRooms)
            intent.putExtra("roomBundle", bundle)
            context.startActivity(intent)

        }
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.clearAnimation()
    }

}