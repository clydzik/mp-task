package com.example.mp.domain.user.balance

import com.example.mp.domain.currency.CurrencyRateRepository
import com.example.mp.domain.user.Id
import com.example.mp.domain.user.UserRepository
import org.springframework.stereotype.Service
import java.math.RoundingMode

@Service
class BalanceService(
        private val userRepository: UserRepository,
        private val userBalanceRepository: UserBalanceRepository,
        private val currencyRateRepository: CurrencyRateRepository
) {

    fun getBalance(userId: Id, currency: Currency) = userRepository.get(userId)
            .let { userBalanceRepository.get(it.id) }
            .let {
                Balance(
                        userId = it.userId,
                        monetaryAmount = convert(it.monetaryAmount, currency)
                )
            }

    private fun convert(monetaryAmount: MonetaryAmount, currency: Currency): MonetaryAmount {
        val currencyRate = currencyRateRepository.getRate(currency)
        return MonetaryAmount(
                amount = Amount(monetaryAmount.amount.value.divide(currencyRate.rate.value, 2, RoundingMode.HALF_UP)),
                currency = currencyRate.currency
        )
    }
}
