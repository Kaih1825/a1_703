package com.example.a1_703

import android.content.Intent
import android.graphics.Color
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.ImageView
import android.widget.TextClock
import android.widget.TextView
import coil.load
import com.example.a1_703.databinding.ActivityNewsBinding
import com.example.a1_703.databinding.ActivityNewsInfoBinding
import org.json.JSONArray
import org.json.JSONObject

class NewsInfoActivity() : AppCompatActivity() {
    lateinit var b:ActivityNewsInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b=ActivityNewsInfoBinding.inflate(layoutInflater)
        setContentView(b.root)
        var jsonObject= JSONObject(intent.getStringExtra("json")!!)
        b.date.text=jsonObject.getString("Date")
        b.type.text=jsonObject.getString("Type")
        b.ptype.text=jsonObject.getString("Announcer")
        b.context.text=jsonObject.getString("Content")
        b.title.text=jsonObject.getString("Title")

        b.back.setOnClickListener {
            finish()
        }

        var isStar=SqlMethods.News(this).getStar(jsonObject.toString())
        if(isStar){
            b.star.setColorFilter(Color.RED)
        }else{
            b.star.setColorFilter(Color.GRAY)
        }

        b.star.setOnClickListener {
            if(SqlMethods.News(this).starChange(jsonObject.toString())){
                b.star.setColorFilter(Color.RED)
            }else{
                b.star.setColorFilter(Color.GRAY)
            }
        }


        var urlArr=jsonObject.getString("Url").split("\n")
        var linkArr=jsonObject.getString("LinkText").split("\n")
        for(i in 0 until urlArr.count()){
            var textView=TextView(this)
            var txt=SpannableString(linkArr[i])
            txt.setSpan(UnderlineSpan(),0,txt.length,0)
            textView.text=txt
            textView.setPadding(0,10,0,10)
            textView.setTextColor(Color.BLUE)
            textView.setOnClickListener {
                var uri= Uri.parse(urlArr[i])
                var i=Intent(Intent.ACTION_VIEW,uri)
                i.setPackage("com.android.chrome")
                startActivity(i)
            }
            b.contextView.addView(textView)
        }

        var pics=jsonObject.getJSONArray("PIC")
        for(i in 0 until pics.length()){
            var url=pics[i] as String
            var imageView=ImageView(this)
            b.contextView.addView(imageView)
            imageView.load(url)
        }
    }
}