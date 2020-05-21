package com.epro.pos.ui.adapter

import android.content.Context
import android.view.View
import com.epro.pos.R
import com.epro.pos.listener.FragmentChanageEvent
import com.epro.pos.listener.ProblemClickEvent
import com.epro.pos.mvp.model.bean.HelpCenterBean
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import com.umeng.commonsdk.debug.D
import org.greenrobot.eventbus.EventBus

class HelpCenterAdapter(mContext: Context, list: ArrayList<String>, layoutId: Int = R.layout.help_center_list) : CommonAdapter<String>(mContext, list, layoutId) {



    override fun bindData(holder: ViewHolder, data: String, position: Int) {

        holder.setText(R.id.tvHelpTitle,data)
        holder.setOnItemClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                var msg = FragmentChanageEvent()
                msg.postion = 12
                EventBus.getDefault().postSticky(msg)

                //点击的问题类别
                var event = ProblemClickEvent()
                event.position = position
                EventBus.getDefault().postSticky(event)
            }
        })
    }

}