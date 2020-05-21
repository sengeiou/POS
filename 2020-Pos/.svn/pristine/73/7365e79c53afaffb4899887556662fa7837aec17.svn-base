package com.epro.pos.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.pos.mvp.contract.PersonCenterContract
import com.epro.pos.mvp.model.PersonCenterModel
import com.mike.baselib.net.exception.ErrorStatus
import java.io.File

class PersonCenterPresenter : BasePresenter<PersonCenterContract.View>(), PersonCenterContract.Presenter {
    override fun updateImage(type: String, image: File, isCreateThumb: Int) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.updateImage(image,isCreateThumb)
                .subscribe({ bean ->
                    mRootView?.onUpdateImageSucess(bean)
                    mRootView?.dismissLoading(type, ErrorStatus.SUCCESS,ErrorStatus.SUCCESS_MSG)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    private val model by lazy { PersonCenterModel() }

    override fun PersonCenter(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.PersonCenter()
                .subscribe({ bean ->
                    mRootView?.onPersonCenterSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    //编辑头像
    override fun EditAvatar(type: String, avatar: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.EditAvatar(avatar)
                .subscribe({ bean ->
                    mRootView?.onEditAvatarSuccess()
                    mRootView?.dismissLoading(type, ErrorStatus.SUCCESS,ErrorStatus.SUCCESS_MSG)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    //退出登录
    override fun ExitLogout(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.Logout()
                .subscribe({ bean ->
                    mRootView?.onExitLogoutSuccess()
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