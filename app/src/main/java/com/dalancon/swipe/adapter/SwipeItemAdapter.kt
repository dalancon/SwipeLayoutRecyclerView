package com.dalancon.swipe.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dalancon.swipe.R
import kotlinx.android.synthetic.main.swipe_layout.view.*

/**
 * Created by dalancon on 2019/3/20.
 */

class SwipeItemAdapter(var context: Context) : RecyclerView.Adapter<SwipeItemAdapter.MyHolder>() {

    private var layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyHolder {
        return MyHolder(layoutInflater.inflate(R.layout.swipe_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.itemView.tv_delete.setOnClickListener {
            Toast.makeText(context, "delete", Toast.LENGTH_LONG).show()
        }

        holder.itemView.tv_content.setOnClickListener {
            Toast.makeText(context, "content", Toast.LENGTH_LONG).show()
        }
    }


    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}
