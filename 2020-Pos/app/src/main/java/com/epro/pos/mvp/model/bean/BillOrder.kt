package com.epro.pos.mvp.model.bean
data class BillOrder(val amount: String,
                     val brokerage: String,
                     val left: String,
                     val orderCloseTime: String,
                     val orderSn: String,
                     val paySn: String,
                     val tradeType: Int)