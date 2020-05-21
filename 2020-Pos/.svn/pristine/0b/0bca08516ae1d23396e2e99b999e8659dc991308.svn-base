package com.epro.pos.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.pos.mvp.contract.SelectShopperContract
import com.epro.pos.mvp.model.SelectShopperModel
import com.mike.baselib.net.exception.ErrorStatus

class SelectShopperPresenter : BasePresenter<SelectShopperContract.View>(), SelectShopperContract.Presenter {

    private val model by lazy { SelectShopperModel() }

    override fun getShopperList(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getShopperList()
                .subscribe({ bean ->
                    mRootView?.onGetShopperListSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    fun arrangeDistribution(courierId: String, courierName: String, orderSns: ArrayList<String>, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.arrangeDistribution(courierId, courierName, orderSns)
                .subscribe({ bean ->
                    mRootView?.onArrangeDistrubitionSuccess()
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

}