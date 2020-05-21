package com.epro.pos.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.pos.mvp.contract.QualificationInfoContract
import com.epro.pos.mvp.model.QualificationInfoModel
import com.epro.pos.mvp.model.bean.UpdateImageBean
import com.mike.baselib.net.exception.ErrorStatus
import java.io.File
import java.util.*

class QualificationInfoPresenter : BasePresenter<QualificationInfoContract.View>(), QualificationInfoContract.Presenter {


    private val model by lazy { QualificationInfoModel() }

    override fun updateQualificationInfo(type: String, idcarNegative:String,idcarPositive:String,idcarValidity:String,legalIdcar:String, legalName:String, license:String, licenseId:String,
                                         licenseNo:String,licensePhoto:String,licenseValidity:String, registration:String, registrationId:String,registrationNo:String,registrationPhoto:String, registrationValidity:String,
                                         shopId:String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.QualificationInfo(idcarNegative, idcarPositive, idcarValidity, legalIdcar, legalName, license, licenseId, licenseNo, licensePhoto, licenseValidity, registration, registrationId, registrationNo, registrationPhoto, registrationValidity, shopId)
                .subscribe({ bean ->
                    mRootView?.onUpdateQualificationInfoSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    override fun QualificationInfo(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.qualificationBaseInfo()
                .subscribe({ bean ->
                    mRootView?.onQualificationInfoSuccess(bean.result)
                    mRootView?.dismissLoading(type, ErrorStatus.SUCCESS,ErrorStatus.SUCCESS_MSG)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    override fun updateImage(type:String, image: File, isCreateThumb:Int,phototype:Int) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.updateImage(image,isCreateThumb)
                .subscribe({ bean ->
                    mRootView?.onUpdateImageSucess(bean,phototype)
                    mRootView?.dismissLoading(type, ErrorStatus.SUCCESS,ErrorStatus.SUCCESS_MSG)
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