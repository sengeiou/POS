package com.epro.pos.mvp.contract

import com.epro.pos.mvp.model.bean.ProductCategory
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

class CashierContract {
    interface View : IBaseView {
        fun onGetShopProductCategorysSuccess(categorys:ArrayList<ProductCategory>)
    }

    interface Presenter : IPresenter<CashierContract.View> {
        fun getShopProductCategorys(isRefresh:Boolean,type:String)
    }
}