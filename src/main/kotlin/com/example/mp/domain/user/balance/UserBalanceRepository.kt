package com.example.mp.domain.user.balance

import com.example.mp.domain.user.Id

interface UserBalanceRepository {

    fun add(balance: Balance)
    fun get(userId: Id): Balance
}
