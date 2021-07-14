package com.example.mp.infra.currency.nbp

import java.math.BigDecimal

data class NBPRate(val mid: BigDecimal)

data class NBPCurrencyRate(
        val code: String,
        val rates: List<NBPRate>
)
