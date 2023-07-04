package com.example.a1_703

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a1_703.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var b:ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.news.setOnClickListener {
            startActivity(Intent(this,News::class.java))
        }

        b.history.setOnClickListener {
            startActivity(Intent(this,HistoryActivity::class.java))
        }

        b.buyTicket.setOnClickListener {
            startActivity(Intent(this,BuyTicket::class.java))
        }

        b.skill.setOnClickListener {
            startActivity(Intent(this,SkillsInfo::class.java))
        }

        b.setting.setOnClickListener {
            startActivity(Intent(this,SettingActivity::class.java))
        }

        b.allTickets.setOnClickListener {
            startActivity(Intent(this,AllTickets::class.java))
        }

    }
}