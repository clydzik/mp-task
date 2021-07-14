package com.example.mp.domain.user.balance

import com.example.mp.domain.user.Id
import java.math.BigDecimal

data class Currency(val value: java.util.Currency) {
    companion object {

        fun from(currencyCode: String) = Currency(java.util.Currency.getInstance(currencyCode))
    }
}

data class Amount(val value: BigDecimal) {
    companion object {

        fun from(value: String) = Amount(BigDecimal(value))
    }
}

data class MonetaryAmount(val amount: Amount, val currency: Currency)

data class Balance(val userId: Id, val monetaryAmount: MonetaryAmount)
