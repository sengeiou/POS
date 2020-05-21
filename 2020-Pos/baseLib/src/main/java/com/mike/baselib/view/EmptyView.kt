package com.mike.baselib.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.mike.baselib.R
import kotlinx.android.synthetic.main.layout_empty_view.view.*
import org.jetbrains.anko.imageResource

class EmptyView(context: Context) : LinearLayout(context), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v) {

        }
    }

    init {
        initView()
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.layout_empty_view, this, true)
    }

    class Builder(context: Context) {
        private var imageRes = R.mipmap.no_data
        private var showText1 = ""
        private var showText2 = ""
        private var context: Context? = null
        var clickListener: View.OnClickListener? = null

        init {
            this.context = context
        }

        fun setImageResoue(imageRes: Int): Builder {
            this.imageRes = imageRes
            return this
        }

        fun setShowText1(showText1: String): Builder {
            this.showText1 = showText1
            return this
        }

        fun setShowText2(showText2: String): Builder {
            this.showText2 = showText2
            return this
        }

        fun setClickLisener(listener: OnClickListener): Builder {
            this.clickListener = listener
            return this
        }

        fun create(): EmptyView {
            val view = EmptyView(context!!)
            view.ivImage.imageResource = imageRes
            view.tvLeft.text = showText1
            view.tvRight.text = showText2
            view.tvRight.setOnClickListener(clickListener)
            return view
        }
    }


}