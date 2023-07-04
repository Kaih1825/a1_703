package com.example.a1_703.Adapter

import android.app.Activity
import android.app.WallpaperColors
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import coil.load
import com.example.a1_703.BuyTicket
import com.example.a1_703.TicketInfo
import com.example.a1_703.databinding.BuyTicketsListBinding
import com.example.a1_703.databinding.TicketImageBinding

class BuyTicketsListAdapter(var context:Context,var titles:Array<String>,var subTitles:Array<String>,var contexts:Array<String>,var colors: Array<Int>):BaseAdapter() {
    override fun getCount(): Int {
        return titles.count()+1
    }

    override fun getItem(position: Int): Any {
        return 0
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        if(position==0){
            var b=TicketImageBinding.inflate((context as Activity).layoutInflater)
            b.img.load("https://static.accupass.com/eventintro/2306120731541284895676.jpg")
            return b.root
        }
        var b=BuyTicketsListBinding.inflate((context as Activity).layoutInflater)
        b.title.text=titles[position-1]
        b.subTitle.text=subTitles[position-1]
        b.context.text=contexts[position-1]
        b.title.setTextColor(colors[position-1])
        b.subTitle.setTextColor(colors[position-1])

        b.buy.setOnClickListener {
            var i=Intent(context,TicketInfo::class.java)
            i.putExtra("Title",titles[position-1])
            i.putExtra("Subtitle",subTitles[position-1])
            i.putExtra("Context",contexts[position-1])
            i.putExtra("Color",colors[position-1])
            context.startActivity(i)
        }
        return b.root
    }
}