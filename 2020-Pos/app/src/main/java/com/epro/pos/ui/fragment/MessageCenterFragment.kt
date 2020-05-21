package com.epro.pos.ui.fragment;

import android.os.Bundle
import android.util.Log
import com.epro.pos.R
import com.epro.pos.listener.BackStageCurrentTabChangeEvent
import com.epro.pos.listener.FragmentChanageEvent
import com.epro.pos.mvp.contract.MessageCenterContract
import com.epro.pos.mvp.model.bean.MessageCenterBean
import com.epro.pos.mvp.model.bean.updateReadBean
import com.epro.pos.mvp.presenter.MessageCenterPresenter
import com.epro.pos.ui.adapter.MessageAdapter
import com.epro.pos.utils.PosConst
import com.mike.baselib.fragment.BaseTitleBarListFragment
import com.mike.baselib.utils.AppBusManager
import kotlinx.android.synthetic.main.activity_message_center.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.backgroundColor


class MessageCenterFragment : BaseTitleBarListFragment<MessageCenterBean.MessageCenterOneBean, MessageCenterContract.View, MessageCenterPresenter, MessageAdapter>(), MessageCenterContract.View {
    override fun getListData() {
        mPresenter.MessageCenter(PosConst.NOTICE_MESSAGE, AppBusManager.getUserId(), page)
    }

    override fun initData() {

    }

    override fun layoutContentId(): Int {
        return R.layout.activity_message_center
    }

    override fun getListAdapter(list: ArrayList<MessageCenterBean.MessageCenterOneBean>): MessageAdapter {
        return MessageAdapter(activity!!, list)
    }

    companion object {
        const val TAG = "MessageCenter"
        const val EXTRA = "extra"
        fun newInstance(type: String, extra: String = ""): MessageCenterFragment {
            val args = Bundle()
            args.putString(TAG, type)
            args.putString(EXTRA, extra)
            val fragment = MessageCenterFragment()
            fragment.setArguments(args)
            return fragment
        }

        fun newInstance(): MessageCenterFragment {
            return newInstance("")
        }
    }

    override fun getPresenter(): MessageCenterPresenter {
        return MessageCenterPresenter()
    }

    //更新已读消息
    override fun onUpdateReadMessageSuccess(result: updateReadBean.Result) {

    }

    override fun initView() {
        super.initView()
        logTools.t("YB").d("initView")
        mPresenter.MessageCenter(PosConst.NOTICE_MESSAGE, AppBusManager.getUserId(), page)
        getTitleView().text = getString(R.string.message_center)
        getRvListView().backgroundColor = resources.getColor(R.color.bottomColor)
        listDataAdapter!!.onItemClickListener = object : MessageAdapter.OnItemClickListener {
            override fun onClick(item: MessageCenterBean.MessageCenterOneBean, position: Int) {
                listDataAdapter!!.updateListView(position)
                logTools.t("YB").d("item: "+item.msgType)
                mPresenter.UpdateReadMessages(PosConst.UPDATE_READ, item.id)
                startSkip(item.msgType)
            }
        }
    }

    private fun startSkip(msgType: String) {
        val event = BackStageCurrentTabChangeEvent()
          when(msgType){
              //SYS 系统消息，BIZ:证件更新，REM：续约通知，GRM:商品审核通知，FBM:意见反馈通知，VUM：版本更新通知
             "SYS"->{

             }

             "BIZ"->{
                 event.position = 8
                 event.action = PosConst.BUSINESS_BASE_INFO  //跳转商户资料界面
                 event.extra = TAG+"_"
              }
              "REM"->{
                  event.position = 8
                  event.action = PosConst.BUSINESS_BASE_INFO  //跳转商户资料界面
                  event.extra = TAG+"_"
              }
              "GRM"->{
                  event.position = 2
                  event.action = PosConst.GET_GOODS_FILE_LIST //跳转商品档案
                  event.extra = TAG+"_"
              }
              "FBM"->{
                  event.position = 8
                  event.action = PosConst.COMMIT_FEEDBACK  //意见反馈
                  event.extra = TAG+"_"
              }
              "VUM"->{
                  event.position = 8
                  event.action = PosConst.VERSION_UPDATE  //版本更新
                  event.extra = ""
              }
          }
        EventBus.getDefault().postSticky(event)
    }

    override fun dismissLoading(errorMsg: String, errorCode: Int, type: String) {
            logTools.t("YB").d("dismissLoading success")
               if (listDataAdapter!!.mData.isEmpty()){
                  multipleStatusView.showEmpty()
               }else{
                   multipleStatusView.showContent()
               }
        super.dismissLoading(errorMsg, errorCode, type)
    }

    override fun initListener() {
    }

    override fun lazyLoad() {
        page = 1
        getListData()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            Log.e("YB", "onHiddenChanged")
        }
    }
}


