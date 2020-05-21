package com.epro.pos.mvp.model.bean

/*| 字段     | 类型    | 必须 | 说明     |
| -------- | ------- | ---- | -------- |
| id       | Integer | Y    | 主键id   |
| avatar   | String  | Y    | 头像     |
| account  | String  | Y    | 账号     |
| name     | String  | Y    | 名字     |
| email    | String  | Y    | 电子邮件 |
| phone    | String  | Y    | 电话     |
| roleid   | String  | Y    | 角色id   |
| rolename | String  | Y    | 角色名称 |*/
data class PersonCenterBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<PersonCenterBean.Result> {
    data class Result(val isBargain:String,val areacode:String,val id: Integer,val avatar:String,val account:String,val name:String,val email:String,val phone:String,val roleid:String,val rolename:String,val shopId:String,val shopName:String,val deptid:String)
}
/*
"account": "880000071000",
"areacode": "+86",
"avatar": "group1/M00/00/07/wKgBul3CHPWAHvglAAvqHw7LRwM518_120x120.jpg",
"createtime": "2019-11-01 14:13:05",
"deptid": 28,
"email": "1696791817@qq.com",
"id": 75,
"name": "dada",
"phone": "+8613337399077",
"roleid": "8,2",
"rolename": "店长",
"shopId": "88000007",
"shopName": "eproshop坂田社区店"*/
