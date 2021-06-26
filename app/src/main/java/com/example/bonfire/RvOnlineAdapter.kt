package com.example.bonfire

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bonfire.data.User
import com.example.bonfire.ui.HomeFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_rv_online.view.*

class RvOnlineAdapter(private val list: List<User>): RecyclerView.Adapter<RvOnlineAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_rv_online,
            parent,
            false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(holder.itemView).load(list[position].imageUrl).into(holder.itemView.userImageView)
    }

    override fun getItemCount(): Int = list.size

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}