package ru.pg13

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import ru.pg13.features.games.configureGamesRouting
import ru.pg13.features.login.configureLoginRouting
import ru.pg13.features.register.configureRegisterRouting
import ru.pg13.plugins.*
import java.net.URI

fun main() {
    //Database.connect("jdbc:postgresql://localhost:5432/playzone", driver = "org.postgresql.Driver",
    //user = "postgres", password = "129837qaw")

    val config = HikariConfig("hikari.properties")
    //config.jdbcUrl = System.getenv("DATABASE_URL")
    //config.driverClassName = System.getenv("JDBC_DRIVER")
    val uri = URI(System.getenv("DATABASE_URL"))
    val username = uri.userInfo.split(":").toTypedArray()[0]
    val password = uri.userInfo.split(":").toTypedArray()[1]

    //config.jdbcUrl =
        //"jdbc:postgresql://" + uri.host + ":" + uri.port + uri.path + "?sslmode=require" + "&user=$username&password=$password"

    val dataSource = HikariDataSource(config)

    Database.connect(dataSource)
    embeddedServer(Netty, port = System.getenv("PORT").toInt()) {
        configureRouting()
        configureLoginRouting()
        configureRegisterRouting()
        configureGamesRouting()
        configureSerialization()
    }.start(wait = true)
}
