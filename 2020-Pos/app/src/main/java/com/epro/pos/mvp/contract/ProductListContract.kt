package com.epro.pos.mvp.contract

import com.epro.pos.mvp.model.bean.Product
import com.mike.baselib.base.IPresenter
import com.mike.baselib.base.ListView

interface ProductListContract {

    interface View : ListView<Product> {

    }


    interface Presenter : IPresenter<View> {
        fun getProductList(type: String)
    }
}