package com.example.mp.domain.user

import java.util.UUID

data class Id(val value: UUID) {
    companion object {

        fun from(value: String) = Id(UUID.fromString(value))
    }
}

data class Name(val value: String)

data class User(val id: Id, val name: Name)

class UserNotFoundException(id: Id) : RuntimeException("user not found: $id")
