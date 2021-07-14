package com.example.mp.domain.currency

import com.example.mp.domain.user.balance.Currency

interface CurrencyRateRepository {

    fun getRate(currency: Currency): CurrencyRate
}
