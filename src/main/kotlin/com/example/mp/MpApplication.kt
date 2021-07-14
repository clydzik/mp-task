package com.example.mp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MpApplication

fun main(args: Array<String>) {
	runApplication<MpApplication>(*args)
}
