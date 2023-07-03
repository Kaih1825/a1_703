package com.example.a1_703

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import com.example.a1_703.databinding.ActivityEditProfileBinding

class EditProfile : AppCompatActivity() {
    lateinit var b: ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.back.setOnClickListener {
            finish()
        }

        var sp = getSharedPreferences("sp", Context.MODE_PRIVATE)
        var cursor = SqlMethods.User(this).getInfo(sp.getString("email", "non")!!)
        cursor.moveToFirst()
        b.name.setText(cursor.getString(1).toString())
        b.tel.setText(cursor.getString(3).toString())
        b.edtEmail.setText(cursor.getString(0).toString())
        b.edtEmail.isEnabled=false
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
        var sex = "Boy"
        if (cursor.getString(2) == "Boy") {
            b.boy.setBackgroundColor(resources.getColor(R.color.blue))
            b.girl.setBackgroundColor(Color.parseColor("#DCDCDC"))
            b.boy.setTextColor(Color.WHITE)
            b.girl.setTextColor(resources.getColor(R.color.blue))
            sex = "Boy"
        } else {
            b.girl.setBackgroundColor(resources.getColor(R.color.blue))
            b.boy.setBackgroundColor(Color.parseColor("#DCDCDC"))
            b.girl.setTextColor(Color.WHITE)
            b.boy.setTextColor(resources.getColor(R.color.blue))
            sex = "Girl"
        }

        b.tel.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var txt = b.tel.text.toString()
                if (!Regex("[0-9]{10}").matches(txt)) {
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

        b.boy.setOnClickListener {
            b.boy.setBackgroundColor(resources.getColor(R.color.blue))
            b.girl.setBackgroundColor(Color.parseColor("#DCDCDC"))
            b.boy.setTextColor(Color.WHITE)
            b.girl.setTextColor(resources.getColor(R.color.blue))
            sex = "Boy"
        }

        b.girl.setOnClickListener {
            b.girl.setBackgroundColor(resources.getColor(R.color.blue))
            b.boy.setBackgroundColor(Color.parseColor("#DCDCDC"))
            b.girl.setTextColor(Color.WHITE)
            b.boy.setTextColor(resources.getColor(R.color.blue))
            sex = "Girl"
        }
        b.reg.setOnClickListener {
            SqlMethods.User(this).update(b.edtEmail.text.toString(),b.name.text.toString(),sex,b.tel.text.toString())
            finish()
        }
    }
}