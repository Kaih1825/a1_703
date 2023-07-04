package com.example.a1_703

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.example.a1_703.Adapter.NewsListAdapter
import com.example.a1_703.Adapter.ViewPagerAdapter
import com.example.a1_703.databinding.ActivityNewsBinding
import com.google.android.material.chip.Chip
import org.json.JSONArray
import org.json.JSONObject

class News : AppCompatActivity() {
    lateinit var b:ActivityNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b= ActivityNewsBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.newNews.setOnClickListener {
            startActivity(Intent(this,NewNews()::class.java))
        }

        var images= arrayOf(R.drawable.banner1,R.drawable.banner2,R.drawable.banner3,R.drawable.banner4)
        b.viewPager.adapter=ViewPagerAdapter(this,images)
        b.viewPager.offscreenPageLimit=3
        var iS= arrayOf(b.i1,b.i2,b.i3,b.i4)
        b.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                for(i in 0 until iS.size){
                    iS[i].setCardBackgroundColor(Color.GRAY)
                }
                iS[position].setCardBackgroundColor(resources.getColor(R.color.blue))
            }
        })

        b.allStar.setOnClickListener {
            startActivity(Intent(this,AllStarActivity::class.java))
        }

        var jsonArray= SqlMethods.storeNews(this).getNews()
        b.list.adapter=NewsListAdapter(this,jsonArray)

        b.chips.setOnCheckedStateChangeListener { _, checkedIds ->
            var type=b.chips.findViewById<Chip>(checkedIds[0]).text.toString()
            var jsonArray2= SqlMethods.storeNews(this).getNews()
            var tisJson=jsonArray2
            if(type!="全部"){
                tisJson= jsonArray2.filter { it.getString("Type")==type } as ArrayList<JSONObject>
            }

            b.list.adapter=NewsListAdapter(this,tisJson)
        }

        b.back.setOnClickListener {
            finish()
        }

    }

    override fun onRestart() {
        super.onRestart()
        var jsonArray=SqlMethods.storeNews(this).getNews()
        var checkedIds=b.chips.checkedChipIds
        var type=b.chips.findViewById<Chip>(checkedIds[0]).text.toString()
        var tisJson=jsonArray
        if(type!="全部"){
            tisJson= jsonArray.filter { it.getString("Type")==type } as ArrayList<JSONObject>
        }

        b.list.adapter=NewsListAdapter(this,tisJson)
    }
}