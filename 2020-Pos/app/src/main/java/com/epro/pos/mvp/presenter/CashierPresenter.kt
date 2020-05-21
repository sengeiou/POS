package com.epro.pos.mvp.presenter

import com.epro.pos.mvp.contract.CashierContract
import com.epro.pos.mvp.model.CashierModel
import com.epro.pos.utils.PosBusManager
import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.net.exception.ExceptionHandle

class CashierPresenter : BasePresenter<CashierContract.View>(), CashierContract.Presenter {
    override fun getShopProductCategorys(isRefresh: Boolean, type: String) {
        checkViewAttached()
//        val products = ArrayList<Product>()
//        for (i in 0..30) {
//            val info = Product(i.toString(), i.toString(), i + 4.34)
//            products.add(info)
//        }
//        val productCategorys = ArrayList<ProductCategory>()
//        for (i in 0..20) {
//            val productCategory = ProductCategory(i.toString(), i.toString(), products)
//            productCategorys.add(productCategory)
//        }
//        PosBusManager.setShopProductCategorys(productCategorys)
        val categorys = PosBusManager.getShopProductCategorys()
        if (isRefresh) {
            categorys.clear()
        }
        if (categorys.size == 0) {
            mRootView?.showLoading(type)
            val disposable = model.getShopProductCategorys()
                    .subscribe({ bean ->
                        PosBusManager.setShopProductCategorys(bean.result.rows)
                        mRootView?.onGetShopProductCategorysSuccess(bean.result.rows)
                        mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                    }, { throwable ->
                        //处理异常
                        ExceptionHandle.handleException(throwable)
                        mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                        mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    })
            addSubscription(disposable)
        } else {
            mRootView?.onGetShopProductCategorysSuccess(categorys)
        }


    }


    private val model by lazy { CashierModel() }

}