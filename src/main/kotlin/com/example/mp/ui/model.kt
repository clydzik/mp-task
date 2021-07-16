package com.example.mp.ui


data class ErrorMessage(val message: String) {
    companion object {
        fun of(throwable: Throwable) = ErrorMessage(throwable.message ?: "")
    }
}

data class ConvertedBalance(val balance: String, val currency: String)
