package com.epro.pos.ui.adapter

import android.content.Context
import android.view.View
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.SelectPhotoBean
import com.epro.pos.utils.PosConst
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter

class SelectPhotoAdapter (mContext: Context, itemList: ArrayList<SelectPhotoBean>, layoutId: Int = R.layout.item_selectphote) :
CommonAdapter<SelectPhotoBean>(mContext, itemList, layoutId) {

    // 点击弹窗取消按钮回调
    interface OnItemClickListener {
        fun onClick(item: SelectPhotoBean)
    }

    init {
        itemList.add(SelectPhotoBean(PosConst.Camera_TYPE, "" + mContext.getString(R.string.take_phone)))
        itemList.add(SelectPhotoBean(PosConst.Gallery_TYPE, "" + mContext.getString(R.string.select_from_phone_album)))
        itemList.add(SelectPhotoBean(PosConst.Cancel_TYPE, "" + mContext.getString(R.string.cancel)))
    }


    var onItemClickListener: OnItemClickListener? = null

    override fun bindData(holder: ViewHolder, data: SelectPhotoBean, position: Int) {
        holder.setText(R.id.tvName, data.content)
        if (position==2){
         //   holder.getView<TextView>(R.shopId.tvName).setPadding(0,20,0,0)
            holder.getView<View>(R.id.vLine).visibility =View.GONE
        }
        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onClick(data)
               }
            }
        })
    }

}