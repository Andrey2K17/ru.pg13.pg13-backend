package ru.pg13

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import ru.pg13.features.authority.models.configureAuthorityRouting
import ru.pg13.features.games.configureGamesRouting
import ru.pg13.features.login.configureLoginRouting
import ru.pg13.features.register.configureRegisterRouting
import ru.pg13.features.songs.configureSongsRouting
import ru.pg13.plugins.*
import java.net.URI

fun main() {
    val config = HikariConfig("hikari.properties")
    val dataSource = HikariDataSource(config)

    Database.connect(dataSource)
    embeddedServer(Netty, port = System.getenv("PORT").toInt()) {
        configureRouting()
        configureLoginRouting()
        configureRegisterRouting()
        configureGamesRouting()
        configureSerialization()
        configureSongsRouting()
        configureAuthorityRouting()
    }.start(wait = true)
}
