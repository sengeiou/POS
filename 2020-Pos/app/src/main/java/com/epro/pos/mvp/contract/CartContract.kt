package com.epro.pos.mvp.contract

import com.epro.pos.mvp.model.bean.Product
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter


class CartContract {
    interface View : IBaseView {
        fun onSearchProductSuccess(products: ArrayList<Product>)
    }

    interface Presenter : IPresenter<View> {
    }
}