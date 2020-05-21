package com.epro.pos.listener

class CashierFunChangeEvent(isRefund: Boolean) {
    var isRefund = false
    init {
        this.isRefund = isRefund
    }
}