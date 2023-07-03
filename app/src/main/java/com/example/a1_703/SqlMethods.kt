package com.example.a1_703

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.text.Editable
import android.util.Log

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
}