package com.epro.pos.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.pos.mvp.contract.EditBaseInfoContract
import com.epro.pos.mvp.model.EditBaseInfoModel
import com.mike.baselib.net.exception.ErrorStatus

class EditBaseInfoPresenter : BasePresenter<EditBaseInfoContract.View>(), EditBaseInfoContract.Presenter {

    private val model by lazy { EditBaseInfoModel() }

    override fun updateBusinessBaseInfo(type: String, address:String,area:String,areaId:String,city:String,cityId:String,email:String, id:String, latitude:String,longitude:String,
                                        mobile:String,owner:String,province:String, provinceId:String,shopId:String, shopName:String, shopType:String,shopTypeId:String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.updateBusinessBaseInfo(address, area, areaId, city, cityId, email, id, latitude, longitude, mobile, owner, province, provinceId, shopId, shopName, shopType, shopTypeId)
                .subscribe({ bean ->
                    mRootView?.onEditBaseInfoSuccess(bean)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    override fun BusinessBaseInfo(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.businessBaseInfo()
                .subscribe({ bean ->
                    mRootView?.onBusinessInfoSuccess(bean.result)
                    mRootView?.dismissLoading(type, ErrorStatus.SUCCESS,ErrorStatus.SUCCESS_MSG)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    override fun searchAddress(type: String, parentId: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.searchAddress(parentId)
                .subscribe({ bean ->
                    mRootView?.onSearchAddressSuccess(bean.result)
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