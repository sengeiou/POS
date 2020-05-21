package com.epro.pos.mvp.presenter

import com.mike.baselib.base.ListPresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.pos.mvp.model.bean.Product
import com.epro.pos.mvp.contract.ProductListContract
import com.epro.pos.mvp.model.ProcuctListModel
import com.mike.baselib.net.exception.ErrorStatus

class ProductListPresenter : ListPresenter<Product, ProductListContract.View>(), ProductListContract.Presenter {

    private val model by lazy { ProcuctListModel() }

    override fun getProductList(type: String) {
        checkViewAttached()
//        mRootView?.showLoading(type)
//        val disposable = model.GoodsList()
//                .subscribe({ bean ->
//                    mRootView?.getListDataSuccess(bean.result.rows, type)
//                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
//                }, { throwable ->
//                    //处理异常
//                    ExceptionHandle.handleException(throwable)
//                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
//                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
//                })
//        addSubscription(disposable)
        val list = ArrayList<Product>()
        for (i in 1..20) {
            list.add(Product("" + i, "洗衣粉", (i + 0.96).toString()))
        }
        mRootView?.getListDataSuccess(list, type)
    }

}
