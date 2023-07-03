package com.example.a1_703.Adapter

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.a1_703.databinding.NewsListBinding
import org.json.JSONObject

class NewsListAdapter(var context: Context,var jsonArray:ArrayList<JSONObject>): BaseAdapter() {
    override fun getCount(): Int {
        return jsonArray.count()
    }

    override fun getItem(position: Int): Any {
        return 0
    }

    override fun getItemId(position: Int): Long {
        return 5
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var b=NewsListBinding.inflate((context as Activity).layoutInflater)
        b.date.text=jsonArray[position].getString("Date")
        b.type.text=jsonArray[position].getString("Type")
        b.title.text=jsonArray[position].getString("Title")
        return b.root
    }
}