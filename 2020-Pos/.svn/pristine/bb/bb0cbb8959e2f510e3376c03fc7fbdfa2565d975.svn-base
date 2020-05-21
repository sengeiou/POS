package com.epro.pos.mvp.contract

import com.epro.pos.mvp.model.bean.QualificationBaseBean
import com.epro.pos.mvp.model.bean.UpdateImageBean
import com.epro.pos.mvp.model.bean.UpdateQualificationBaseBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter
import java.io.File
import java.util.*

interface QualificationInfoContract {

    interface View : IBaseView {
        fun onUpdateQualificationInfoSuccess(result: UpdateQualificationBaseBean.Result)
        fun onQualificationInfoSuccess(result: QualificationBaseBean.Result)
        fun onUpdateImageSucess(result:UpdateImageBean,phototype: Int)
        fun onDeleteSuccess()
    }


    interface Presenter : IPresenter<View> {
        //更新商户资质信息
        fun updateQualificationInfo(type: String,idcarNegative:String,idcarPositive:String,idcarValidity:String,legalIdcar:String, legalName:String, license:String, licenseId:String,
                                    licenseNo:String,licensePhoto:String,licenseValidity:String, registration:String, registrationId:String,registrationNo:String,registrationPhoto:String, registrationValidity:String,
                                    shopId:String)
        //查询商户资质信息
        fun QualificationInfo(type: String)
        //上传图片
        fun updateImage(type:String,image:File,isCreateThumb:Int,phototype:Int)
        //删除tips
        fun deDeleteTips(type: String,shopinfoId:String)
    }
}