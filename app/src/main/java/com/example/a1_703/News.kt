package com.example.a1_703

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a1_703.Adapter.ViewPagerAdapter
import com.example.a1_703.databinding.ActivityNewsBinding

class News : AppCompatActivity() {
    lateinit var b:ActivityNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b= ActivityNewsBinding.inflate(layoutInflater)
        setContentView(b.root)

        var images= arrayOf(R.drawable.banner1,R.drawable.banner2,R.drawable.banner3,R.drawable.banner4)
        b.viewPager.adapter=ViewPagerAdapter(this,images)
        b.viewPager.offscreenPageLimit=3
    }
}