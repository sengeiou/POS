package com.epro.pos.mvp.model.bean


data class GetFinancialScanBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<GetFinancialScanBean.Result> {
    data class Result(val closeAmount: String,
                      val openAmount: String,
                      val payFinances: ArrayList<PayFinance>,
                      val refundAmount: String,
                      val sellTotalAmount: String,
                      val dateTime:String) {

    }

    data class PayFinance(val offlineCloseAmount: String,
                          val offlineOpenAmount: String,
                          val offlineRefundAmount: String,
                          val offlineSellTotalAmount: String,
                          val onlineCloseAmount: String,
                          val onlineOpenAmount: String,
                          val onlineRefundAmount: String,
                          val onlineSellTotalAmount: String,
                          val payType: Int)
}