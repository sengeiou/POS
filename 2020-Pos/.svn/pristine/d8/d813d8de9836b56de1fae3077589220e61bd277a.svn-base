package com.epro.pos.ui.base


import android.opengl.GLSurfaceView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.classic.common.MultipleStatusView
import com.epro.pos.R
import com.mike.baselib.base.ListPresenter
import com.mike.baselib.base.ListView
import com.mike.baselib.fragment.BaseLazyLoadFragment
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.AppContext
import com.mike.baselib.utils.ToastUtil
import com.mike.baselib.utils.ext_loadGif
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.fragment_base_show_list.*


abstract class BaseShowListFragment<D, V : ListView<D>, P : ListPresenter<D, V>, A : CommonAdapter<D>> :
        BaseLazyLoadFragment<V, P>(), ListView<D> {

    override fun getLayoutId(): Int {
        return R.layout.fragment_base_show_list
    }


    protected var page: Int = 1// 第一页开始
    protected var dataList = ArrayList<D>()
    protected var listDataAdapter: A? = null


    override fun initView() {
        flHeader.removeAllViews()
        flHeader.addView(LayoutInflater.from(activity!!).inflate(customHearder(), flHeader, false))
    }




    override fun initData() {
        rvList.layoutManager = LinearLayoutManager(activity)
        listDataAdapter = getListAdapter(ArrayList())
        rvList.adapter = listDataAdapter
        //设置全局的Header构建器
        AppBusManager.initRefreshUI(refreshLayout)
        refreshLayout.setEnableLoadMore(true)
        refreshLayout.setPrimaryColors(resources.getColor(R.color.white), resources.getColor(R.color.secondaryTextColor))
        (refreshLayout.refreshHeader as ClassicsHeader).setPrimaryColor(resources.getColor(R.color.white))
        (refreshLayout.refreshFooter as ClassicsFooter).setPrimaryColor(resources.getColor(R.color.white))
        refreshLayout.setOnRefreshListener { refreshlayout ->
            page = 1
            getListData()
        }

        refreshLayout.setOnLoadMoreListener { refreshlayout ->
            page += 1
            getListData()
        }
        refreshLayout.setPrimaryColors(resources.getColor(R.color.white), resources.getColor(R.color.mainTextColor))
        //refreshLayout?.autoRefresh()
//        page = 1
//        getListData()

        multipleStatusView.setOnRetryClickListener(View.OnClickListener {
            page= 1
            getListData()
        })
    }

    override fun lazyLoad() {
        page = 1
        getListData()
    }

    fun getMultipleStatusView(): MultipleStatusView {
        return multipleStatusView
    }

    fun getRvListView(): RecyclerView {
        return rvList
    }

    fun getRefreshView(): SmartRefreshLayout {
        return refreshLayout
    }

    /**
     * 获取列表数据
     */

    abstract fun getListData()

    override fun getListDataSuccess(list: List<D>, type: String,total:Int) {
        //  logTools.toJson(list)
        if (page == 1) {
            this.dataList.clear()
            this.dataList.addAll(list)
            listDataAdapter?.setData(this.dataList)
            refreshLayout?.finishRefresh()
            refreshLayout.setNoMoreData(false)
        } else {
            if (list.size == 0) {
                ToastUtil.showToast(getString(R.string.not_more_data))
                refreshLayout?.finishLoadMoreWithNoMoreData()
                return
            }
            this.dataList.addAll(list)
            listDataAdapter?.setData(this.dataList)
            refreshLayout?.finishLoadMore()
        }
    }

    override fun showError(errorMsg: String, errorCode: Int, type: String) {
        ToastUtil.showToast(errorMsg)
        logTools.d(errorMsg)
        if (page == 1) {
            refreshLayout?.finishRefresh()
        } else {
            refreshLayout?.finishLoadMore()
        }
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            multipleStatusView.showNoNetwork()
        } else {
            multipleStatusView.showContent()
        }
    }

    override fun showLoading(type: String) {
        // refreshLayout?.autoRefresh()
        if (refreshLayout.state != RefreshState.Refreshing && refreshLayout.state != RefreshState.Loading && multipleStatusView.viewStatus != MultipleStatusView.STATUS_LOADING) {
            multipleStatusView?.showLoading()
            multipleStatusView.findViewById<ImageView>(R.id.ivLoading).ext_loadGif(R.mipmap.gif_loading)
        }
    }

    override fun dismissLoading(errorMsg: String, errorCode: Int, type: String) {
        if (errorCode == ErrorStatus.SUCCESS) {
            if (this.dataList.size == 0) {
                multipleStatusView?.showEmpty()
            } else {
                multipleStatusView?.showContent()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        page = 1// 第一页开始
        dataList = ArrayList()
        listDataAdapter = null
    }

    abstract fun getListAdapter(list: ArrayList<D>): A

    abstract fun customHearder(): Int

    override fun getListDataSuccess(list: List<D>, type: String) {
        getListDataSuccess(list,type,0)
    }
}


