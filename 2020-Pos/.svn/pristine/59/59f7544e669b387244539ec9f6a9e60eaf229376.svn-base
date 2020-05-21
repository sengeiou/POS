package com.epro.pos.mvp.contract

import com.epro.pos.mvp.model.bean.GoodsCategoryCount
import com.epro.pos.mvp.model.bean.Product
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface SelectGoodsContract {

    interface View : IBaseView {
        fun onGetGoodsCategoryCountList(categoryCounts: ArrayList<GoodsCategoryCount>)
        fun onGetProductList(products: ArrayList<Product>)
    }


    interface Presenter : IPresenter<View> {
        fun getGoodsCategoryCountList(type: String)
    }
}