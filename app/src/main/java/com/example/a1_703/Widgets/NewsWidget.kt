package com.example.a1_703.Widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.example.a1_703.NewsInfoActivity
import com.example.a1_703.R

/**
 * Implementation of App Widget functionality.
 */
class NewsWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget3(context, appWidgetManager, appWidgetId)
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
        if(intent!=null && intent.action=="com.example.a1_703.newClick"){
            var i=Intent(context!!,NewsInfoActivity::class.java)
            i.flags=Intent.FLAG_ACTIVITY_NEW_TASK
            i.putExtra("json",intent.getStringExtra("jsonooo"))
            Log.e("TAG", intent.getStringExtra("json").toString(), )
            context.startActivity(i)
        }
    }
}

internal fun updateAppWidget3(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val views = RemoteViews(context.packageName, R.layout.news_widget)

    views.setRemoteAdapter(R.id.list, Intent(context,NewsListWidgetService::class.java))

    var intent=Intent(context,NewsWidget::class.java)
    intent.action="com.example.a1_703.newClick"
    var pi=PendingIntent.getBroadcast(context,0,intent,0)
    views.setPendingIntentTemplate(R.id.list,pi)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.list)
}