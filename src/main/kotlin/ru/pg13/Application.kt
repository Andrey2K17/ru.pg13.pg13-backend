package ru.pg13

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import ru.pg13.features.games.configureGamesRouting
import ru.pg13.features.login.configureLoginRouting
import ru.pg13.features.register.configureRegisterRouting
import ru.pg13.plugins.*

fun main() {
    Database.connect("jdbc:postgresql://localhost:5432/playzone", driver = "org.postgresql.Driver",
    user = "postgres", password = "129837qaw")
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureLoginRouting()
        configureRegisterRouting()
        configureGamesRouting()
        configureSerialization()
    }.start(wait = true)
}
