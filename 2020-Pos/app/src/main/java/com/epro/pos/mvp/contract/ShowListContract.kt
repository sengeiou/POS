package com.epro.pos.mvp.contract

import com.epro.pos.mvp.model.bean.Cashier
import com.epro.pos.mvp.model.bean.GoodsCategory
import com.mike.baselib.base.IPresenter
import com.mike.baselib.base.ListView

interface ShowListContract {

    interface View<D> : ListView<D> {
        fun onGetGoodsCategoryListSuccess(categorys: ArrayList<GoodsCategory>)
        fun onLowerShelfGoodsSuccess()
        fun onGetCashierListSuccsess(cashiers:ArrayList<Cashier>)
        fun onDeleteGoodsFileSuccess(failList:ArrayList<String>,successList:ArrayList<String>)
    }


    interface Presenter<D> : IPresenter<View<D>> {
        fun ShowList(type: String)
        fun getGoodsCategoryList(type: String)
    }
}