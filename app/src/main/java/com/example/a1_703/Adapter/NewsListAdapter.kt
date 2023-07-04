package com.example.a1_703.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.ColorFilter
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.a1_703.NewsInfoActivity
import com.example.a1_703.SqlMethods
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
        var isStar=SqlMethods.News(context).getStar(jsonArray[position].toString())
        if(isStar){
            b.star.setColorFilter(Color.RED)
        }else{
            b.star.setColorFilter(Color.GRAY)
        }
        b.star.setOnClickListener {
            if(SqlMethods.News(context).starChange(jsonArray[position].toString())){
                b.star.setColorFilter(Color.RED)
            }else{
                b.star.setColorFilter(Color.GRAY)
            }
        }

        b.root.setOnClickListener {
            var i=Intent(context,NewsInfoActivity::class.java)
            i.putExtra("json",jsonArray[position].toString())
            context.startActivity(i)
        }
        return b.root
    }
}