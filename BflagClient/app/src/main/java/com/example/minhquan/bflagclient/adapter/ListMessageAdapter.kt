package com.example.minhquan.bflagclient.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.minhquan.bflagclient.R

class ListMessageAdapter(var context: Context) : RecyclerView.Adapter<ListMessageAdapter.ViewHolder>() {
    //val data = List<Message>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View?
        if (viewType == 0 ) {
             view = LayoutInflater.from(parent.context).inflate(R.layout.temp, parent, false)
        }
        else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.message, parent, false)
        }
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 19
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0) 0 else 1
     }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {


        init {

            itemView.setOnClickListener(this)

        }

        override fun onClick(p0: View?) {
            Toast.makeText(context, "Clicked!", Toast.LENGTH_SHORT).show()
        }
    }

}