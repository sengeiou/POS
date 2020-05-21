package com.epro.pos.mvp.model.bean

import java.util.*

/*| 字段                 | 类型    | 必须 | 说明                                    |
"result":{
		"id":"1",
		"idcarNegative":"group1/M00/00/00/wKgBul2MXYyACqh9AAgOusLI8eo224.jpg",
		"idcarPositive":"group1/M00/00/00/wKgBul2MXYyALL8DAAgOusLI8eo589.jpg",
		"idcarValidity":"2019-10-05 00:00:00",
		"isDelete":"",
		"isOverdue":1,
		"legalIdcar":"511010199403021125",
		"legalName":"zll",
		"license":"许可证",
		"licenseId":1,
		"licenseNo":"321",
		"licensePhoto":"group1/M00/00/00/wKgBul2MXYyAB3zyAAgOusLI8eo349.jpg",
		"licenseValidity":"2020-12-30 00:00:00",
		"opinions":"",
		"registration":"许可证",
		"registrationId":1,
		"registrationNo":"123212",
		"registrationPhoto":"group1/M00/00/00/wKgBul2MaXuAGPxEAAF58twEUo8306.jpg",
		"registrationValidity":"2019-09-28 00:00:00",
		"shopId":"88000001",
		"status":1
	}          |*/
data class QualificationBaseBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<QualificationBaseBean.Result> {
    data class Result(val id:String,val idcarNegative:String,val idcarPositive:String,val idcarValidity:String,val isDelete:String,val isOverdue:String,val legalIdcar:String,
                     val legalName:String,val license:String,val licenseId:String,val licenseNo:String,val licensePhoto:String,val licenseValidity:String,val shopinfoOpinions:String,val registration:String,
                      val registrationId:String,val registrationNo:String,val registrationPhoto:String,val registrationValidity:String,val shopId:String,val shopinfoStatus:String,val shopinfoId:String)
}