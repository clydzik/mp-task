package com.example.mp.domain.user.currency

import com.example.mp.domain.user.balance.Currency
import com.example.mp.domain.user.balance.MonetaryAmount

interface CurrencyConversionsRepository {

    fun getConversion(monetaryAmount: MonetaryAmount, currency: Currency): MonetaryAmount
}
