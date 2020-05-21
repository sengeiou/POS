package com.epro.comp.login.mvp.model.bean


data class LoginBean(val code:Int, val message: String, val result:LoginUser){
    data class LoginUser(val user:User,val token:String){

    }

    //    code string
//    验证码
//
//    loginname string
//    登录名
//
//    icon_login_password string
//    密码

//    data class Send(val code:String,val loginname:String,val password:String ){
//
//    }
    data class Send(val loginname:String ){

    }
}

