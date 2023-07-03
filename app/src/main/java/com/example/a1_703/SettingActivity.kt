package com.example.a1_703

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.a1_703.databinding.ActivitySettingBinding
import com.example.a1_703.databinding.ActivitySkillsInfoBinding

class SettingActivity : AppCompatActivity() {
    lateinit var b:ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b= ActivitySettingBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.back.setOnClickListener {
            finish()
        }

        b.editProfile.setOnClickListener {
            startActivity(Intent(this,EditProfile::class.java))
        }

        b.changePwd.setOnClickListener {
            startActivity(Intent(this,EditPassword::class.java))
        }

        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            b.themwMode.isChecked=true
        }
        b.themwMode.setOnCheckedChangeListener { buttonView, isChecked ->
            var spe=getSharedPreferences("sp",Context.MODE_PRIVATE).edit()
            if(isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                spe.putInt("ThemeMode",AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                spe.putInt("ThemeMode",AppCompatDelegate.MODE_NIGHT_NO)
            }
            spe.commit()
        }

        b.logout.setOnClickListener {
            var spe=getSharedPreferences("sp",Context.MODE_PRIVATE).edit()
            spe.putBoolean("isLogin",false).commit()
            startActivity(Intent(this,LoginActivity::class.java))
            finishAffinity()
        }
    }
}