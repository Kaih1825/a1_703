package com.example.a1_703

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.text.Editable
import android.util.Log
import com.example.a1_703.Widgets.MyTickets
import com.example.a1_703.Widgets.NewsWidget
import com.example.a1_703.Widgets.updateAppWidget2
import com.example.a1_703.Widgets.updateAppWidget3
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset

class SqlMethods {
    class User(var context: Context){
        lateinit var database:SQLiteDatabase

        init {
            try{
                database=context.openOrCreateDatabase("sql.db",Context.MODE_PRIVATE,null)
                database.execSQL("CREATE TABLE User (email TEXT PRIMARY KEY,name TEXT,sex TEXT,tel TEXT,pwd TEXT)")
            }catch (ex:java.lang.Exception){}
        }

        fun login(account: String, pwd: String):Boolean{
            var cursor=database.rawQuery("SELECT * FROM User WHERE email='$account'",null)
            if(cursor.count==0)return false

            cursor.moveToFirst()
            var boo=cursor.getString(4)==pwd
            if(boo){
                var sp=context.getSharedPreferences("sp",Context.MODE_PRIVATE)
                var spe=sp.edit()
                spe.putBoolean("isLogin",true)
                spe.putString("email",account)
                spe.commit()
            }
            return boo
        }



        fun reg(email:String,name:String,sex:String,tel:String,pwd:String):Boolean{
            var cursor=database.rawQuery("SELECT * FROM User WHERE email='$email'",null)
            if(cursor.count!=0)return false
            database.execSQL("INSERT INTO User VALUES('$email','$name','$sex','$tel','$pwd')")
            return true
        }

        fun getInfo(email:String):Cursor{
            return database.rawQuery("SELECT * FROM User WHERE email='$email'",null)
        }

        fun update(email:String,name:String,sex:String,tel:String){
            database.execSQL("UPDATE User SET name='$name',sex='$sex',tel='$tel' WHERE email='$email'")
        }

        fun changePwd(email:String,pwd: String){
            database.execSQL("UPDATE User SET pwd='$pwd' WHERE email='$email'")
        }
    }

    class News(var context: Context){
        lateinit var database: SQLiteDatabase
        init {
            database=context.openOrCreateDatabase("sql.db",Context.MODE_PRIVATE,null)
            try{
                database.execSQL("CREATE TABLE news(jsonText TEXT)")
            }catch (ex:Exception){}
        }
        fun starChange(jsonText:String):Boolean{
            var newsJson=jsonText.replace("'","''")
            var cursor=database.rawQuery("SELECT * FROM news WHERE jsonText='$newsJson'",null)
            if(cursor.count==0){
                database.execSQL("INSERT INTO news(jsonText) VALUES('$newsJson')")
                return true
            }else{
                database.execSQL("DELETE FROM news WHERE jsonText='$newsJson'")
                return false
            }
        }

        fun getStar(jsonText: String):Boolean{
            var newsJson=jsonText.replace("'","''")
            var cursor=database.rawQuery("SELECT * FROM news WHERE jsonText='$newsJson'",null)
            return cursor.count!=0
        }
    }

    class storeNews(var context: Context){
        var database: SQLiteDatabase
        init {
            database=context.openOrCreateDatabase("sql.db",Context.MODE_PRIVATE,null)
            try{
                database.execSQL("CREATE TABLE storeNews(jsonText TEXT)")
                var tmpJsonArr= JSONArray(context.assets.open("news.json").bufferedReader().readText())
                for(i in 0 until  tmpJsonArr.length()){
                    database.execSQL("INSERT INTO storeNews(jsonText) VALUES('${tmpJsonArr[i] as JSONObject}')")
                }
            }catch (ex:Exception){}
        }

        fun getNews(): ArrayList<JSONObject> {
            var jsonArray= arrayListOf<JSONObject>()
            var cursor=database.rawQuery("SELECT * FROM storeNews",null)
            cursor.moveToFirst()
            for(i in 0 until cursor.count){
                jsonArray.add(JSONObject(cursor.getString(0)))
                cursor.moveToNext()
            }
            return jsonArray
        }

        fun insertNews(jsonText: String){
            database.execSQL("INSERT INTO storeNews(jsonText) VALUES('$jsonText')")
            var ids=AppWidgetManager.getInstance(context).getAppWidgetIds(ComponentName(context,NewsWidget::class.java))
            for(i in ids){
                updateAppWidget3(context,AppWidgetManager.getInstance(context),i)
            }
        }
    }

    class Tickets(var context: Context) {
        lateinit var database: SQLiteDatabase

        init {
            try {
                database = context.openOrCreateDatabase("sql.db", Context.MODE_PRIVATE, null)
                database.execSQL("CREATE TABLE Tickets (name NTEXT,count INTEGER)")
            } catch (ex: java.lang.Exception) {
            }
        }

        fun insert(name: String,count:String){
            database.execSQL("INSERT INTO Tickets(name,count) VALUES('${String(name.toByteArray(),Charsets.UTF_8)}',$count)")
            var ids=AppWidgetManager.getInstance(context).getAppWidgetIds(ComponentName(context,MyTickets::class.java))
            for(i in ids){
                updateAppWidget2(context,AppWidgetManager.getInstance(context),i)
            }
        }

        fun getAll(): Cursor {
            return database.rawQuery("SELECT * FROM Tickets",null)
        }
    }
}