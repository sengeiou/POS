package com.epro.pos.mvp.model.bean


data class GetFinancialBillDetailBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<GetFinancialBillDetailBean.Result> {
    data class Result(val total: Int, val rows: ArrayList<BillOrder>)
}