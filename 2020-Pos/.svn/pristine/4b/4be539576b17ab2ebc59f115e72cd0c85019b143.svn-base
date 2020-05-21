package com.epro.pos.mvp.model.bean

/*| 字段      | 类型   | 必须 | 说明                          |
| --------- | ------ | ---- | ----------------------------- |
| startTime | string | Y    | 开始时间(yyyy-mm-dd hh:ss:mm) |
| endTime   | string | Y    | 结束时间                      |*/

/*| 字段             | 类型       | 必须 | 说明         |
| ---------------- | ---------- | ---- | ------------ |
| id               | Long       | Y    | 主键id       |
| shopId           | string     | Y    | 店铺id       |
| shopName         | string     | Y    | 店铺名字     |
| statementId      | string     | Y    | 对账单号     |
| cashierId        | string     | Y    | 收银员id     |
| cashierName      | string     | Y    | 收银员名字   |
| createTime       | string     | Y    | 对账时间     |
| firstSaleTime    | string     | Y    | 首单销售时间 |
| lastSaleTime     | string     | Y    | 莫比销售时间 |
|                  |            |      |              |
| cashCount        | integer    | Y    | 现金笔数     |
| cashAmount       | bigdecimal | Y    | 现金金额     |
| cashSaleCount    | integer    | Y    | 现金销售笔数 |
| cashSaleAmount   | bigdecimal | Y    | 现金销售金额 |
| cashBackCount    | integer    | Y    | 现金退货笔数 |
| cashBackAmount   | bigdecimal | Y    | 现金退货金额 |
|                  |            |      |              |
| backCard......   |            |      | 银行卡       |
| creditCard...... |            |      | 信用卡       |
| wx......         |            |      | 微信         |
| zfb......        |            |      | 支付宝       |
|                  |            |      |              |
| totalCount       |            |      | 总笔数       |
| totalAmount      |            |      | 总金额       |
| leftCash         |            |      | 结余现金     | */

class CashierReconciliationBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<CashierReconciliationBean.Result> {
    data class Result(val backTotalAmount: String,
                         val backTotalCount: String,
                         val bankCardAmount: String,
                         val bankCardBackAmount: String,
                         val bankCardBackCount: String,
                         val bankCardCount: String,
                         val bankCardSaleAmount: String,
                         val bankCardSaleCount: String,
                         val cashAmount: String,
                         val cashBackAmount: String,
                         val cashBackCount: String,
                         val cashCount: String,
                         val cashSaleAmount: String,
                         val cashSaleCount: String,
                         val cashierId: String,
                         val cashierName: String,
                         val createTime: String,
                         val creditCardAmount: String,
                         val creditCardBackAmount: String,
                         val creditCardBackCount: String,
                         val creditCardCount: String,
                         val creditCardSaleAmount: String,
                         val creditCardSaleCount: String,
                         val faceAmount: String,
                         val faceBackAmount: String,
                         val faceBackCount: String,
                         val faceCount: String,
                         val faceSaleAmount: String,
                         val faceSaleCount: String,
                         val firstSaleTime: String,
                         val id: String,
                         val lastSaleTime: String,
                         val saleTotalAmount: String,
                         val saleTotalCount: String,
                         val shopId: String,
                         val shopName: String,
                         val statementId: String,
                         val totalAmount: String,
                         val totalCount: String,
                         val wxAmount: String,
                         val wxBackAmount: String,
                         val wxBackCount: String,
                         val wxCount: String,
                         val wxSaleAmount: String,
                         val wxSaleCount: String,
                         val zfbAmount: String,
                         val zfbBackAmount: String,
                         val zfbBackCount: String,
                         val zfbCount: String,
                         val zfbSaleAmount: String,
                         val zfbSaleCount: String)
    data class Send(val startTime: String,val endTime:String)
}
/*
"bankCardAmount": 0,
"bankCardBackAmount": 0,
"bankCardBackCount": 0,
"bankCardCount": 0,
"bankCardSaleAmount": 0,
"bankCardSaleCount": 0,
"cashAmount": 499.80,
"cashBackAmount": -500.20,
"cashBackCount": 1,
"cashCount": 2,
"cashSaleAmount": 1000.00,
"cashSaleCount": 1,
"cashierId": "1",
"cashierName": "是一个大学生",
"createTime": "2019-08-27 11:17:14",
"creditCardAmount": 299.00,
"creditCardBackAmount": 0,
"creditCardBackCount": 0,
"creditCardCount": 2,
"creditCardSaleAmount": 299.00,
"creditCardSaleCount": 2,
"firstSaleTime": "2019-08-07 16:48:03",
"id": 6,
"lastSaleTime": "2019-08-07 16:48:03",
"leftCash": 999.80,
"originalCash": 500.00,
"shopId": "1234567",
"shopName": "",
"statementId": "AS1234567201908271117148420",
"totalAmount": 2599.40,
"totalCount": 10,
"wxAmount": 200.00,
"wxBackAmount": 100.00,
"wxBackCount": 1,
"wxCount": 2,
"wxSaleAmount": 100.00,
"wxSaleCount": 1,
"zfbAmount": 600.20,
"zfbBackAmount": 0,
"zfbBackCount": 0,
"zfbCount": 2,
"zfbSaleAmount": 600.20,
"zfbSaleCount": 2*/
