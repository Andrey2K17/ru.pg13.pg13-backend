package ru.pg13

import ru.pg13.features.register.RegisterReceiveRemote

data class TokenCache(
    val login: String,
    val token: String
)

object InMemoryCache {
 val userList: MutableList<RegisterReceiveRemote> = mutableListOf()
 val tokens: MutableList<TokenCache> = mutableListOf()
}