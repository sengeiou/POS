package com.epro.pos.mvp.contract

import com.epro.pos.mvp.model.bean.GoodsCategory
import com.epro.pos.mvp.model.bean.Product
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface CreateGoodsContract {

    interface View : IBaseView {
        fun onCreateGoodsSuccess(product:Product)
        fun onGetGoodsCategoryList(categorys: ArrayList<GoodsCategory>)

    }


    interface Presenter : IPresenter<View> {
        fun getGoodsCategoryList(type: String)
    }
}