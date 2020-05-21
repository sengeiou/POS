package com.epro.pos.mvp.contract

import com.epro.pos.mvp.model.bean.ProductDetail
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface EditStockGoodsPutinDetailContract {

    interface View : IBaseView {
        fun onGetProductDetailSuccess(productDetail: ProductDetail)
        fun onAddStockPutinSuccess()
        fun onUpdateStockPutinSuccess()
        fun onDeleteStockPutinSuccess()

    }


    interface Presenter : IPresenter<View> {

    }
}