package com.example.bonfire.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.bonfire.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class onlinefrnds_adapter extends RecyclerView.Adapter<onlinefrnds_adapter.ViewHolder> {

    Context mContext;
    ArrayList<String> imageurllist;
    ArrayList<String> namelist;

    public onlinefrnds_adapter(Context mContext,ArrayList<String> imageurllist,ArrayList<String> namelist)
    {
        this.mContext=mContext;
        this.imageurllist=imageurllist;
        this.namelist=namelist;
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.list_item_rv_online,parent,false);
        return new onlinefrnds_adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Picasso.get().load(imageurllist.get(position)).into(holder.profileimg);
        holder.name.setText(namelist.get(position));
    }

    @Override
    public int getItemCount() {
        return imageurllist.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profileimg;
        TextView name;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            profileimg=itemView.findViewById(R.id.online_user_img);
            name=itemView.findViewById(R.id.online_user_name);  
        }
    }
}
