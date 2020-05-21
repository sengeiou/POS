package com.epro.pos.ui.fragment;

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.TypedValue
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import com.mike.baselib.fragment.BaseTitleBarCustomFragment
import com.epro.pos.R
import com.epro.pos.mvp.contract.GoodsFileDetailContract
import com.epro.pos.mvp.model.bean.GoodsFileDetail
import com.epro.pos.mvp.model.bean.Item
import com.epro.pos.mvp.model.bean.Product
import com.epro.pos.mvp.presenter.GoodsFileDetailPresenter
import com.epro.pos.ui.adapter.DetailChildAdapter
import com.epro.pos.ui.adapter.GoodsFileDetailProductAdapter
import com.epro.pos.utils.PosBusManager
import com.epro.pos.utils.PosConst
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.AppContext
import com.mike.baselib.utils.DisplayManager
import kotlinx.android.synthetic.main.fragment_goods_file_detail.*
import kotlinx.android.synthetic.main.item_order_detail_parent.rvItems

/**
 * 商品档案详情
 */
class GoodsFileDetailFragment : BaseTitleBarCustomFragment<GoodsFileDetailContract.View, GoodsFileDetailPresenter>(), GoodsFileDetailContract.View {

    val titles = arrayListOf(AppContext.getInstance().getString(R.string.product_id), AppContext.getInstance().getString(R.string.product_name), AppContext.getInstance().getString(R.string.brand), AppContext.getInstance().getString(R.string.product_category), AppContext.getInstance().getString(R.string.unit), AppContext.getInstance().getString(R.string.product_style), AppContext.getInstance().getString(R.string.total_inventory))
    val unit = AppBusManager.getAmountUnit()
    val titleList = arrayListOf(AppContext.getInstance().getString(R.string.product_photos), AppContext.getInstance().getString(R.string.barcode), AppContext.getInstance().getString(R.string.product_specifications), AppContext.getInstance().getString(R.string.initial_inventory),AppContext.getInstance().getString(R.string.my_job_4),AppContext.getInstance(). getString(R.string.creat_purchase_price)+"($unit)", AppContext.getInstance().getString(R.string.retail_price)+"($unit)",AppContext.getInstance(). getString(R.string.online_shop_price)+"($unit)")
    val weights = arrayListOf(1F, 2F, 1F, 1F, 1F, 1F, 1F, 1F)
    override fun onGetGoodsFileDetailSuccess(goodsFileDetail: GoodsFileDetail) {
        var goodsNumber=0
        if(goodsFileDetail.productList.isNotEmpty()){
            for(p in goodsFileDetail.productList){
                goodsNumber+=p.productNumber
            }
        }
        val items = ArrayList<Item>()
        val goodsInfo= arrayListOf(goodsFileDetail.goodsID,goodsFileDetail.goodsName,goodsFileDetail.brandName
                ,goodsFileDetail.goodsTypeName,goodsFileDetail.goodsUnitName,PosBusManager.getGoodsTypeText(goodsFileDetail.goodsBinding),goodsNumber.toString())
        for (i in titles.indices) {
            val item = Item(i, titles[i]).valueContent2(goodsInfo[i])
            items.add(item)
        }
        rvItems.layoutManager = GridLayoutManager(activity!!, 2)
        rvItems.adapter = DetailChildAdapter(activity!!, items)

        //添加头标题
        for (i in titleList.indices) {
            val tv = TextView(activity)
            tv.text = titleList[i]
            tv.paint.isFakeBoldText=true
            tv.gravity= Gravity.CENTER_VERTICAL
            val padding= DisplayManager.dip2px(5f)!!
            tv.setPadding(padding,padding,padding,padding)
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12.toFloat())
            tv.setTextColor(resources.getColor(R.color.mainTextColor))
            val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, if (weights.size == 0) 1.toFloat() else weights[i])
            tv.layoutParams = params
            llTitle.addView(tv)
        }

        rvProducts.layoutManager = LinearLayoutManager(activity!!)
        val adapter = GoodsFileDetailProductAdapter(activity!!, goodsFileDetail.productList)
        adapter.textWeights = weights
        rvProducts.adapter = adapter
    }

    companion object {
        const val TAG = "GoodsFileDetail"
        const val GOODS_ID= "goodsId"
        fun newInstance(goodsId:String): GoodsFileDetailFragment {
            val args = Bundle()
            args.putString(GOODS_ID, goodsId)
            val fragment = GoodsFileDetailFragment()
            fragment.setArguments(args)
            return fragment
        }

        fun newInstance(): GoodsFileDetailFragment {
            return newInstance("")
        }
    }

    override fun getPresenter(): GoodsFileDetailPresenter {
        return GoodsFileDetailPresenter()
    }

    override fun layoutContentId(): Int {
        return R.layout.fragment_goods_file_detail
    }

    override fun initData() {

    }

    override fun initView() {
        super.initView()
        setHaveBackView(true)
        getTitleView().text=getString(R.string.files_detail)
        getLeftTitleView().text=getString(R.string.goods_files)
    }

    override fun initListener() {
    }

    override fun lazyLoad() {
           val goodsId=arguments!!.getString(GOODS_ID)
           mPresenter.getGoodsFileDetail(goodsId,PosConst.GET_GOODS_FILE_DETAIL)
    }

}


