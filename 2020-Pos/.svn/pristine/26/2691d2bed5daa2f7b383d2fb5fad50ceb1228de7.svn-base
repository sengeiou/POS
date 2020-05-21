package com.epro.pos.ui.view

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.TextView
import com.epro.pos.R
import com.mike.baselib.utils.AppUtils
import kotlinx.android.synthetic.main.layout_custom_searchview.view.*
import com.mike.baselib.utils.toast

class CustomSearchView(context: Context, attributeSet: AttributeSet) : LinearLayout(context, attributeSet), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v) {

        }
    }

    var svHint = ""

    var hint = ""
        set(value) {
            etSearch.setHint(value)
        }

    init {
        val t = getContext().obtainStyledAttributes(attributeSet, R.styleable.CustomSearchView)
        svHint = t.getString(R.styleable.CustomSearchView_custom_sv_hint).toString()
        t.recycle()
        initView()
    }

    interface OnQueryClickListner{
        fun onQueryClick(content:String)
    }
    var onQueryClickListner:OnQueryClickListner?=null
    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.layout_custom_searchview, this, true)
        etSearch.hint = svHint
        etSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                if (p1 == EditorInfo.IME_ACTION_SEARCH) {
                    if(context is Activity){
                        AppUtils.closeKeyboard(context as Activity)
                    }
                    val content = etSearch.text.toString().trim()
                    onQueryClickListner?.onQueryClick(content)
                }
                return false
            }
        })
    }

    fun  getSearchText():String{
        return etSearch.text.toString().trim()
    }
    fun setInputType(inputType:Int){
        etSearch.inputType=inputType
    }
    fun setSearchText(content: String){
        etSearch.setText(content)
    }

}