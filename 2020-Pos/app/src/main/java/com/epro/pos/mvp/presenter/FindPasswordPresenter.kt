package com.epro.pos.mvp.presenter

import com.epro.pos.R
import com.epro.pos.mvp.contract.FindPasswordContract
import com.epro.pos.mvp.model.FindPasswordModel
import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.net.exception.ExceptionHandle
import com.mike.baselib.utils.ValidateUtils

class FindPasswordPresenter : BasePresenter<FindPasswordContract.View>(), FindPasswordContract.Presenter {


    private val model by lazy { FindPasswordModel() }
    override fun findPassword(account:String,password:String,passwordConfirm:String,code: String,authType:String,type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.findPassword(account, password, passwordConfirm,code,authType)
                .subscribe({ bean ->
                    mRootView?.onFindPasswordSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    override fun getVfCode(account: String, getType: Int, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getVfCode(account, getType)
                .subscribe({ bean ->
                    mRootView?.onGetVfCodeSuccess(bean.result)
                    mRootView?.dismissLoading(type, ErrorStatus.SUCCESS,ErrorStatus.SUCCESS_MSG)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    override fun login(account:String,password:String,loginType:String,type:String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.login(account,password,loginType)
                .subscribe({
                    bean->
                    mRootView?.loginSuccessed(bean.result)
                    mRootView?.dismissLoading(type, ErrorStatus.SUCCESS,ErrorStatus.SUCCESS_MSG)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    fun checkParams(mobile:String,code:String):Boolean{
        if(!ValidateUtils.validatePhoneNo(mobile)){
            toast(R.string.phone_format_error)
            return false
        }

        if(!ValidateUtils.validateVfcode(code)){
            toast(R.string.vfcode_format_error)
            return false
        }
        return true
    }
    fun checkPhone(mobile:String):Boolean{
        if(!ValidateUtils.validatePhoneNo(mobile)){
            toast(R.string.phone_format_error)
            return false
        }
        return true
    }
    fun checkParams2(email:String,code:String):Boolean{
        if(!ValidateUtils.validateEmail(email)){
            toast(R.string.email_format_error)
            return false
        }

        if(!ValidateUtils.validateVfcode(code)){
            toast(R.string.vfcode_format_error)
            return false
        }
        return true
    }
    fun checkEmail(email:String):Boolean{
        if(!ValidateUtils.validateEmail(email)){
            toast(R.string.email_format_error)
            return false
        }
        return true
    }

    fun checkParams3(password:String,repassword:String):Boolean{
        if(!ValidateUtils.validatePassword(password)){
            toast(R.string.password_format_error)
            return false
        }
        if(!ValidateUtils.validatePassword(repassword)){
            toast(R.string.repassword_format_error)
            return false
        }
        if(!(password.equals(repassword))){
            toast(R.string.two_password_not_same)
            return false
        }
        return true
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
}