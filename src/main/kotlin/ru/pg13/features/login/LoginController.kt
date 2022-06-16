package ru.pg13.features.login

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.pg13.InMemoryCache
import ru.pg13.TokenCache
import ru.pg13.database.tokens.TokenDTO
import ru.pg13.database.tokens.Tokens
import ru.pg13.database.users.Users
import ru.pg13.features.register.RegisterResponseRemote
import java.util.*

class LoginController(private val call: ApplicationCall) {

    suspend fun performLogin() {
        val receive = call.receive<LoginReceiveRemote>()
        val userDTO = Users.fetchUser(receive.login)
        if (userDTO == null) {
            call.respond(HttpStatusCode.BadRequest, "User not found")
        } else {
            if (userDTO.password == receive.password) {
                val token = UUID.randomUUID().toString()
                Tokens.insert(
                    TokenDTO(
                        rowId = UUID.randomUUID().toString(), login = receive.login,
                        token = token
                    )
                )
                call.respond(LoginResponseRemote(token = token))
            } else{
                call.respond(HttpStatusCode.BadRequest, "Invalid password")
            }
        }
    }
}