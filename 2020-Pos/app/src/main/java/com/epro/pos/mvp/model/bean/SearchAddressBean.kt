package com.epro.pos.mvp.model.bean

/*| 字段      | 类型    | 必须 | 说明 |
| --------- | ------- | :--- | ---- |
| id        | Integer | Y    | ID   |
| name      | string  | Y    | 名称 |
| parentId  | Integer | Y    | 父ID |
| longitude | string  | Y    | 经度 |
| latitude  | string  | Y    | 纬度 |
| sort      | Integer | Y    | 排序 |
| level     | Integer | Y    | 等级 |*/
class SearchAddressBean(val code:Int,val message:String,val result: Result) {
    data class Result(val id:String,val name:String,val parentId:String,val longitude:String,val latitude:String,val sort:String,val level:String)
    data class Send(val parentId:String)
}