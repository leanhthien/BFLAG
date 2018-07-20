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

class ChatAdapter(private var context: Context) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    internal val data: MutableList<Chat> = mutableListOf()
    private lateinit var chat: Chat

    fun setData(messange: Chat): Int {
        data.add(messange)
        notifyItemInserted(data.size - 1)
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = if (viewType == 0)
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat_right, parent, false)
        else
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat_left, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (data[position].type == 0) 0 else 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        chat = data[position]
        if (chat.type == 1) Glide.with(context).load(chat.urlAvatar).apply(RequestOptions.circleCropTransform())
                .into(holder.imgChatAvatar!!)
        holder.txtChatMessage.text = chat.message
        //holder.txtChatMessage.setBackgroundResource(R.drawable.background_friendchat)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val cardViewx: CardView? = itemView.findViewById(R.id.cardView)
        internal var imgChatAvatar: ImageView? = itemView.findViewById(R.id.img_ChatAvatar)
        internal var txtChatMessage: TextView = itemView.findViewById(R.id.txt_ChatMessage)

    }
}