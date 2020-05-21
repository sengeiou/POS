package com.epro.pos.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.pos.mvp.contract.BusinessInfoContract
import com.epro.pos.mvp.model.BusinessInfoModel
import com.mike.baselib.net.exception.ErrorStatus

class BusinessInfoPresenter : BasePresenter<BusinessInfoContract.View>(), BusinessInfoContract.Presenter {



    override fun QualificationInfo(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.qualificationBaseInfo()
                .subscribe({ bean ->
                    mRootView?.onQualificationInfoSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }


    private val model by lazy { BusinessInfoModel() }

    override fun BusinessBaseInfo(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.businessBaseInfo()
                .subscribe({ bean ->
                    mRootView?.onBusinessInfoSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    override fun RenewShop(type: String, shopId: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.reNewShop(shopId)
                .subscribe({ bean ->
                    mRootView?.onRenewShopSuccess(bean.code)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    //删除提示tips
    override fun deDeleteTips(type: String, shopinfoId: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.deDeleteTips(shopinfoId)
                .subscribe({ bean ->
                    mRootView?.onDeleteSuccess()
                    mRootView?.dismissLoading(type, ErrorStatus.SUCCESS,ErrorStatus.SUCCESS_MSG)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

}