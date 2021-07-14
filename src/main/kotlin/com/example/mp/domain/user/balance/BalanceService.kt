package com.example.mp.domain.user.balance

import com.example.mp.domain.user.Id
import com.example.mp.domain.user.UserRepository
import com.example.mp.domain.user.currency.CurrencyConversionsRepository

class BalanceService(
        private val userRepository: UserRepository,
        private val userBalanceRepository: UserBalanceRepository,
        private val currencyConversionsRepository: CurrencyConversionsRepository
) {

    fun getBalance(userId: Id, currency: Currency) = userRepository.get(userId)
            .let { userBalanceRepository.get(it.id) }
            .let {
                Balance(
                        userId = it.userId,
                        monetaryAmount = currencyConversionsRepository.getConversion(it.monetaryAmount, currency)
                )
            }
}
