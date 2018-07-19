package com.example.minhquan.bflagclient.rockleeadapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.rockleeutil.Chat

class ChatAdapter(internal var context: Context) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    internal val data: MutableList<Chat> = mutableListOf()
    private lateinit var chat: Chat

    fun setData(listNewChat: List<Chat>) {
        data.addAll(listNewChat)
        //notifyDataSetChanged()
        notifyItemInserted(data.size - listNewChat.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        chat = data[position]
        if (chat.urlAvatarL == null) holder.cardViewLeft.visibility = View.GONE
        if (chat.urlAvatarR == null) holder.cardViewRight.visibility = View.GONE


        Glide.with(context).load(chat.urlAvatarL).apply(RequestOptions.circleCropTransform())
                .into(holder.imgChatLeftAvatar)
        holder.txtChatLeftMessage.text = chat.messageL

        Glide.with(context).load(chat.urlAvatarR).apply(RequestOptions.circleCropTransform())
                .into(holder.imgChatRightAvatar)
        holder.txtChatRightMessage.text = chat.messageR

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val cardViewLeft: CardView = itemView.findViewById(R.id.cardViewLeft)
        internal var imgChatLeftAvatar: ImageView = itemView.findViewById(R.id.img_ChatleftAvatar)
        internal var txtChatLeftMessage: TextView = itemView.findViewById(R.id.txt_ChatLeftMessage)

        internal val cardViewRight: CardView = itemView.findViewById(R.id.cardViewRight)
        internal var imgChatRightAvatar: ImageView = itemView.findViewById(R.id.img_ChatRightAvatar)
        internal var txtChatRightMessage: TextView = itemView.findViewById(R.id.txt_ChatRightMessage)
    }
}