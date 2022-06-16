package ru.pg13.database.users

class UserDTO(
    val login: String,
    val password: String,
    val email: String?,
    val username: String
)