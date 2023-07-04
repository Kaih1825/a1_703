package com.example.a1_703

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.WindowInsets
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.example.a1_703.databinding.ActivityRegBinding

class RegActivity : AppCompatActivity() {
    lateinit var b: ActivityRegBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityRegBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var txt = b.name.text.toString()
                if (txt.contains(" ")) {
                    b.name.error = "不能包含空格"
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        b.tel.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var txt = b.tel.text.toString()
                if (!Regex("^09[0-9]{2}-[0-9]{3}-[0-9]{3}").matches(txt)) {
                    b.tel.error = "格式錯誤"
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        b.edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                TODO("Not yet implemented")
                var txt = b.edtEmail.text.toString()
                if (txt.contains(" ") || !Regex("^[A-Z,a-z,0-9]+@[A-Z,a-z,0-9]+\\.[A-Z,a-z,0-9]{2,4}$").matches(
                        txt
                    ) || txt.length > 30
                ) {
                    b.edtEmail.error = "格式錯誤"
                }
            }

            override fun afterTextChanged(s: Editable?) {
//                TODO("Not yet implemented")
            }

        })

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

        var sex="Boy"
        b.boy.setOnClickListener {
            b.boy.setBackgroundColor(resources.getColor(R.color.blue))
            b.girl.setBackgroundColor(Color.parseColor("#DCDCDC"))
            b.boy.setTextColor(Color.WHITE)
            b.girl.setTextColor(resources.getColor(R.color.blue))
            sex="Boy"
        }

        b.girl.setOnClickListener {
            b.girl.setBackgroundColor(resources.getColor(R.color.blue))
            b.boy.setBackgroundColor(Color.parseColor("#DCDCDC"))
            b.girl.setTextColor(Color.WHITE)
            b.boy.setTextColor(resources.getColor(R.color.blue))
            sex="Girl"
        }

        b.pwd2.addTextChangedListener {
            if(b.pwd2.text.toString()!=b.pwd1.text.toString()){
                b.pwd2.error="兩次密碼不同"
            }
        }

        b.reg.setOnClickListener{
            if(b.pwd1.text.isEmpty() || b.edtEmail.text.isEmpty() || b.name.text.isEmpty() || b.tel.text.isEmpty()){
                b.pwd2.error="有格子沒填"
                return@setOnClickListener
            }
            if(b.pwd2.text.toString()!=b.pwd1.text.toString()){
                b.pwd2.error="兩次密碼不同"
                return@setOnClickListener
            }
            if(SqlMethods.User(this).reg(b.edtEmail.text.toString(),b.name.text.toString(),sex,b.tel.text.toString(),b.pwd1.text.toString())){
                AlertDialog.Builder(this).setPositiveButton("確定"){_,_ ->
//                    startActivity(Intent(this,LoginActivity::class.java))
                    finish()
                }.setTitle("註冊成功").show()
            }else{
                AlertDialog.Builder(this).setPositiveButton("確定"){_,_ ->

                }.setTitle("註冊失敗").show()
            }
        }

    }
}