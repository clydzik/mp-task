package com.example.mp.util

fun String.readResourceAsString() = object {}.javaClass.getResource(this)?.readText()
    ?: throw IllegalArgumentException("Could not find resource $this")
