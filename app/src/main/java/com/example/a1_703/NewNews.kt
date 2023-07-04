package com.example.a1_703

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.util.Log
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import com.example.a1_703.databinding.ActivityNewNewsBinding
import com.example.a1_703.databinding.ActivityNewsBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.CalendarConstraints.DateValidator
import com.google.android.material.datepicker.MaterialDatePicker
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import javax.xml.validation.Validator

class NewNews : AppCompatActivity() {
    lateinit var b: ActivityNewNewsBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityNewNewsBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.type.setText("全國賽")
        b.ptype.setText("一般使用者")
        var typeAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            listOf("全國賽", "身障賽", "國際賽", "展能節", "達人盃")
        )
        b.type.setAdapter(typeAdapter)
        var pTypeAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            listOf("系統管理者", "活動管理", "一般使用者")
        )
        b.ptype.setAdapter(pTypeAdapter)
        var nowTime = LocalDateTime.now()
        var formator = DateTimeFormatter.ofPattern("yyyy/MM/dd")

        b.date.setText(formator.format(nowTime))

        var validator = object : DateValidator {
            override fun describeContents(): Int {
                return 0
            }

            override fun writeToParcel(dest: Parcel, flags: Int) {}

            override fun isValid(date: Long): Boolean {
                return Date(date) <= Calendar.getInstance().time
            }

        }
        var datePicker = MaterialDatePicker.Builder.datePicker().setCalendarConstraints(
            CalendarConstraints.Builder().setValidator(validator).build()
        ).build()

        b.date.setOnClickListener {
            if(datePicker.isAdded){
                return@setOnClickListener
            }
            datePicker.show(supportFragmentManager, "")
        }

        datePicker.addOnPositiveButtonClickListener {
            b.date.setText(SimpleDateFormat("yyyy/MM/dd").format(it))
        }

        b.add.setOnClickListener {
            var jsonText=JSONObject(hashMapOf("Date" to b.date.text.toString(),"Type" to b.type.text.toString(),"Title" to b.title.text.toString(),"Content" to b.context.text.toString(),"Announcer" to b.ptype.text.toString(),"LinkText" to b.linktext.text.toString(),"Url" to b.url.text.toString(),"PIC" to if (b.pics.text.toString().isEmpty()) listOf<Any>() else  b.pics.text.toString().split("\n")) as Map<*, *>).toString()
            SqlMethods.storeNews(this).insertNews(jsonText)
            finish()
        }

        b.back.setOnClickListener {
            finish()
        }
    }
}