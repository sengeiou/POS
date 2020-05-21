package com.epro.pos.mvp.contract

import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

class EditBindPhoneContract {
    interface View : IBaseView {
        fun onEditBindPhonePopSuccess()
    }


    interface Presenter : IPresenter<View> {
        fun EditBindPhonePop(type: String)
    }
}