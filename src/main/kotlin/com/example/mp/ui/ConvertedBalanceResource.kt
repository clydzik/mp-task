package com.example.mp.ui

import com.example.mp.domain.user.Id
import com.example.mp.domain.user.UserNotFoundException
import com.example.mp.domain.user.balance.Balance
import com.example.mp.domain.user.balance.BalanceService
import com.example.mp.domain.user.balance.Currency
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ConvertedBalanceResource(private val balanceService: BalanceService) {

    @GetMapping("/users/{id}/balance")
    fun getConvertedBalance(@PathVariable id: String, @RequestParam("currency") currency: String) = balanceService
            .getBalance(
                    userId = Id.from(id),
                    currency = Currency.from(currency)
            ).toUi()

    private fun Balance.toUi() = ConvertedBalance(
            balance = monetaryAmount.amount.value.toString(),
            currency = monetaryAmount.currency.value.toString()
    )

    @ExceptionHandler(UserNotFoundException::class)
    fun notFound(exception: Exception) = ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ErrorMessage.of(exception))
}
