package com.epro.pos.mvp.contract

import com.epro.pos.mvp.model.bean.GetHomeSalesBean
import com.epro.pos.mvp.model.bean.GetMyBusinessBean
import com.epro.pos.mvp.model.bean.GetWaitdoListBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface HomeContract {

    interface View:IBaseView{
        fun onGetMybusinessSuccess(business:Any)
        fun onGetHomeSalesSuccess(result: GetHomeSalesBean.Result)
        fun onGetWaitdoListSuccess(result: GetWaitdoListBean.Result)
    }

    interface Presenter:IPresenter<View>{

    }
}