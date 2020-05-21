package com.epro.pos.utils

class PosConst {
    companion object {
        const val LOGIN = "/admin/auth"
        const val REGISTER = "/open/register"
        const val GET_VFCODE = "/admin/passd/getVcode"
        const val GET_USERVFCODE = "/admin/getVCodeByUserAccount"
        const val FIND_PASSWORD = "/admin/passd/resetPassword"//忘记密码
        const val LOGOUT = "/admin/logout" //退出登录
        const val MODIFY_PASSWORD = "/admin/user/changePwd"
        const val GET_POS_CATEGORY_PRODUCTS = "admin/pos/productpos/list"
        const val GET_PRODUCT_LIST = "/admin/product/list"
        const val CREATE_ORDER="/admin/padPos/checkStand/generateOrder"
        const val GET_PERSON_INFO = "/admin/user/view"  //查询用户基本信息
        const val GET_GOODS_CATEGORY_LIST="/admin/type/list"//商品分类
        const val GET_GOODS_CATEGORY_COUNT_LIST="/admin/product/productTypeCount"//商品分类 商品个数
        const val GET_GOODS_FILE_LIST="/admin/product/archivesList"//商品档案
        const val DELETE_GOODS_FILE="/admin/product/mobileDelete"//删除档案
        const val GET_GOODS_FILE_DETAIL=" /admin/goods/detail"//档案详情

        const val UPDATE_AVATAR = "/admin/user/updateAvatar"  //编辑头像
        const val UNBIND_ACCOUNT = "/admin/user/unbindAccount" //解除绑定
        const val BIND_PHONE_EMAIL = "/admin/user/bindAccount" //绑定手机/邮箱
        const val BUSINESS_BASE_INFO = "/admin/shop/selectByUserId" //商户资料
        const val UPDATE_BUSINESS_BASE_INFO = "/admin/shop/update" //更新商户资料
        const val UPDATE_QUALIFICATION_INFO = "/admin/shopinfo/update"
        const val MERCHANT_QUALIFICATION_INFO = "/admin/shopinfo/detail/" //查询商户资质信息
        const val COMMIT_FEEDBACK = "/admin/shopUserFeedback/add"  //提交意见反馈
        const val SELECT_USER_INFO = "/admin/user/selectUserInfo"  //查询商户信息
        const val HISTORY_RECORD_LIST = "/admin/shopUserFeedback/selectListByShopId"  //意见反馈历史记录
        const val HISTORY_RECORD_DETAIL=" /admin/shopUserFeedback/detail/"  //意见反馈详情
        const val RENEW_SHOP = "/admin/shop/signing"   //续约
        const val NOTICE_MESSAGE="/admin/noticeList/list" //消息列表
        const val UPDATE_READ="/admin/noticeList/updateRead" //消息已读
        const val UPDATE_IMAGE = "/admin/image/upload"  //更新图片
        const val DELETE_TIPS = "/admin/shopinfo/deleteTips" //删除tips
        const val SEARCH_ADDRESS = "/admin/address/list"   //查询地址
        const val VERSION_UPDATE = "/admin/version/selectNewVersion" //版本更新
        const val VALID_USER = "/admin/passd/validUser"  //效验账号

        //首页统计
        const val GET_MY_BUSINESS="/admin/home/goodsStatistics" //我的业务统计
        const val GET_WAITDO_LIST="/admin/padPos/home/todoInfo" //我的待办
        const val GET_SALES="/admin/padPos/home/order/{type}" //销售统计


        //订单管理
        const val GET_ORDER_RECORD_LIST = "/admin/tradeManage/padOrder/list"//订单记录
        const val GET_ORDER_GOODS_RECORDS="admin/tradeManage/padOrderGoods/list"// 商品记录
        const val GET_ORDER_SALES_RANKING_GOODS_LIST="/admin/tradeManage/padSaleRange/list" //销售排行
        const val GET_GROSS_PROFIT_GATHER_LIST="/admin/tradeManage/padMargin/list" //毛利汇总
        const val GET_CASHIER_RECONCILIATION_LIST="/admin/tradeManage/padAccountStatement/list"//收银对账
        const val GET_ORDER_RECORD_DETAIL="/admin/tradeManage/padOrder/detail/{orderSn}"//订单记录详情
        const val GET_CASHIER_LIST="/admin/order/listCashier"//获取收银员列表

        //库存管理
        const val GET_STOCK_QUERY_LIST="/admin/stock/productList"//库存查询
        const val GET_STOCK_GOODS_PUTIN_LIST="/admin/stock/list"// 商品入库
        const val GET_STOCK_TAKING_LIST="/admin/inventory/list" //库存盘点
        const val GET_STOCK_OUTIN_DETAIL="/admin/stockInfo/list"//出入库明细
        const val GET_GOODS_DETAIL_FROM_BARCODE="/admin/pos/productpos/detailByBarCode" //根据条形码查询商品
        const val ADD_STOCK_PUTIN="/admin/stock/add" //新增入库
        const val UPDATE_STOCK_PUTIN="/admin/stock/update" //修改入库
        const val DELETE_STOCK_PUTIN="/admin/stock/delete" //删除入库
        const val ADD_STOCK_TAKING="/admin/inventory/add" //新增盘点
        const val UPDATE_STOCK_TAKING="/admin/inventory/update" //修改盘点
        const val DELETE_STOCK_TAKING="/admin/inventory/delete" //删除盘点


        //网店管理
        const val GET_NETSHOP_UPPER_SHELF_GOODS_LIST="/admin/goodsStatus/list" //网店上架商品管理
        const val GET_NETSHOP_ONLINE_ORDER_LIST="/admin/padOnlineOrder/list" //网店线上订单管理
        const val LOWER_SHELF_GOODS="/admin/goodsStatus/ipadLowerShelfAll" //下架商品
        const val GET_SHOPPER_LIST="/admin/padOnlineOrder/listCourier" //获取配送员列表
        const val ARRANGE_DISTRIBUTION="/admin/padOnlineOrder/arrangeLogistic" //安排配送接口
        const val CANCEL_ONLINE_ORDER="/admin/padOnlineOrder/cancel" //取消订单接口
        const val VERIFY_ONLINE_ORDER="/admin/padOnlineOrder/verifyOrder" //提货码验证接口
        const val REFUND_AGAIN_ONLINE_ORDER="/admin/padOnlineOrder/againRefund/{orderSn}" //重新退款

        //财务管理
        const val GET_FINANCIAL_SCAN="/admin/shopfinance/scan" //财务管理首页总览接口
        const val GET_FINANCIAL_BILL_LIST="/admin/shopfinance/billList"
        const val GET_FINANCIAL_BILL_DETAIL="/admin/shopfinance/billDetail/{billSn}"


        //POS收银接口
        const val CASHIER_RECONCILIATION ="/admin/padPos/padCashierCheck/check" //收银对账
        const val CHANGE_USER_LOGOUT ="/admin/padPos/padCashierCheck/insert" //交班推出系统
        const val GET_POS_TRADE_LIST="/admin/padPos/orderSearch/listOrder" // 收银交易查询
        const val GET_POS_ORDER_DETAIL="/admin/padPos/padRefund/availableRefundCount/{orderSn}" // 收银交易订单详情
        const val REFUND_PART_ORDER="/admin/padPos/padRefund/partRefund" // 部分商品退款
        const val REFUND_WHOLE_ORDER="/admin/padPos/padRefund/wholeRefund/{orderSn}" // 整单退款
        const val CHECK_ORDER_PAY="/admin/pos/index/orderPayStatusCheck/{orderSn}" // 检查支付状态
        const val SEARCH_POS_PRODUCT="/admin/pos/productpos/findPosProductSearch" //关键字搜索(添加到购物车)
        const val ADD_SIMPLE_PRODUCT="/admin/pos/productpos/goodsAdd" //新增简易商品入库
        const val CANCEL_POS_TRADE="/admin/padPos/checkStand/orderPayStatusCancel/{orderSn}" //取消收银交易
        const val GET_SYSTEM_TIME="/admin/date/getTime"



        // EP:邮箱密码登录, MP:手机密码登录, MV:手机验证码登录 ，FB:facebook登录，TW:推特登录
        const val LOGIN_TYPE_EP="ep"
        const val LOGIN_TYPE_MP="mp"
        const val LOGIN_TYPE_MV="MV"
        const val LOGIN_TYPE_FB="FB"
        const val LOGIN_TYPE_TW="TW"

        //验证码类型(1-登录,2-注册,3-找回密码,4-修改密码,5-绑定账号,6-解除绑定)
        const val VF_TYPE_LOGIN=1
        const val VF_TYPE_REGISTER=2
        const val VF_TYPE_FINDPASSWORD=3
        const val VF_TYPE_MODIFY_PASSWORD=4
        const val VF_TYPE_BIND_ACCOUNT = 5
        const val VF_TYPE_UNBIND_ACCOUNT = 6

        //支付类型
        const val PAY_MODE_CASH=1 //现金
        const val PAY_MODE_ZFB=2 //支付宝
        const val PAY_MODE_PAYPAL=3 //paypal
        const val PAY_MODE_WX=4 //微信
        const val PAY_MODE_OTHER=5 //其他支付
        const val PAY_MODE_FACEPAY=7 //刷脸付
        const val PAY_MODE_WX_ZFB=8

        //交易类型(1.线上 2.线下)
        const val TRADE_TYPE_ALL=0
        const val TRADE_TYPE_ONLINE=1
        const val TRADE_TYPE_OUTLINE=2

        //订单类型(1.销售 2.退货)
        const val ORDER_TYPE_ALL=0
        const val ORDER_TYPE_SALE=1
        const val ORDER_TYPE_REFUND=2

        const val KEY_CART_PRODUCTS="cart_products"
        const val KEY_REFUND_CART_PRODUCTS="refund_cart_products"
        const val KEY_IS_REFUND="is_refund" //是否退款
        const val KEY_PEND_ORDERS="pend_orders"
        const val KEY_SHOP_PRODUCTS="shop_products"
        const val KEY_SHOP_PRODUCT_CATEGORYS="shop_product_categorys"
        const val KEY_PRODUCT_CATEGORY_NAME="product_category_name"
        const val KEY_SHOP_ID="shop_id"

        //res.code == '10028' || res.code == '10026' || res.code == '10022'  需要轮询的状态码
        const val PAY_POLLING_CODE1=10028
//        const val PAY_POLLING_CODE2=10026
//        const val PAY_POLLING_CODE3=10022
        const val PAY_SUCCESS=200 //支付成功
        const val PAY_CANCEL=300 //支付取消

        //status;//0待上架  1:上架 2：冻结 3：下架商品--

        const val GOODS_STATUS_WAIT_UPPER_SHELF=0
        const val GOODS_STATUS_UPPER_SHELF=1
        const val GOODS_STATUS_FROZEN=2
        const val GOODS_STATUS_LOWER_SHELF=3


        // 订单状态(( 1.未支付 2.用户已取消3.待配送 4.待自提 5.配送中  6.交易完成  7.店家已取消)-->
        const val ONLINE_ORDER_STATUS_NOPAY=1
        const val ONLINE_ORDER_STATUS_USER_CANCEL=2
        const val ONLINE_ORDER_STATUS_WAIT_DISTRIBUTION=3
        const val ONLINE_ORDER_STATUS_WAIT_SELFTAKE=4
        const val ONLINE_ORDER_STATUS_DISTRIBUTION=5
        const val ONLINE_ORDER_STATUS_SUCCESS=6
        const val ONLINE_ORDER_STATUS_CANCEL=7

        // 退款状态  1.退款中 2.退款成功 3.退款失败
        const val REFUND_ORDER_STATUS_DOING=1
        const val REFUND_ORDER_STATUS_SUCCESS=2
        const val REFUND_ORDER_STATUS_FAILED=3

        //交易渠道(POS  SCP  PADPOS APP)
        const val TRADE_RES_POS="POS"
        const val TRADE_RES_SCP="SCP"
        const val TRADE_RES_PADPOS="PADPOS"
        const val TRADE_RES_APP="APP"

        // 0今日 1近一周 2近一月
        const val DAY=0
        const val WEEK=1
        const val MONTH=2

        // 1.已入库 0 未入库
        const val STOCK_STATUS_IN=1
        const val STOCK_STATUS_OUT=0

        //盘点状态(1.已确认 0.未确认)
        const val STOCK_TAKING_YES=1
        const val STOCK_TAKING_NO=0

        //1：新增入库 0转化
        const val STOCK_TYPE_ADD=1
        const val STOCK_TYPE_CHANGE=0

        //按销量降序或者升序(asc,desc)
        const val SORT_ORDER_DOWN="desc"
        const val SORT_ORDER_UP="asc"

        // 送货方式(0.自提 1.配送)
        const val DISTRIBUTION_TYPE_SELFTAKE = 0 //自提
        const val DISTRIBUTION_TYPE_SEND = 1 //配送

        const val CART_MAX_NUM=9999

        //选择相册类型
        const val Camera_TYPE = 0 //相机
        const val Gallery_TYPE = 1  //相册
        const val Cancel_TYPE = 2

    }
}