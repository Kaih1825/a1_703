package com.example.a1_703

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a1_703.databinding.ActivityTicketInfoBinding

class TicketInfo : AppCompatActivity() {
    lateinit var b:ActivityTicketInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b=ActivityTicketInfoBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.title.text=intent.getStringExtra("Title")
        b.subTitle.text=intent.getStringExtra("Subtitle")
        b.context.text=intent.getStringExtra("Context")
        b.title.setTextColor(intent.getIntExtra("Color",0))
        b.subTitle.setTextColor(intent.getIntExtra("Color",0))

        b.back.setOnClickListener {
            finish()
        }

        b.add.setOnClickListener {
            var count=b.num.text.toString().toInt()
            if(count!=10){
                count++
            }
            b.add.isEnabled = count != 10
            b.min.isEnabled = count != 1
            b.num.text=count.toString()
            b.total.text="共 $${count*100} 元"
        }

        b.min.setOnClickListener {
            var count=b.num.text.toString().toInt()
            if(count!=1){
                count--
            }
            b.min.isEnabled = count != 1
            b.add.isEnabled = count != 10
            b.num.text=count.toString()
            b.total.text="共 $${count*100} 元"
        }

        b.buy.setOnClickListener {
            var txt=b.total.text.toString().split(" ")[1]
            SqlMethods.Tickets(this).insert(b.title.text.toString(),txt.substring(1,txt.length))
            finish()
        }

    }
}