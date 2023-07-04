package com.example.a1_703.Widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast
import com.example.a1_703.AllTickets
import com.example.a1_703.R
import com.example.a1_703.TicketInfo

/**
 * Implementation of App Widget functionality.
 */
class GoToMyTicketWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
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
        if(intent!=null && intent.action=="com.example.a1_703.myticket_click"){
            var i=Intent(context,AllTickets::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context!!.startActivity(i)
        }
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.go_to_my_ticket_widget)

    var intent=Intent(context,GoToMyTicketWidget::class.java)
    intent.action="com.example.a1_703.myticket_click"
    var pi=PendingIntent.getBroadcast(context,0,intent,0)
    views.setOnClickPendingIntent(R.id.click,pi)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}