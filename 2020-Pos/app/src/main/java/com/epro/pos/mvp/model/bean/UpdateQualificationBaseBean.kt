package com.epro.pos.mvp.model.bean

/*
idcarNegative: "group1/M00/00/00/wKgBul2MXYyACqh9AAgOusLI8eo224.jpg"
idcarPositive: "group1/M00/00/00/wKgBul2MXYyALL8DAAgOusLI8eo589.jpg"
idcarValidity: "2019-10-05"
legalIdcar: "511010199403021125"
legalName: "zll"
license: "许可证"
licenseId: "1"
licenseNo: "321"
licensePhoto: "group1/M00/00/00/wKgBul2MXYyAB3zyAAgOusLI8eo349.jpg"
licenseValidity: "2020-12-30"
registration: "许可证"
registrationId: "1"
registrationNo: "123212"
registrationPhoto: "group1/M00/00/00/wKgBul2MaXuAGPxEAAF58twEUo8306.jpg"
registrationValidity: "2019-09-28"
shopId: "88000001"*/
data class UpdateQualificationBaseBean(override val code: Int, override val message: String,override val result: Result):BaseBean<UpdateQualificationBaseBean.Result>  {
    data class Result(val code: Int,val message:String,var result:Any)
    data class Send(val idcarNegative:String,val idcarPositive:String,val idcarValidity:String,val legalIdcar:String,val legalName:String,val license:String,val licenseId:String,
                    val licenseNo:String,val licensePhoto:String,val licenseValidity:String,val registration:String,val registrationId:String,val registrationNo:String,val registrationPhoto:String,val registrationValidity:String,
                    val shopId:String)
}