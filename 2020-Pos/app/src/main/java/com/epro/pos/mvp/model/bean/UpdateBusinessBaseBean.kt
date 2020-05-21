package com.epro.pos.mvp.model.bean

/*| 字段       | 类型    | 必须 | 说明       |
| ---------- | ------- | ---- | ---------- |
| id         | Long    | Y    | ID         |
| shopId     | String  | Y    | 店铺ID     |
| shopName   | String  | Y    | 店铺名称   |
| shopType   | String  | Y    | 经营类型   |
| shopTypeId | Integer | Y    | 经营类型ID |
| mobile     | String  | Y    | 联系人手机 |
| email      | String  | Y    | 联系人邮箱 |
| owner      | String  | Y    | 联系人     |
| province   | String  | Y    | 省         |
| provinceId | Integer | Y    | 省ID       |
| city       | String  | Y    | 市         |
| cityId     | Integer | Y    | 市ID       |
| area       | String  | Y    | 区         |
| areaId     | Integer | Y    | 区ID       |
| address    | String  | Y    | 详细地址   |
| longitude  | String  | Y    | 经度       |
| latitude   | String  | Y    | 纬度       |*/
data class UpdateBusinessBaseBean ( val code: Int,  val message: String,  val result:Any) {
    data class Send(val address:String,val area:String,val areaId:String,val city:String,val cityId:String,val email:String,val id:String,val latitude:String,val longitude:String,
                    val mobile:String,val owner:String,val province:String,val provinceId:String,val shopId:String,val shopName:String,val shopType:String,val shopTypeId:String)
}