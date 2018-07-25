package com.example.minhquan.bflagclient.adapter

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
import com.example.minhquan.bflagclient.model.Chat

class ChatAdapter(private var context: Context) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    internal val data: MutableList<Chat> = mutableListOf()
    private lateinit var chat: Chat

    fun setData(message: Chat): Int {
        data.add(message)
        notifyItemInserted(data.size - 1)
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = if (viewType == SENDER)
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat_sender, parent, false)
        else
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat_receiver, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {

        return if (data[position].type == SENDER) SENDER else RECEIVER

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        chat = data[position]

        if (chat.type == RECEIVER)
            Glide.with(context)
                    .load(chat.urlAvatar)
                    .apply(RequestOptions
                            .circleCropTransform())
                    .into(holder.imgChatAvatar!!)
        //Set length text
        if (chat.message!!.length > MAX_LENGTH) {
            val lp = holder.txtChatMessage.layoutParams as ViewGroup.LayoutParams
            lp.width = 0
            holder.txtChatMessage.layoutParams = lp
        }
        holder.txtChatMessage.text = chat.message
        //holder.txtChatMessage.setBackgroundResource(R.drawable.background_friendchat)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal val cardViewx: CardView? = itemView.findViewById(R.id.cardView)
        internal var imgChatAvatar: ImageView? = itemView.findViewById(R.id.img_ChatAvatar)
        internal var txtChatMessage: TextView = itemView.findViewById(R.id.txt_ChatMessage)

    }

    companion object {
        const val MAX_LENGTH = 30
        const val SENDER = 0
        const val RECEIVER = 1
    }
}