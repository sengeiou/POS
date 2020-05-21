package com.epro.pos.mvp.model.bean

/*| 字段       | 类型    | 必须 | 说明                     |
| ---------- | ------- | ---- | ------------------------ |
| id         | Integer | Y    | 消息id                   |
| bizType    | String  | Y    | 消息业务类型             |
| shopId     | String  | Y    | 店铺ID                   |
| message    | String  | Y    | 消息内容                 |
| isRead     | String  | Y    | 是否已读  0  未读  1已读 |
| isDelete   | String  | Y    | 是否已删  0  未删 1 已删 |
| msgId      | String  | Y    | 消息对应链接key          |
| msgType    | date    | Y    | 消息通知类型             |
| senderId   | date    | Y    | 发送者                   |
| receiveId  | Integer | Y    | 接受者                   |
| role       | String  | Y    | 权限                     |
| createDate | Long    | Y    | 消息创建时间             |*/
data class MessageCenterBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<MessageCenterBean.Result> {
    data class Result(val rows: List<MessageCenterOneBean>,val total:String)
    data class MessageCenterOneBean(val id: Int,val bizType:String,val shopId:String,val message:String,val isRead:String,val isDelete:String,val msgId:String,val msgType:String,val senderId:String,
                      val receiveId:String,val role:String,val createDate:String)
    data class Send(val userid: Int,val page:mPage)
    data class mPage(val no:Int,val size :Int =6)
}
