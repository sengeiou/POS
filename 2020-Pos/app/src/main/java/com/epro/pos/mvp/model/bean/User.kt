package com.epro.pos.mvp.model.bean

//created_time	string($date-time)
//添加时间
//
//del_flag	boolean
//是否删除
//
//departmentid	integer($int64)
//部门主键
//
//productId	string
//主键
//
//loginname	string
//登录名
//
//icon_login_password	string
//密码
//
//permissionidSet	string
//区域权限ID组
//
//phone	string
//电话
//
//roleid	string
//角色Id
//
//truename	string
//姓名
//
//uniitid	integer($int32)
//单位id
//
//updated_time	string($date-time)
//修改时间
//
//user_display	string
//用户头像
//
//usertype	integer($int32)
//用户类型

data class User(val created_time :String,
                val del_flag:Boolean,
                val departmentid:Int,
                var id:String,
                val token:String,
                val loginname:String,
                val password:String,
                val permissionidSet:String,
                var phone :String,
                var roleid :String,
                val truename:String,
                val uniitid:Int,
                val updated_time:String,
                val user_display:String,
                val usertype:Int)