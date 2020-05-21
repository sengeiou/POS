package com.mike.baselib.fragment

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.classic.common.MultipleStatusView
import com.mike.baselib.R
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.AppContext
import com.mike.baselib.utils.ToastUtil
import com.mike.baselib.utils.ext_loadGif
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.activity_base_custom.*

abstract class BaseCustomFragment<V: IBaseView,P: IPresenter<V>>: BaseLazyLoadFragment<V, P>(){
    override fun initView() {
        setRefreshEnable(false)
        smartRefreshLayout.setEnableLoadMore(false)
        AppBusManager.initRefreshUI(smartRefreshLayout)
        smartRefreshLayout.setOnRefreshListener { refreshlayout ->
            logTools.d("do here1")
            lazyLoad()
        }
        flContent.removeAllViews()
        flContent.addView(LayoutInflater.from(activity!!).inflate(layoutContentId(),null))

        multipleStatusView.setOnRetryClickListener(View.OnClickListener {
            lazyLoad()
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_base_custom
    }

    abstract fun layoutContentId():Int

    override fun showError(errorMsg: String, errorCode: Int, type: String) {
        super.showError(errorMsg, errorCode, type)
        ToastUtil.showToast(errorMsg)
        logTools.d(errorMsg)
        finishRefresh()
        if(errorCode==ErrorStatus.NETWORK_ERROR){
            multipleStatusView.showNoNetwork()
        }else{
            multipleStatusView.showContent()
        }
    }

    override fun showLoading(type: String) {
        if(smartRefreshLayout?.state != RefreshState.Refreshing&&multipleStatusView.viewStatus!=MultipleStatusView.STATUS_LOADING){
            multipleStatusView.showLoading()
            multipleStatusView.findViewById<ImageView>(R.id.ivLoading).ext_loadGif(R.mipmap.gif_loading)
        }
    }

    override fun dismissLoading(errorMsg: String, errorCode: Int, type: String) {
        if(errorCode==ErrorStatus.SUCCESS){
            multipleStatusView.showContent()
        }
        finishRefresh()
    }


    fun getMultipleStatusView(): MultipleStatusView {
        return multipleStatusView
    }

    fun getRefreshLayout(): SmartRefreshLayout {
        return smartRefreshLayout
    }
    /**
     * 开关刷新功能
     */

    fun setRefreshEnable(isEnable: Boolean) {
        getRefreshLayout().setEnableRefresh(isEnable)
    }

    /**
     * 结束刷新
     */
    fun finishRefresh() {
        getRefreshLayout().finishRefresh()
    }

}


