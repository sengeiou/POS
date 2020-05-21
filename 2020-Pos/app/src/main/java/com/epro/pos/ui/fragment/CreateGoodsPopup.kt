package com.epro.pos.ui.fragment;

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.epro.pos.R
import com.epro.pos.listener.CartProductChangeEvent
import com.epro.pos.mvp.contract.CreateGoodsContract
import com.epro.pos.mvp.model.bean.*
import com.epro.pos.mvp.presenter.CreateGoodsPresenter
import com.epro.pos.ui.adapter.GoodsCategoryAdapter
import com.epro.pos.ui.adapter.CreateGoodsAdapter
import com.epro.pos.utils.PosBusManager
import com.epro.pos.utils.PosConst
import com.mike.baselib.fragment.BaseDialogFragment
import com.mike.baselib.utils.*
import kotlinx.android.synthetic.main.popup_create_goods.*
import org.greenrobot.eventbus.EventBus


class CreateGoodsPopup : BaseDialogFragment<CreateGoodsContract.View, CreateGoodsPresenter>(), CreateGoodsContract.View, View.OnClickListener {
    override fun onGetGoodsCategoryList(categorys: ArrayList<GoodsCategory>) {
        cateGoryadapter!!.setData(categorys)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            tvBack -> {
                if (isFirstPage) {
                    dismiss()
                } else {
                    viewFlipper.showNext()
                    isFirstPage = true
                    tvSure.visibility = View.VISIBLE
                    tvTitle.text = getString(R.string.new_product_create)
                    tvBack.text = getString(R.string.delete_dialog_cancel)
                }

            }
            tvSure -> {
                if (createGoodsAdapter!!.checkParams()) {
                    return
                }
                val produtBarCode = createGoodsAdapter!!.mData[0].content2
                val goodsName = createGoodsAdapter!!.mData[1].content2
                val specificationsValueNames = createGoodsAdapter!!.mData[3].content2
                val retailPrice = createGoodsAdapter!!.mData[4].content2
                val buyingPrice = createGoodsAdapter!!.mData[5].content2
                val productNumber = if (TextUtils.isEmpty(createGoodsAdapter!!.mData[6].content2)) 0.toLong() else createGoodsAdapter!!.mData[6].content2.toLong()
                val product = CreateGoodsBean.Product(produtBarCode, productNumber, specificationsValueNames)
                mPresenter.createGoods(product, goodsName, goodsTypeId, goodsTypeName, buyingPrice, retailPrice, PosConst.ADD_SIMPLE_PRODUCT)
            }
        }

    }

    override fun getContentLayoutId(): Int {
        return R.layout.popup_create_goods
    }

    companion object {
        const val TAG = "createGoods"
        const val KEY_WORD = "key_word"
        fun newInstance(productBarCode: String): CreateGoodsPopup {
            val args = Bundle()
            args.putString(KEY_WORD, productBarCode)
            val fragment = CreateGoodsPopup()
            fragment.setArguments(args)
            return fragment
        }

        fun newInstance(): CreateGoodsPopup {
            return newInstance("")
        }
    }

    override fun getPresenter(): CreateGoodsPresenter {
        return CreateGoodsPresenter()
    }

    override fun onCreateGoodsSuccess(product: Product) {
        toast(getString(R.string.add_cart_successfully))
        val cartProduct = CartProduct(if (PosBusManager.isRefund()) -1 else 1, product)
        PosBusManager.setOneCartProduct(cartProduct)
        EventBus.getDefault().post(CartProductChangeEvent())
        dismiss()
    }

    var isFirstPage = true
    var cateGoryadapter: GoodsCategoryAdapter? = null
    var createGoodsAdapter: CreateGoodsAdapter? = null
    var goodsTypeId = ""
    var goodsTypeName = ""
    override fun initData() {
        val unit = AppBusManager.getAmountUnit()
        val titleList = arrayListOf(AppContext.getInstance().getString(R.string.bar_code), AppContext.getInstance().getString(R.string.productname), AppContext.getInstance().getString(R.string.category), AppContext.getInstance().getString(R.string.specification),AppContext.getInstance().getString(R.string.retail_price)+ "($unit)",AppContext.getInstance().getString(R.string.creat_purchase_price)+"($unit)", AppContext.getInstance().getString(R.string.number_of_storage))
        val itemList = ArrayList<CreateGoodsItem>()
        for (i in titleList.indices) {
            val item = CreateGoodsItem(i, titleList[i], i != 2)
            val keyword = arguments!!.getString(KEY_WORD)!!
            if (i == 0 && keyword.isNotEmpty() && keyword.ext_isPureNumber_orDecimal()) {
                item.valueContent2(keyword)
            } else if (i == 1 && !keyword.ext_isPureNumber_orDecimal()) {
                item.valueContent2(keyword)
            }
            itemList.add(item)
        }
        rvList.layoutManager = LinearLayoutManager(activity!!)
        createGoodsAdapter = CreateGoodsAdapter(activity!!, itemList)
        rvList.adapter = createGoodsAdapter
        createGoodsAdapter!!.onItemClickListener = object : CreateGoodsAdapter.OnItemClickListener {
            override fun onClick(item: CreateGoodsItem) {
                if (item.content == getString(R.string.category)) {
                    viewFlipper.showNext()
                    isFirstPage = false
                    tvSure.visibility = View.GONE
                    tvTitle.text = getString(R.string.category)
                    tvBack.text = getString(R.string.come_back)
                }
            }
        }

        rvCategorys.layoutManager = LinearLayoutManager(activity!!)
        cateGoryadapter = GoodsCategoryAdapter(activity!!, ArrayList())
        rvCategorys.adapter = cateGoryadapter
        cateGoryadapter!!.onItemClickListener = object : GoodsCategoryAdapter.OnItemClickListener {
            override fun onClick(item: GoodsCategory) {
                goodsTypeId = item.id
                goodsTypeName = item.typeName
                viewFlipper.showNext()
                isFirstPage = true
                tvSure.visibility = View.VISIBLE
                tvTitle.text = getString(R.string.new_product_create)
                tvBack.text = getString(R.string.cancel)
                for (i in createGoodsAdapter!!.mData.indices) {
                    val c = createGoodsAdapter!!.mData[i]
                    if (c.content == getString(R.string.category)) {
                        c.valueContent2(item.typeName)
                        break
                    }
                }
                rvList.removeAllViews()
                createGoodsAdapter!!.notifyDataSetChanged()
            }
        }
        mPresenter.getGoodsCategoryList(PosConst.GET_GOODS_CATEGORY_LIST)
    }

    override fun initView() {
    }

    override fun initListener() {
        tvBack.setOnClickListener(this)
        tvSure.setOnClickListener(this)
    }

    override fun getWidth(): Int {
        return DisplayManager.getScreenWidth()!! * 4 / 10
    }

    override fun getHeight(): Int {
        return DisplayManager.dip2px(400F)!!
    }

}


