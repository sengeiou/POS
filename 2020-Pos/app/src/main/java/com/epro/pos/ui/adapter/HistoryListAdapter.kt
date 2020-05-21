package com.epro.pos.ui.adapter

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import com.epro.pos.R
import com.epro.pos.listener.FragmentChanageEvent
import com.epro.pos.listener.HistoryDetailSerialNoEvent
import com.epro.pos.mvp.model.bean.HistoryRecordBean
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.textColor
import kotlin.math.log

class HistoryRecordAdapter(mContext: Context, list: ArrayList<HistoryRecordBean.HistoryRecordOneBean>, layoutId: Int = R.layout.history_record_list) :
        CommonAdapter<HistoryRecordBean.HistoryRecordOneBean>(mContext, list, layoutId) {

    interface OnItemClickListener{
        fun onClick(item:HistoryRecordBean.HistoryRecordOneBean)
    }

    var onItemClickListener:OnItemClickListener?=null

    override fun bindData(holder: ViewHolder, data: HistoryRecordBean.HistoryRecordOneBean, position: Int) {
        holder.setText(R.id.tvNum,mContext.getString(R.string.serial_no)+data.serialNo+" "+data.createTime)
      if ("0".equals(data.status.toString())){
          holder.getView<TextView>(R.id.tvStatus).textColor = mContext.resources.getColor(R.color.mainColor)
            holder.setText(R.id.tvStatus,mContext.getString(R.string.wait_to_do))
        }else{
            holder.setText(R.id.tvStatus,mContext.getString(R.string.done))
            holder.getView<TextView>(R.id.tvStatus).textColor = mContext.resources.getColor(R.color.secondaryTextColor)
        }
        var tvContent= holder.getView<TextView>(R.id.tvContent)
        var tvMore = holder.getView<TextView>(R.id.tvMore)
        var tvDetail = holder.getView<TextView>(R.id.tvDetail)
        tvContent.text= data.problem
       if (tvContent.lineCount>3){
            tvContent.maxLines = 3
            tvMore.visibility = View.VISIBLE
        }else{
            tvMore.visibility = View.GONE
        }
        var flag = false
        tvMore.setOnClickListener {
            if (!flag){
                tvMore.text = mContext.getString(R.string.collapse_problem)
                tvContent.maxLines = tvContent.lineCount
                flag = true
            }else{
                tvMore.text = mContext.getString(R.string.expand)
                tvContent.maxLines = 3
                flag = false
            }
        }

        tvDetail.setOnClickListener {
            var msg = FragmentChanageEvent()
            msg.postion = 11
            EventBus.getDefault().postSticky(msg)
            var msg2 = HistoryDetailSerialNoEvent()
            msg2.id = data.id
            EventBus.getDefault().postSticky(msg2)
        }
    }
}
