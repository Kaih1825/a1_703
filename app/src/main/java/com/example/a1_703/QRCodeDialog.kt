package com.example.a1_703

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.WindowManager
import com.example.a1_703.databinding.QrcodeDialogBinding

class QRCodeDialog(var mContext: Context,var bitmap:Bitmap):Dialog(mContext) {
    lateinit var b:QrcodeDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b=QrcodeDialogBinding.inflate(layoutInflater)
        window!!.decorView.background=null
        setContentView(b.root)
        b.img.setImageBitmap(bitmap)
    }

    override fun show() {
        super.show()
    }
}