package com.epro.pos.ui.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.epro.pos.R
import razerdp.basepopup.BasePopupWindow

class RefundPopup  (context: Context) : BasePopupWindow(context){

    override fun onCreateContentView(): View {
       val view = LayoutInflater.from(context).inflate(R.layout.refund_order_pop,null)

        return view
    }
}