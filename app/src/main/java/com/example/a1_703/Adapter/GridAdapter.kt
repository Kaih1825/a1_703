package com.example.a1_703.Adapter

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.a1_703.databinding.SkillImageBinding
import org.json.JSONArray
import org.json.JSONObject

class GridAdapter(var context: Context,var jsonArray: List<JSONObject>):BaseAdapter() {
    override fun getCount(): Int {
        return jsonArray.count()
    }

    override fun getItem(position: Int): Any {
       return jsonArray[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var b=SkillImageBinding.inflate((context as Activity).layoutInflater)
        var ins=context.assets.open("professions/"+(jsonArray[position]).getString("image"))
        b.img.setImageDrawable(Drawable.createFromStream(ins,""))
        b.txt.text=(jsonArray[position]).getString("title")
        return b.root
    }
}