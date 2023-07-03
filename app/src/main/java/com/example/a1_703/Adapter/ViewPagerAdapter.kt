package com.example.a1_703.Adapter

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.a1_703.R
import com.example.a1_703.databinding.NewsViewpager2Binding

class ViewPagerAdapter(var context: Context,var images:Array<Int>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class viewHolder(view:View): RecyclerView.ViewHolder(view) {
        var b=view.findViewById<ImageView>(R.id.img)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var b=NewsViewpager2Binding.inflate((context as Activity).layoutInflater,parent,false)
        return viewHolder(b.root)
    }

    override fun getItemCount(): Int {
        return images.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var h=holder as viewHolder
        h.b.setImageDrawable(context.getDrawable(images[position]))
    }
}