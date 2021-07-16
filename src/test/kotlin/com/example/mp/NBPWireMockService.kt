package com.example.mp

import com.example.mp.util.readResourceAsString
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

object NBPWireMockService {

    fun setupOk(currency: String = "USD", resource: String = "/wiremock/nbp-currency/usd.json") = stubFor(
            get("/nbp/api/exchangerates/rates/A/$currency")
                    .withHeader(HttpHeaders.CONTENT_TYPE, WireMock.matching(MediaType.APPLICATION_JSON_VALUE))
                    .willReturn(
                            WireMock.aResponse()
                                    .withStatus(HttpStatus.OK.value())
                                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                    .withBody(resource.readResourceAsString())
                    )
    ).let { }
}
