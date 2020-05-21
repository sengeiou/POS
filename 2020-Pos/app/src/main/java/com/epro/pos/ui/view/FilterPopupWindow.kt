package com.epro.pos.ui.view

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.Item
import com.epro.pos.ui.adapter.MineAdapter
import com.mike.baselib.utils.ToastUtil
import razerdp.basepopup.BasePopupWindow

class FilterPopupWindow(context: Context) :BasePopupWindow(context){
    override fun onCreateContentView(): View {
        val rvEntry=LayoutInflater.from(context).inflate(R.layout.layout_custom_spinner_drop_list,null) as RecyclerView
        rvEntry.layoutManager=LinearLayoutManager(context)
        val list=ArrayList<Item>()
        for(i in 0..10){
            list.add(Item(R.mipmap.icon_rocket,""+i))
        }
        rvEntry.adapter=MineAdapter(context,list)
        (rvEntry.adapter as MineAdapter).onItemClickListener=object :MineAdapter.OnItemClickListener{
            override fun onClick(item: Item) {
                ToastUtil.showToast(item.content)
                dismiss()
            }
        }
        return rvEntry
    }


}