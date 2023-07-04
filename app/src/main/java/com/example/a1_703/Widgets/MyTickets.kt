package com.example.a1_703.Widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.widget.RemoteViews
import com.example.a1_703.AllTickets
import com.example.a1_703.R
import com.example.a1_703.SqlMethods
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter

/**
 * Implementation of App Widget functionality.
 */
class MyTickets : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget2(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if(intent!=null){
            if(intent.action=="com.example.a1_703.myticket_click_lg"){
                var i=Intent(context,AllTickets::class.java)
                i.flags=Intent.FLAG_ACTIVITY_NEW_TASK
                context!!.startActivity(i)
            }
            val views = RemoteViews(context!!.packageName, R.layout.my_tickets)
            if(intent.action=="com.example.a1_703.next"){
                var sp=context.getSharedPreferences("sp",Context.MODE_PRIVATE)
                var widgetNow=sp.getInt("widgetNow",0)
                var cursor= SqlMethods.Tickets(context).getAll()
                var titles= arrayListOf<String>()
                var count= arrayListOf<Int>()
                cursor.moveToFirst()
                for(i in 0 until cursor.count){
                    titles.add(cursor.getString(0))
                    count.add(cursor.getInt(1))
                    cursor.moveToNext()
                }
                if(widgetNow+1==count.count()){
                    return
                }else{
                    sp.edit().putInt("widgetNow",widgetNow+1).commit()
                }
                var ids=AppWidgetManager.getInstance(context!!).getAppWidgetIds(ComponentName(context!!,MyTickets::class.java))
                var widgetManager=AppWidgetManager.getInstance(context!!)
                for (appWidgetId in ids) {
                    updateAppWidget2(context, widgetManager, appWidgetId)
                }
            }

            if(intent.action=="com.example.a1_703.pre"){
                var sp=context.getSharedPreferences("sp",Context.MODE_PRIVATE)
                var widgetNow=sp.getInt("widgetNow",0)
                var cursor= SqlMethods.Tickets(context).getAll()
                var titles= arrayListOf<String>()
                var count= arrayListOf<Int>()
                cursor.moveToFirst()
                for(i in 0 until cursor.count){
                    titles.add(cursor.getString(0))
                    count.add(cursor.getInt(1))
                    cursor.moveToNext()
                }
                if(widgetNow-1<0){
                    return
                }else{
                    sp.edit().putInt("widgetNow",widgetNow-1).commit()
                }
                var ids=AppWidgetManager.getInstance(context!!).getAppWidgetIds(ComponentName(context!!,MyTickets::class.java))
                var widgetManager=AppWidgetManager.getInstance(context!!)
                for (appWidgetId in ids) {
                    updateAppWidget2(context, widgetManager, appWidgetId)
                }
            }
        }
    }
}

internal fun updateAppWidget2(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val views = RemoteViews(context.packageName, R.layout.my_tickets)
    var cursor= SqlMethods.Tickets(context).getAll()
    var titles= arrayListOf<String>()
    var count= arrayListOf<Int>()
    cursor.moveToFirst()
    for(i in 0 until cursor.count){
        titles.add(cursor.getString(0))
        count.add(cursor.getInt(1))
        cursor.moveToNext()
    }
    var sp=context.getSharedPreferences("sp",Context.MODE_PRIVATE)
    var widgetNow=sp.getInt("widgetNow",0)
    if(cursor.count!=0){
        views.setTextViewText(R.id.title,titles[widgetNow])
        views.setTextViewText(R.id.count,"${count[widgetNow]/100}張")
        views.setTextViewText(R.id.total,"共 $${count[widgetNow]} 元")
        var qrcodeContext= "${titles[widgetNow]}\n${count[widgetNow]/100}張\n共 $${count[widgetNow]} 元"
        var map= mapOf(EncodeHintType.CHARACTER_SET to Charsets.UTF_8)
        var martix= MultiFormatWriter().encode(qrcodeContext, BarcodeFormat.QR_CODE,200,200,map)
        var intArray=IntArray(martix.width*martix.height)
        for(i in 0 until 200){
            for(j in 0 until 200){
                var color= Color.WHITE
                if(martix.get(i,j)){
                    color= Color.BLACK
                }
                intArray[i*200+j]=color
            }
        }
        var bitmap= Bitmap.createBitmap(intArray,200,200, Bitmap.Config.ARGB_8888)
        views.setImageViewBitmap(R.id.img,bitmap)
    }else{
        views.setTextViewText(R.id.title,"沒有資料")
        views.setTextViewText(R.id.count,"")
        views.setTextViewText(R.id.total,"")
    }

    var intent=Intent(context,MyTickets::class.java)
    intent.action="com.example.a1_703.myticket_click_lg"
    var pi=PendingIntent.getBroadcast(context,0,intent,0)
    views.setOnClickPendingIntent(R.id.showTicket,pi)

    var intent2=Intent(context,MyTickets::class.java)
    intent2.action="com.example.a1_703.next"
    var pi2=PendingIntent.getBroadcast(context,0,intent2,0)
    views.setOnClickPendingIntent(R.id.next,pi2)

    var intent3=Intent(context,MyTickets::class.java)
    intent3.action="com.example.a1_703.pre"
    var pi3=PendingIntent.getBroadcast(context,0,intent3,0)
    views.setOnClickPendingIntent(R.id.pre,pi3)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}