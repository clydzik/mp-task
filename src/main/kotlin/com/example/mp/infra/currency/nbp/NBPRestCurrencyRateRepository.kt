package com.example.mp.infra.currency.nbp

import com.example.mp.domain.currency.CurrencyRate
import com.example.mp.domain.currency.CurrencyRateRepository
import com.example.mp.domain.currency.Rate
import com.example.mp.domain.user.balance.Currency
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.client.WebClient

@Repository
class NBPRestCurrencyRateRepository(
        @Qualifier(NBPWebClientConfig.NBP_CONVERTER_QUALIFIER) private val webClient: WebClient
) : CurrencyRateRepository {

    override fun getRate(currency: Currency): CurrencyRate = webClient
            .get()
            .uri { uriBuilder -> uriBuilder.path(GET_CURRENCY_RATE_BY_CURRENCY).build(currency.value.currencyCode) }
            .retrieve()
            .bodyToMono(NBPCurrencyRate::class.java)
            .map { it.toDomain() }
            .block()!!

    private fun NBPCurrencyRate.toDomain() = CurrencyRate(
            currency = Currency.from(code),
            rate = Rate(rates.first().mid)
    )

    companion object {

        private const val GET_CURRENCY_RATE_BY_CURRENCY = "/api/exchangerates/rates/A/{currency}"
    }
}

@Configuration
class NBPWebClientConfig(@Value("\${currency-rate.base-url}") val baseUrl: String) {

    @Bean(NBP_CONVERTER_QUALIFIER)
    fun defaultWebClient(): WebClient =
            WebClient.builder()
                    .baseUrl(baseUrl)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build()

    companion object {

        const val NBP_CONVERTER_QUALIFIER = "nbpWebClient"
    }
}
