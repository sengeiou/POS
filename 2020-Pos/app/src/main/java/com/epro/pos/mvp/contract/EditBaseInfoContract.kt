package com.epro.pos.mvp.contract

import com.epro.pos.mvp.model.bean.BusinessBaseInfoBean
import com.epro.pos.mvp.model.bean.SearchAddressBean
import com.epro.pos.mvp.model.bean.UpdateBusinessBaseBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface EditBaseInfoContract {

    interface View : IBaseView {
        fun onEditBaseInfoSuccess(result: UpdateBusinessBaseBean)
        fun onBusinessInfoSuccess(result: BusinessBaseInfoBean.Result)
        fun onSearchAddressSuccess(result:SearchAddressBean.Result)
    }

    interface Presenter : IPresenter<View> {
        //更新商户信息
        fun updateBusinessBaseInfo(type:String,address:String,area:String,areaId:String,city:String,cityId:String,email:String, id:String, latitude:String,longitude:String,
                                   mobile:String,owner:String,province:String, provinceId:String,shopId:String, shopName:String, shopType:String,shopTypeId:String)
        //查询商户基本信息
        fun BusinessBaseInfo(type: String)
        //查询地址
        fun searchAddress(type: String,parentId:String)
    }
}