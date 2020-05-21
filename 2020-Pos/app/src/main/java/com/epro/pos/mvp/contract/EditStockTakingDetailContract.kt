package com.epro.pos.mvp.contract

import com.epro.pos.mvp.model.bean.ProductDetail
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface EditStockTakingDetailContract {

    interface View : IBaseView {
        fun onGetProductDetailSuccess(productDetail: ProductDetail)
        fun onAddStockTakingSuccess()
        fun onUpdateStockTakingSuccess()
        fun onDeleteStockTakingSuccess()

    }


    interface Presenter : IPresenter<View> {
    }
}