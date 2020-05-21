package com.epro.pos.mvp.model.bean

import com.mike.baselib.interface_.Judgable


data class OnlineOrder(val address: String,
                       val consignee: String,
                       val courierName: String,
                       val courierMobile: String?,
                       val channel: String?,
                       val createTime: String,
                       val logisticsFee: String,
                       val mobile: String,
                       val orderActualAmount: String,
                       val orderCancelReason: String,
                       val orderClosedTime: String,
                       val orderDesc: String,
                       val orderId: String,
                       val orderSn: String,
                       val orderStatus: Int,
                       val orderType: Int,
                       val deliveryType: Int,
                       val originalOrderSn: String,
                       val paySn: String,
                       val payType: Int,
                       val pickUpAdress: String,
                       val pickUpTime: String,
                       val productCount: Int,
                       val products: ArrayList<OrderProduct>,
                       val puserId: String,
                       val reimburseStatus: Int,
                       val remark: String,
                       val shopId: String,
                       val refundPaySn:String?,
                       val shopName: String,
                       val shopMobile: String,
                       val tradeType: Int, override var judgeValue: Boolean=false):Judgable {
    override fun judge(): Boolean {
       return judgeValue
    }

}