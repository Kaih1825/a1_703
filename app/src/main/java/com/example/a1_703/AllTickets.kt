package com.example.a1_703

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.a1_703.Adapter.AllTicketsAdapter
import com.example.a1_703.databinding.ActivityAllStarBinding
import com.example.a1_703.databinding.ActivityAllTicketsBinding

class AllTickets : AppCompatActivity() {
    lateinit var b:ActivityAllTicketsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b=ActivityAllTicketsBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.back.setOnClickListener {
            finish()
        }

        var cursor=SqlMethods.Tickets(this).getAll()
        var titles= arrayListOf<String>()
        var count= arrayListOf<Int>()
        cursor.moveToFirst()
        for(i in 0 until cursor.count){
            titles.add(cursor.getString(0))
            count.add(cursor.getInt(1))
            cursor.moveToNext()
        }
        b.list.adapter=AllTicketsAdapter(this, titles,count)
    }
}