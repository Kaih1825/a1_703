package com.example.a1_703

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a1_703.Adapter.NewsListAdapter
import com.example.a1_703.databinding.ActivityAllStarBinding
import com.google.android.material.chip.Chip
import org.json.JSONArray
import org.json.JSONObject

class AllStarActivity : AppCompatActivity() {
    lateinit var b:ActivityAllStarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b=ActivityAllStarBinding.inflate(layoutInflater)
        setContentView(b.root)
        var jsonArray= SqlMethods.storeNews(this).getNews()
        var jsonArray2= arrayListOf<JSONObject>()
        for(i in 0 until  jsonArray.count()){
            if(SqlMethods.News(this).getStar((jsonArray[i]).toString())){
                jsonArray2.add(jsonArray[i])
            }
        }
        b.list.adapter= NewsListAdapter(this,jsonArray2)

        b.back.setOnClickListener {
            finish()
        }

        b.chips.setOnCheckedStateChangeListener { _, checkedIds ->
            var type=b.chips.findViewById<Chip>(checkedIds[0]).text.toString()
            var tisJson=jsonArray2
            if(type!="全部"){
                tisJson= jsonArray2.filter { it.getString("Type")==type } as ArrayList<JSONObject>
            }

            b.list.adapter= NewsListAdapter(this,tisJson)
        }

        b.back.setOnClickListener {
            finish()
        }
    }
}