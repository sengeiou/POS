package com.epro.pos.ui.view

import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.epro.pos.R
import com.mike.baselib.utils.AppContext

class PhoneOrEmailCheckDialog: Dialog {
    var tvTitle: TextView? = null
    var tvDialogContent: TextView? = null
    var btDialogConfirm: Button? = null //确定按钮可通过外部自定义按钮内容

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, themeStyle: Int) : super(context, themeStyle) {
        initView()
    }

    private fun initView() {
        setContentView(R.layout.phone_email_check_dialog)
        tvTitle = findViewById(R.id.tv_dialog_title)
        tvDialogContent = findViewById(R.id.tv_dialog_content)
        btDialogConfirm = findViewById(R.id.bt_dialog_confirm)
    }

    class Builder(val context: Context) {
        var confirmListener: OnConfirmListener? = null
        var title: String? = null
        var content: String? = null
        var confirmText: String? = null

        fun setOnConfirmListener(confirmListener: OnConfirmListener): Builder {
            this.confirmListener = confirmListener
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


        fun create(): PhoneOrEmailCheckDialog {
            val dialog = PhoneOrEmailCheckDialog(context, R.style.Theme_AudioDialog)
            if (!TextUtils.isEmpty(title)) {
                dialog.tvTitle?.text = this.title
            } else {
                dialog.tvTitle?.visibility = View.GONE
            }

            dialog.tvDialogContent?.text = this.content

            dialog.btDialogConfirm?.text = this.confirmText ?: AppContext.getInstance().getString(R.string.confirm)


            if (confirmListener != null) {
                dialog.btDialogConfirm?.setOnClickListener { v -> confirmListener!!.onClick(dialog) }
            }
            return dialog
        }

    }

    // 点击弹窗跳转回调
    interface OnConfirmListener {
        fun onClick(dialog: Dialog)
    }

}