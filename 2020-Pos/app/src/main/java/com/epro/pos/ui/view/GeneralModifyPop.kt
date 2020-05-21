package com.epro.pos.ui.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.epro.pos.R
import razerdp.basepopup.BasePopupWindow

class GeneralModifyPop(context: Context) : BasePopupWindow(context) {
    override fun onCreateContentView(): View {
       val view =LayoutInflater.from(context).inflate(R.layout.general_modify_pop,null)
        return view
    }
}