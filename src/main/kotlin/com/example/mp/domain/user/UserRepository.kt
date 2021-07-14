package com.example.mp.domain.user

interface UserRepository {

    fun add(user: User)
    fun get(id:Id): User
}
