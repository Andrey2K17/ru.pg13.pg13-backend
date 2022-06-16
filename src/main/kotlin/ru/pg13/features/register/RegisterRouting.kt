package ru.pg13.features.register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.pg13.InMemoryCache
import ru.pg13.TokenCache
import ru.pg13.features.login.LoginReceiveRemote
import ru.pg13.features.login.LoginResponseRemote
import ru.pg13.utils.isValidEmail
import java.util.*

fun Application.configureRegisterRouting() {

    routing {
        post("/register") {
            val registerController = RegisterController(call)
            registerController.registerNewUser()
        }
    }
}