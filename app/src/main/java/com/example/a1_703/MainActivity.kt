package com.example.a1_703

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var sp=getSharedPreferences("sp",Context.MODE_PRIVATE)
        AppCompatDelegate.setDefaultNightMode(sp.getInt("ThemeMode",AppCompatDelegate.MODE_NIGHT_NO))
        Handler().postDelayed({
            var sp=getSharedPreferences("sp", Context.MODE_PRIVATE)
            if(sp.getBoolean("isLogin",false)){
                startActivity(Intent(this, HomeActivity::class.java))
            }else{
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, 2000)
    }
}