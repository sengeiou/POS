package com.epro.pos.mvp.model.bean


/**
 * 产品分类
 */

// "goodsTypeId": "10",
// "goodsTypeName": "口味"
data class ProductCategory(val goodsTypeId:String ,val goodsTypeName:String,val goodsProductDetailList:ArrayList<Product> )