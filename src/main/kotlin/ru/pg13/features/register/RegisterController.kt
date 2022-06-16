package ru.pg13.features.register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import ru.pg13.InMemoryCache
import ru.pg13.TokenCache
import ru.pg13.database.tokens.TokenDTO
import ru.pg13.database.tokens.Tokens
import ru.pg13.database.users.UserDTO
import ru.pg13.database.users.Users
import ru.pg13.utils.isValidEmail
import java.util.*

class RegisterController(private val call: ApplicationCall) {
    suspend fun registerNewUser() {
        val registerReceiveRemote = call.receive<RegisterReceiveRemote>()
        if (!registerReceiveRemote.email.isValidEmail()){
            call.respond(HttpStatusCode.BadRequest, "Email is not valid")
        }
        val userDTO = Users.fetchUser(registerReceiveRemote.login)

        if (userDTO != null) {
            call.respond(HttpStatusCode.Conflict, "User already exist")
        } else {
            val token = UUID.randomUUID().toString()

            try {
                Users.insert(
                    UserDTO(
                        login = registerReceiveRemote.login,
                        password = registerReceiveRemote.password,
                        email = registerReceiveRemote.email,
                        username = ""
                    )
                )
            } catch (e: ExposedSQLException) {
                call.respond(HttpStatusCode.Conflict, "User already exists")
            }

            Tokens.insert(
                TokenDTO(
                    rowId = UUID.randomUUID().toString(), login = registerReceiveRemote.login,
                    token = token
                )
            )
            call.respond(RegisterResponseRemote(token = token))
        }
    }
}