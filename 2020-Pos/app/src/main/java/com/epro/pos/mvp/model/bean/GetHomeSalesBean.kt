package com.epro.pos.mvp.model.bean

data class GetHomeSalesBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<GetHomeSalesBean.Result> {
    data class Result(val offLineAmount: String,
                         val offLineCount: String,
                         val onlineAmount: String,
                         val onlineCount: String,
                         val totalAmount: String,
                         val totalCount: String)
}
