package com.example.a1_703

import android.animation.Animator
import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.Animation
import androidx.core.view.drawToBitmap
import coil.load
import coil.transform.BlurTransformation
import com.example.a1_703.Adapter.GridAdapter
import com.example.a1_703.databinding.ActivitySkillsInfoBinding
import org.json.JSONArray
import org.json.JSONObject

class SkillsInfo : AppCompatActivity() {
    lateinit var b:ActivitySkillsInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b= ActivitySkillsInfoBinding.inflate(layoutInflater)
        setContentView(b.root)
        var skills= arrayOf("全部","製造工程技術","營建技術","資訊與通訊技術","運輸與物流","社會與個人服務","藝術與時尚","青少年組","未來職類")
        var skillBtn= arrayOf(b.b1,b.b2,b.b3,b.b4,b.b5,b.b6,b.b7,b.b8,b.b9)
        var skillTxt= arrayOf(b.t1,b.t2,b.t3,b.t4,b.t5,b.t6,b.t7,b.t8,b.t9)
        var jsonText=assets.open("professions.json").bufferedReader().readText()
        var jsonArrayOld=JSONArray(jsonText)
        var jsonArray= arrayListOf<JSONObject>()
        var nowJson=jsonArray


        for(i in 0 until jsonArrayOld.length()){
            jsonArray.add(jsonArrayOld[i] as JSONObject)
        }
        jsonArray.sortBy { it.getString("title") }
        b.b1.setBackgroundColor(Color.GRAY)
        b.t1.setTextColor(Color.WHITE)
        for(i in 0 until skillBtn.size){
            skillTxt[i].text=skills[i]
            skillBtn[i].setOnClickListener {
                for(j in 0 until skillBtn.size){
                    skillBtn[j].setBackgroundColor(Color.WHITE)
                    skillTxt[j].setTextColor(Color.BLACK)
                }
                skillBtn[i].setBackgroundColor(Color.GRAY)
                skillTxt[i].setTextColor(Color.WHITE)
                if(i!=0){
                    var newJson=jsonArray.filter { it.getString("category")==skills[i] }
                    nowJson=newJson as ArrayList<JSONObject>
                    b.grid.adapter=GridAdapter(this,newJson)
                }else{
                    b.grid.adapter=GridAdapter(this,jsonArray)
                }
            }
        }

        b.back.setOnClickListener {
            finish()
        }


        b.grid.adapter=GridAdapter(this,jsonArray)

        var nowPosition=0

        b.pre.setOnClickListener {
            nowPosition--
            b.pre.isEnabled=true
            b.nxt.isEnabled=true
            if(nowPosition-1<0){
                b.pre.isEnabled=false
            }
            if(nowPosition+1==nowJson.size){
                b.nxt.isEnabled=false
            }
            var ins=assets.open("professions/"+(nowJson[nowPosition]).getString("image"))
            b.dialogImg.setImageDrawable(Drawable.createFromStream(ins,""))
            b.dialogTxt.text=(nowJson[nowPosition]).getString("description")
            b.dialogTitle.text=(nowJson[nowPosition]).getString("title")
        }
        b.nxt.setOnClickListener {
            nowPosition++
            b.pre.isEnabled=true
            b.nxt.isEnabled=true
            if(nowPosition-1<0){
                b.pre.isEnabled=false
            }
            if(nowPosition+1==nowJson.size){
                b.nxt.isEnabled=false
            }
            var ins=assets.open("professions/"+(nowJson[nowPosition]).getString("image"))
            b.dialogImg.setImageDrawable(Drawable.createFromStream(ins,""))
            b.dialogTxt.text=(nowJson[nowPosition]).getString("description")
            b.dialogTitle.text=(nowJson[nowPosition]).getString("title")
        }

        b.dialog.setOnClickListener {  }

        b.grid.setOnItemClickListener { parent, view, position, id ->
            nowPosition=position
            b.pre.isEnabled=true
            b.nxt.isEnabled=true
            if(nowPosition-1<0){
                b.pre.isEnabled=false
            }
            if(nowPosition+1==nowJson.size){
                b.nxt.isEnabled=false
            }
            var bac=window.decorView.drawToBitmap()
            var new=Bitmap.createBitmap(bac,0,0,window.decorView.width,window.decorView.height-130)
            b.bac.load(new){
                transformations(
                    BlurTransformation(
                        this@SkillsInfo,
                        3f,5f
                    )
                )
            }
            var ins=assets.open("professions/"+(nowJson[position]).getString("image"))
            b.dialogImg.setImageDrawable(Drawable.createFromStream(ins,""))
            b.dialogTxt.text=(nowJson[position]).getString("description")
            b.dialogTitle.text=(nowJson[position]).getString("title")

            b.bac.visibility= View.VISIBLE
            b.dialog.visibility= View.VISIBLE

            var animate=ValueAnimator.ofFloat(0f,1f)
            animate.duration = 500
            animate.addUpdateListener {
                var value=animate.getAnimatedValue()
                b.dialog.scaleX= value as Float
                b.dialog.scaleY= value
            }

            animate.start()

            var animate2=ValueAnimator.ofFloat(1f,0f)
            animate2.duration = 500
            animate2.addUpdateListener {
                var value=animate2.getAnimatedValue()
                b.dialog.scaleX= value as Float
                b.dialog.scaleY= value
                if(value==0f){
                    b.bac.visibility= View.GONE
                    b.dialog.visibility=View.GONE
                }
            }


            b.bac.setOnClickListener {
                animate2.start()
            }

            b.closeDialog.setOnClickListener {
                animate2.start()
            }
        }

    }
}