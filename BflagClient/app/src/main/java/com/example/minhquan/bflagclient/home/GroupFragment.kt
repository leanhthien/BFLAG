package com.example.minhquan.bflagclient.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.minhquan.bflagclient.R
import kotlinx.android.synthetic.main.fragment_messages.*
import com.example.minhquan.bflagclient.adapter.ListGroupAdapter


class GroupFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_group,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setUpListView()
    }

    private fun setUpListView() {
        val adapter = ListGroupAdapter(this.context!!)
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter


    }

}