package ru.pg13.plugins

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import kotlinx.serialization.Serializable

@Serializable
data class Test(
    val text: String
)

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respond(Test(text = "Hello, Krasnoyarsk!"))
        }
        get("/test") {
            call.respondText("наконец то эта залупа завелась!")
        }
    }
}
