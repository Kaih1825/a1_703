package com.example.a1_703

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a1_703.Adapter.BuyTicketsListAdapter
import com.example.a1_703.databinding.ActivityBuyTicketBinding

class BuyTicket : AppCompatActivity() {
    lateinit var b:ActivityBuyTicketBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b=ActivityBuyTicketBinding.inflate(layoutInflater)
        setContentView(b.root)
        var titles= arrayOf("\uD83C\uDF32職涯探索森林\uD83C\uDF32","⛰️職訓冒險島⛰️","\uD83D\uDD27創客玩轉世界\uD83D\uDEE0️")
        var subTitles= arrayOf("找到自己的熱情！","體驗職類的操作秘辛！","現場實作最對味！")
        var contexts= arrayOf("想跟技能選手一樣找到自己的興趣所在嗎？快來這區踩點！\n" +
                "透過「性格測試」認識自我，並進一步「體驗職場」，探索未來更多可能！","匯集亮點產業(AI、IoT、5G等議題)的職訓計畫，並運用時下最流行的 #VR、#AR模擬室內配線及CNC操作等項目，讓大小朋友沉浸在職類的奧秘之中。","在競賽的68個職類中，多半都包含手作及創意等面向，創客玩轉世界匯聚精髓，舉辦多項有趣的DIY課程，讓大家像技能選手一樣，動手打造，玩轉樂趣！")
        var colors= arrayOf(Color.argb(255,75,132,99),Color.argb(255,115,144,185),Color.argb(255,231,138,66))
        b.list.adapter=BuyTicketsListAdapter(this,titles,subTitles,contexts,colors)

        b.back.setOnClickListener {
            finish()
        }
    }
}