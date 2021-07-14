package com.example.mp.domain.user.balance

import com.example.mp.domain.currency.CurrencyRate
import com.example.mp.domain.currency.CurrencyRateRepository
import com.example.mp.domain.currency.Rate
import com.example.mp.domain.user.Id
import com.example.mp.domain.user.Name
import com.example.mp.domain.user.User
import com.example.mp.domain.user.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.kotlin.mock
import java.math.BigDecimal
import java.util.UUID

class BalanceServiceTest {

    private val userRepository = mock<UserRepository> {}
    private val userBalanceRepository = mock<UserBalanceRepository> {}
    private val currencyRateRepository = mock<CurrencyRateRepository> {}

    private val balanceService = BalanceService(
            userRepository = userRepository,
            userBalanceRepository = userBalanceRepository,
            currencyRateRepository = currencyRateRepository
    )

    @Test
    fun shouldProvideRecalculatedBalanceForUser() {
        given(userRepository.get(USER.id)).willReturn(USER)
        given(userBalanceRepository.get(USER.id)).willReturn(BALANCE)
        given(currencyRateRepository.getRate(TARGET_CURRENCY)).willReturn(CURRENCY_RATE)
        val expectedBalance = Balance(
                USER.id,
                monetaryAmount = MonetaryAmount(
                        amount = Amount.from("26.05000"),
                        currency = TARGET_CURRENCY
                ),
        )

        val result = balanceService.getBalance(USER.id, TARGET_CURRENCY)

        assertThat(result).isEqualTo(expectedBalance)
    }

    companion object {

        val USER = User(
                id = Id(UUID.randomUUID()),
                name = Name("UserName")
        )
        val BALANCE = Balance(
                userId = USER.id,
                monetaryAmount = MonetaryAmount(
                        amount = Amount.from("104.20"),
                        currency = Currency.from("PLN")
                )
        )

        val TARGET_CURRENCY = Currency.from("USD")
        val CURRENCY_RATE = CurrencyRate(
                rate = Rate(BigDecimal("4")),
                currency = TARGET_CURRENCY
        )
    }
}
