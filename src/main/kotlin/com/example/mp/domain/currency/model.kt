package com.example.mp.domain.currency

import com.example.mp.domain.user.balance.Currency
import java.math.BigDecimal

class Rate(val value:BigDecimal)

class CurrencyRate(val currency: Currency, val rate: Rate)
