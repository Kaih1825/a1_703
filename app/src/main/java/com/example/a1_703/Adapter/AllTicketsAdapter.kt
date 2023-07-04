package com.example.a1_703.Adapter

import android.app.Activity
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.core.view.drawToBitmap
import com.example.a1_703.QRCodeDialog
import com.example.a1_703.databinding.AllTicketsListBinding
import com.google.zxing.*
import java.io.File
import java.io.FileOutputStream
import java.lang.Math.abs
import java.time.LocalDateTime
import kotlin.random.Random

class AllTicketsAdapter(var context: Context,var titles:ArrayList<String>,var counts:ArrayList<Int>):BaseAdapter() {
    override fun getCount(): Int {
        return titles.count()
    }

    override fun getItem(position: Int): Any {
        return 0
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var b=AllTicketsListBinding.inflate((context as Activity).layoutInflater)
        b.count.text="${counts[position]/100}張"
        b.total.text="共 $${counts[position]} 元"
        b.title.text= titles[position]
        var qrcodeContext= "${b.title.text}\n${b.count.text}\n${b.total.text}"
        var map= mapOf(EncodeHintType.CHARACTER_SET to Charsets.UTF_8)
        var martix=MultiFormatWriter().encode(qrcodeContext,BarcodeFormat.QR_CODE,200,200,map)
        var intArray=IntArray(martix.width*martix.height)
        for(i in 0 until 200){
            for(j in 0 until 200){
                var color= Color.WHITE
                if(martix.get(i,j)){
                    color=Color.BLACK
                }
                intArray[i*200+j]=color
            }
        }
        var bitmap=Bitmap.createBitmap(intArray,200,200,Bitmap.Config.ARGB_8888)
        b.img.setImageBitmap(bitmap)
        b.img.setOnClickListener {
            QRCodeDialog(context,bitmap).show()
        }
        b.root.setOnClickListener {
            var os=FileOutputStream(File("/storage/emulated/0/Download/","${abs(Random.nextInt())}.png"))
            b.root.drawToBitmap().compress(Bitmap.CompressFormat.PNG,86,os)
//            os.flush()
            Toast.makeText(context,"儲存成功",Toast.LENGTH_SHORT).show()
        }
        return b.root
    }
}