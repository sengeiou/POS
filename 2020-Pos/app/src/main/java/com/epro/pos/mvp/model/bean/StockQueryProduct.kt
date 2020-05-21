package com.epro.pos.mvp.model.bean

data class StockQueryProduct(val buyingPrice: String,
                             val goodsName: String,
                             val goodsTypeName: String,
                             val goodsUnitName: String,
                             val productBarCode: String,
                             val productNumber: Int,
                             val stockEarlyNumr: Int,
                             val retailPrice: String,
                             val specificationsValueNames: String)