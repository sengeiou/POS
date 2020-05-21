package com.epro.pos.ui.adapter

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.LanguageSelectBean
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import org.jetbrains.anko.textColor

class LanguageAdapter(mContext:Context, list:ArrayList<LanguageSelectBean>, layoutId:Int = R.layout.language_select_item):CommonAdapter<LanguageSelectBean>(mContext,list,layoutId) {

    var selectPosition:Int? =null
    var comfirmPosition:Int?=null
    var onItemClickListener:OnItemClickListener?=null
    val STATUS_CLICK:String = "status_click"
    var flag = false
    var  prefs: SharedPreferences =PreferenceManager.getDefaultSharedPreferences(mContext)
    //当列表数据发生变化时,用此方法来更新列表
    fun updateListView(select: Int) {
        this.selectPosition = select
        notifyDataSetChanged()
    }

    fun updateSelect(type : Int){
        this.comfirmPosition = type
        notifyDataSetChanged()
    }
    interface OnItemClickListener{
        fun onClick(item:LanguageSelectBean)
    }

    override fun bindData(holder: ViewHolder, data: LanguageSelectBean, position: Int) {
          holder.setText(R.id.tvLang,data.title)
          var click =prefs.getInt(STATUS_CLICK,0)
          if (2 == position){
              holder.getView<View>(R.id.lineView).visibility = View.GONE
          }else{
               holder.getView<View>(R.id.lineView).visibility = View.VISIBLE
          }
        holder.getView<ImageView>(R.id.imgSelect).visibility = View.GONE
        if (comfirmPosition == position){
              prefs.edit().putInt(STATUS_CLICK,position).commit()
        }
        if (selectPosition == position){
            holder.getView<ImageView>(R.id.imgSelect).visibility = View.VISIBLE
        }else if (position == click){
            if (!flag){
                holder.getView<ImageView>(R.id.imgSelect).visibility = View.VISIBLE
                flag = true
            }else{
                holder.getView<ImageView>(R.id.imgSelect).visibility = View.GONE
            }
        }else{
            holder.getView<ImageView>(R.id.imgSelect).visibility = View.GONE
        }
          holder.setOnItemClickListener(object : View.OnClickListener {
              override fun onClick(view: View?) {
                  if (onItemClickListener != null) {
                      onItemClickListener!!.onClick(data)
                  }
              }
          })
    }
}