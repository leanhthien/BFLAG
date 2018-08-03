package com.example.minhquan.bflagclient.adapter

import android.content.Context
import android.net.Uri
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.model.Chat
import com.example.minhquan.bflagclient.utils.RECEIVER
import com.example.minhquan.bflagclient.utils.SENDER
import com.example.minhquan.bflagclient.utils.SharedPreferenceHelper
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import java.io.File

const val MAX_LENGTH = 30

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
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat_sender,
                                                        parent,
                                                        false)
        else
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat_receiver,
                                                        parent,
                                                        false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {

        return if (data[position].friend!!.email!! == SharedPreferenceHelper.getInstance(context).getUser()!!.email)
            SENDER else RECEIVER
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        chat = data[position]

        if (holder.itemViewType == RECEIVER)
            Glide.with(context)
                    .load(chat.friend?.profileImage)
                    .apply(RequestOptions
                            .circleCropTransform())
                    .into(holder.imgChatAvatar!!)
        //Set length text
        if (chat.content != null ) {

            holder.txtChatMessage.visibility = View.VISIBLE
            holder.imgChatShare.visibility = View.GONE

            if (chat.content!!.length > MAX_LENGTH) {
                val lp = holder.txtChatMessage.layoutParams
                                                as ViewGroup.LayoutParams
                lp.width = 0
                holder.txtChatMessage.layoutParams = lp
            }
            holder.txtChatMessage.text = chat.content
        }
        else {

            holder.txtChatMessage.visibility = View.GONE
            holder.imgChatShare.visibility = View.VISIBLE

            if (holder.itemViewType == SENDER) {

                Glide.with(context)
                        .load(Uri.fromFile(File(chat.imgUrl)))
                        .apply(bitmapTransform(RoundedCornersTransformation(64, 0, RoundedCornersTransformation.CornerType.ALL)))
                        .into(holder.imgChatShare)
            }
            else
                Glide.with(context)
                    .load(chat.imgUrl)
                    .apply(bitmapTransform(RoundedCornersTransformation(64, 0, RoundedCornersTransformation.CornerType.ALL)))
                    .into(holder.imgChatShare)


        }
        //holder.txtChatMessage.setBackgroundResource(R.drawable.background_friend_chat)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal val cardView_: CardView? = itemView.findViewById(R.id.cardView)
        internal var imgChatAvatar: ImageView? = itemView.findViewById(R.id.img_chat_avatar)
        internal var txtChatMessage: TextView = itemView.findViewById(R.id.txt_chat_message)
        internal var imgChatShare: ImageView = itemView.findViewById(R.id.img_share)

    }

}