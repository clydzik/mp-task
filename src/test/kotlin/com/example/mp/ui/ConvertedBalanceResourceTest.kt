package com.example.mp.ui

import com.example.mp.AbstractIntegrationTestCase
import com.example.mp.NBPWireMockService
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.empty
import org.hamcrest.Matchers.not
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.util.UUID

class ConvertedBalanceResourceTest : AbstractIntegrationTestCase() {

    @BeforeAll
    fun setupCurrency() {
        NBPWireMockService.setupOk()
    }

    @Test
    fun `should return converted balance for test user`() {
        defaultRequest()
                .get("/users/$TEST_USER/balance?currency=USD")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("balance", `is`("3107.09"))
                .body("currency", `is`("USD"))
    }

    @Test
    fun `should return not found for unknown user`() {
        val unknownUser = UUID.randomUUID().toString()
        defaultRequest()
                .get("/users/$unknownUser/balance?currency=USD")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", `is`(not(empty<String>())))
    }

    companion object {

        private const val TEST_USER = "802e3fe5-5d1d-42e3-bae1-5c2af37f513c"
    }
}
