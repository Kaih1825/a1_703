package com.example.a1_703.Widgets

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.a1_703.R
import com.example.a1_703.SqlMethods

class NewsListWidgetService(): RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return NewsListWidgetFactory(applicationContext)
    }

    class NewsListWidgetFactory(var context: Context): RemoteViewsFactory {
        override fun onCreate() {}

        override fun onDataSetChanged() {}

        override fun onDestroy() {}

        override fun getCount(): Int {
            return SqlMethods.storeNews(context).getNews().count()
        }

        override fun getViewAt(position: Int): RemoteViews {
            var views=RemoteViews(context.packageName, R.layout.news_widget_list)
            var sqlResult=SqlMethods.storeNews(context).getNews()
            views.setTextViewText(R.id.title,sqlResult[position].getString("Title"))
            views.setTextViewText(R.id.date,sqlResult[position].getString("Date").replace("/","-"))
            var intent=Intent(context,NewsWidget::class.java)
            intent.putExtra("jsonooo",sqlResult[position].toString())
            views.setOnClickFillInIntent(R.id.btn_full,intent)
            return views
        }

        override fun getLoadingView(): RemoteViews? {return null}

        override fun getViewTypeCount(): Int {return 1}

        override fun getItemId(position: Int): Long {return position.toLong()}

        override fun hasStableIds(): Boolean {return true}

    }
}