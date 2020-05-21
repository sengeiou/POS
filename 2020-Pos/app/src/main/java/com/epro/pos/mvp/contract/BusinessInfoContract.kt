package com.epro.pos.mvp.contract

import com.epro.pos.mvp.model.bean.BusinessBaseInfoBean
import com.epro.pos.mvp.model.bean.QualificationBaseBean
import com.epro.pos.mvp.model.bean.RenewBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface BusinessInfoContract {

    interface View : IBaseView {
        fun onBusinessInfoSuccess(result: BusinessBaseInfoBean.Result)
        fun onQualificationInfoSuccess(result: QualificationBaseBean.Result)
        fun onRenewShopSuccess(result:Int)
        fun onDeleteSuccess()
    }


    interface Presenter : IPresenter<View> {
        //查询商户基本信息
        fun BusinessBaseInfo(type: String)

        //查询商户资质信息
        fun QualificationInfo(type: String)

        fun RenewShop(type:String,shopId:String)

        //删除tips
        fun deDeleteTips(type: String,shopinfoId:String)
    }
}