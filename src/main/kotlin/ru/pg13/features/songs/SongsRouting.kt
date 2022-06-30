package ru.pg13.features.songs

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.pg13.database.songs.DAOFacadeImpl

fun Application.configureSongsRouting() {

    routing {
        get("/songs") {
           call.respond(DAOFacadeImpl().dao.allSongs())
        }
    }
}