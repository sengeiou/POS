package com.epro.pos.ui.adapter

import android.content.Context
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.AboutUsBean
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter

class AboutUsAdapter (mContext: Context, list:ArrayList<AboutUsBean>, layoutId:Int = R.layout.about_us_item): CommonAdapter<AboutUsBean>(mContext,list,layoutId) {

    var mTitles = arrayOf(mContext.getString(R.string.what_is_eproshop),mContext.getString(R.string.we_service_and_responsibility),mContext.getString(R.string.disclaimer_privacy_policy))
    var mContent = arrayOf(mContext.getString(R.string.what_is_eproshop_content),mContext.getString(R.string.we_service_and_responsibility_content),mContext.getString(R.string.disclaimer_privacy_policy_content))
    init {
        for (i in mTitles.indices){
            list.add(AboutUsBean(0,"",mTitles[i],mContent[i]))
        }
    }
    override fun bindData(holder: ViewHolder, data: AboutUsBean, position: Int) {
        holder.setText(R.id.tvTitle,data.title)
        holder.setText(R.id.tvContent,data.content)
    }
}