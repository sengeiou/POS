package com.epro.pos.mvp.model.bean

import java.util.*

/*| 字段         | 类型    | 必须 | 说明               |
| ------------ | ------- | ---- | ------------------ |
| id           | Long    | Y    | ID                 |
| userId       | Integer | Y    | 用户ID             |
| shopId       | String  | Y    | 店铺ID             |
| shopName     | String  | Y    | 店铺名称           |
| shopLogo     | String  | Y    | 店铺LOGO           |
| shopType     | String  | Y    | 经营类型           |
| shopTypeId   | Integer | Y    | 经营类型ID         |
| mobile       | String  | Y    | 联系人手机         |
| email        | String  | Y    | 联系人邮箱         |
| owner        | String  | Y    | 联系人             |
| province     | String  | Y    | 省                 |
| provinceId   | Integer | Y    | 省ID               |
| city         | String  | Y    | 市                 |
| cityId       | Integer | Y    | 市ID               |
| area         | String  | Y    | 区                 |
| areaId       | Integer | Y    | 区ID               |
| address      | String  | Y    | 详细地址           |
| longitude    | String  | Y    | 经度               |
| latitude     | String  | Y    | 纬度               |
| status       | Integer | Y    | 状态 0:正常 1:冻结 |
| shopValidity | Date    | Y    | 店铺有效期         |
| validityTime | Long    | Y    | 有效时间           |*/
/*"result": {
    "address": "湾仔大道182号",
    "area": "长洲",
    "areaId": 6,
    "city": "离岛区",
    "cityId": 3,
    "distance": "1",
    "email": "wanzai@gmail.com",
    "endTime": "2019-09-19 09:42:14",
    "id": "2",
    "isRenew": 1,
    "latitude": "650",
    "longitude": "230",
    "mobile": "+8613260506711",
    "opinions": "",
    "owner": "样样",
    "province": "香港",
    "provinceId": 2,
    "shopId": "88000001",
    "shopName": "湾仔大道便利店",
    "shopType": "便利店",
    "shopTypeId": 1,
    "shopinfoVO": "",
    "startTime": "",
    "status": 100,
    "userId": 5,
    "validityTime": "-25"
}*/
data class BusinessBaseInfoBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<BusinessBaseInfoBean.Result> {
    data class Result(val id: String, val userId:Integer, val shopId:String, val shopName:String, val shopLogo:String, val shopType:String, val shopTypeId:String, val mobile:String,
                      val email :String, val owner:String, val province:String, val provinceId:Integer, val city:String, val cityId:Integer, val area:String, val areaId:Integer, val address:String,
    val longitude:String, val latitude:String, val status:String, val startTime: String, val endTime: String, val validityTime:String,val isRenew:String)
}
