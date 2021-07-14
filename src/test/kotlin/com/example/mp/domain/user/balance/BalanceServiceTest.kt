package com.example.mp.domain.user.balance

import com.example.mp.domain.user.Id
import com.example.mp.domain.user.Name
import com.example.mp.domain.user.User
import com.example.mp.domain.user.UserRepository
import com.example.mp.domain.user.currency.CurrencyConversionsRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.kotlin.mock
import java.util.UUID

class BalanceServiceTest {

    private val userRepository = mock<UserRepository> {}
    private val userBalanceRepository = mock<UserBalanceRepository> {}
    private val currencyConversionsRepository = mock<CurrencyConversionsRepository> {}

    private val balanceService = BalanceService(
            userRepository = userRepository,
            userBalanceRepository = userBalanceRepository,
            currencyConversionsRepository = currencyConversionsRepository
    )

    @Test
    fun shouldProvideBalanceForUser() {
        given(userRepository.get(USER.id)).willReturn(USER)
        given(userBalanceRepository.get(USER.id)).willReturn(BALANCE)
        given(currencyConversionsRepository.getConversion(BALANCE.monetaryAmount, TARGET_CURRENCY))
                .willReturn(CONVERTED_AMOUNT)
        val expectedBalance = Balance(
                USER.id,
                monetaryAmount = CONVERTED_AMOUNT
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
                        amount = Amount.from("123.24"),
                        currency = Currency.from("PLN")
                )
        )

        val TARGET_CURRENCY = Currency.from("USD")
        val CONVERTED_AMOUNT = MonetaryAmount(
                amount = Amount.from("15"),
                currency = TARGET_CURRENCY
        )
    }
}
