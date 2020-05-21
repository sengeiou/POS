package com.epro.pos.mvp.contract

import com.epro.pos.mvp.model.bean.Shopper
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface SelectShopperContract {

    interface View : IBaseView {
        fun onGetShopperListSuccess(shoppers:ArrayList<Shopper>)
        fun onArrangeDistrubitionSuccess()

    }


    interface Presenter : IPresenter<View> {
        fun getShopperList(type: String)
    }
}