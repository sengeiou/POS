package com.epro.pos.mvp.contract

import com.epro.pos.mvp.model.bean.PersonCenterBean
import com.epro.pos.mvp.model.bean.UpdateImageBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter
import java.io.File

interface PersonCenterContract {

    interface View : IBaseView {
        fun onPersonCenterSuccess(result: PersonCenterBean.Result)
        fun onEditAvatarSuccess()
        fun onExitLogoutSuccess()
        fun onUpdateImageSucess(result: UpdateImageBean)
    }


    interface Presenter : IPresenter<View> {
        fun PersonCenter(type: String)
        fun EditAvatar(type:String,avatar:String)
        fun ExitLogout(type: String)
        //上传图片
        fun updateImage(type:String, image: File, isCreateThumb:Int)
    }
}