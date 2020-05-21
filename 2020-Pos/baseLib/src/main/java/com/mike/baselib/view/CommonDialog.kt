package com.mike.baselib.view

import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.mike.baselib.R
import kotlinx.android.synthetic.main.dialog_common.*

class CommonDialog : Dialog {
    var tvTitle: TextView? = null
    var tvDialogContent: TextView? = null
    var btDialogConfirm: TextView? = null //确定按钮可通过外部自定义按钮内容
    var btDialogCancel: TextView? = null //取消
    var vLine:View?=null

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, themeStyle: Int) : super(context, themeStyle) {
        initView()
    }

    private fun initView() {
        setContentView(R.layout.dialog_common)
        setCanceledOnTouchOutside(true)
        tvTitle = findViewById(R.id.tv_dialog_title)
        tvDialogContent = findViewById(R.id.tv_dialog_content)
        btDialogConfirm = findViewById(R.id.bt_dialog_confirm)
        btDialogCancel = findViewById(R.id.bt_dialog_cancel)
        vLine = findViewById(R.id.vLine)
    }

    class Builder(val context: Context) {
        var confirmListener: OnConfirmListener=object :OnConfirmListener{
            override fun onClick(dialog: Dialog) {
                dialog.dismiss()
            }
        }
        var cancelListener: OnCancelListener=object :OnCancelListener{
            override fun onClick(dialog: Dialog) {
                dialog.dismiss()
            }
        }
        var title: String? = null
        var content: String? = null
        var confirmText: String? = null
        var cancelText: String? = null
        var cancelIsVisibility: Boolean = true
        var canceledOnTouchOutside=false
        var customContentView:View?=null

        fun setOnConfirmListener(confirmListener: OnConfirmListener): Builder {
            this.confirmListener = confirmListener
            return this
        }

        fun setOnCancelListener(cancelListener: OnCancelListener): Builder {
            this.cancelListener = cancelListener
            return this
        }

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setContent(content: String): Builder {
            this.content = content
            return this
        }

        // 点击确定按钮的文字
        fun setConfirmText(confirmText: String): Builder {
            this.confirmText = confirmText
            return this
        }

        fun setCancelIsVisibility(cancelIsVisibility: Boolean): Builder {
            this.cancelIsVisibility = cancelIsVisibility
            return this
        }

        //取消按钮的文字
        fun setCancelText(cancelText: String): Builder {
            this.cancelText = cancelText
            return this
        }

        fun  setCanceledOnTouchOutside(canceledOnTouchOutside:Boolean): Builder {
            this.canceledOnTouchOutside=canceledOnTouchOutside
            return this
        }

        fun setCustomContentView(customContentView:View):Builder{
            this.customContentView=customContentView
            return this
        }

        fun create(): CommonDialog {
            val dialog = CommonDialog(context, R.style.Theme_AudioDialog)
            if (!TextUtils.isEmpty(title)) {
                dialog.tvTitle?.text = this.title
            } else {
                //dialog.tvTitle?.visibility = View.GONE
            }

            if(customContentView!=null){
                dialog.llContainer.removeAllViews()
                dialog.llContainer.addView(customContentView)
            }else{
                dialog.tvDialogContent?.text = this.content
            }

            dialog.btDialogConfirm?.text = this.confirmText ?: context.resources.getString(R.string.confirm)
            if (this.cancelIsVisibility!!) {
                dialog.btDialogCancel?.text = this.cancelText ?: context.resources.getString(R.string.cancel)
            } else {
                dialog.btDialogCancel?.visibility = View.GONE
                dialog.vLine?.visibility=View.GONE
            }

            if (cancelListener != null) {
                dialog.btDialogCancel?.setOnClickListener { v -> cancelListener!!.onClick(dialog) }
            }
            if (confirmListener != null) {
                dialog.btDialogConfirm?.setOnClickListener { v -> confirmListener!!.onClick(dialog) }
            }

            dialog.setCanceledOnTouchOutside(canceledOnTouchOutside)
            return dialog
        }

    }

    // 点击弹窗取消按钮回调
    interface OnCancelListener {
        fun onClick(dialog: Dialog)
    }

    // 点击弹窗跳转回调
    interface OnConfirmListener {
        fun onClick(dialog: Dialog)
    }

}