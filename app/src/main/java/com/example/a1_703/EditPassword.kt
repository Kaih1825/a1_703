package com.example.a1_703

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.example.a1_703.databinding.ActivityEditPasswordBinding
import com.example.a1_703.databinding.ActivityEditProfileBinding

class EditPassword : AppCompatActivity() {
    lateinit var b:ActivityEditPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b=ActivityEditPasswordBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.back.setOnClickListener {
            finish()
        }
        b.pwd1.addTextChangedListener {
            var txt = b.pwd1.text.toString()
            if (txt.contains(" ") || !(txt.contains(Regex("[A-Z]")) && txt.contains(Regex("[a-z]")) && txt.contains(
                    Regex("[0-9]")
                ) && txt.contains(Regex("\\W")))
                || txt.length<8 || txt.length>15
            ) {
                b.pwd1.error = "格式錯誤"
            }
        }

        b.pwd2.addTextChangedListener {
            if(b.pwd2.text.toString()!=b.pwd1.text.toString()){
                b.pwd2.error="兩次密碼不同"
            }
        }

        b.reg.setOnClickListener{
            if(b.pwd2.text.toString()!=b.pwd1.text.toString()){
                b.pwd2.error="兩次密碼不同"
                return@setOnClickListener
            }
            SqlMethods.User(this).changePwd(getSharedPreferences("sp",Context.MODE_PRIVATE).getString("email","")!!,b.pwd1.text.toString())
            finish()
        }
    }
}