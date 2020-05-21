package com.epro.pos.mvp.model.bean

/*| 字段       | 类型    | 必须 | 说明                                                         |
| ---------- | ------- | :--- | ------------------------------------------------------------ |
| id         | integer | Y    | ID                                                           |
| type       | string  | Y    | 类型1:ios_app; 2:ios_pos ;3:android_app ;4:android_pos ;5:pc_pos |
| typeName   | string  | Y    | 类型名称                                                     |
| content    | String  | Y    | 更新的内容,每个更新点以分号分隔                              |
| version    | String  | Y    | 版本号 V1.0.0                                                |
| createTime | Date    | Y    | 版本更新时间                                                 |*/

/*"content": "1.更新内容:",
"createTime": "2019-11-13 18:43:23",
"downloadUrl": "",
"id": 4,
"type": "4",
"typeName": "android_pos",
"version": "1.0.1"*/
data class UpdateVersionBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<UpdateVersionBean.Result> {
    data class Result(val id: String,val type:String,val typeName:String,val content:String,val version:String,val createTime:String,val downloadUrl:String)
    data class Send(val id: String)
}
