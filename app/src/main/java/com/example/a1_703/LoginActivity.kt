package com.example.a1_703

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import com.example.a1_703.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var b: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(b.root)
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

        b.pwd.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var txt = b.pwd.text.toString()
                if (txt.contains(" ") || txt.length > 15) {
                    b.pwd.error = "格式錯誤"
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        b.showPwd.setOnClickListener {
            if (b.pwd.inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                b.pwd.inputType = 129
                b.showPwd.setImageDrawable(resources.getDrawable(R.drawable.a1_17))
            } else {
                b.pwd.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD //Showing
                b.showPwd.setImageDrawable(resources.getDrawable(R.drawable.a1_12))
            }
            b.pwd.setSelection(b.pwd.text.length)
        }

        var error = 0
        b.login.setOnClickListener {
            if (SqlMethods.User(this).login(b.edtEmail.text.toString(),b.pwd.text.toString())) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                b.pwd.error = "帳號或密碼錯誤"
                error++
                if (error == 3) {
                    b.login.isEnabled = false
                    Handler().postDelayed(
                        {
                            b.login.isEnabled = true
                        }, 10000)
                    error = 0
                }
            }
        }

        b.reg.setOnClickListener {
            startActivity(Intent(this, RegActivity::class.java))
        }
    }
}