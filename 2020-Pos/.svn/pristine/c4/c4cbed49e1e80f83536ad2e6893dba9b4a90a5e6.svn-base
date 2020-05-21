package com.epro.pos.mvp.model.bean
/*| 字段       | 类型    | 必须 | 说明                     |
"createTime":"2019-09-29 10:54:07",
				"email":"123654@qq.com",
				"id":"10",
				"isDelete":0,
				"lastUpdateTime":"",
				"lastUpdateUser":"",
				"mobile":"+86135252558",
				"owner":"zlla",
				"problem":"test",
				"remark":"",
				"result":"",
				"serialNo":"1000010",
				"shopId":"88000001",
				"shopName":"aa",
				"status":0,
				"updateTime":"",
				"userId":"",
				"userName":""*/
data class HistoryRecordBean(override val code: Int, val content:String, val status:Boolean,override val message: String, override val result: Result) : BaseBean<HistoryRecordBean.Result> {
    data class Result(val rows: List<HistoryRecordOneBean>,val total:String)
    data class HistoryRecordOneBean(val createTime:String,val email:String,val id:String,val isDelete:String,val lastUpdateTime:String,val lastUpdateUser:String,val mobile:String,val owner:String,val problem:String
    ,val remark:String,val result:Any,val serialNo:String,val shopId:String,val shopName:String,val status:Integer,val updateTime:String,val userId:String,val userName:String)
    data class Send(val page:PageList)
    data class PageList(val no:Int,val size :Int =10)
}
