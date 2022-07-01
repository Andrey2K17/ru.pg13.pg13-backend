package ru.pg13.features.songs

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking
import ru.pg13.database.songs.DAOFacade
import ru.pg13.database.songs.DAOFacadeImpl

fun Application.configureSongsRouting() {
    val dao: DAOFacade = DAOFacadeImpl().apply {
        runBlocking {
            if(allSongs().isEmpty()) {
                addNewSong(12,"Cash in cash out", "21 Savage")
            }
        }
    }
    routing {
        get("/songs") {
           call.respond(dao.allSongs())
           //call.respond(mapOf("songs" to DAOFacadeImpl().dao.allSongs()[0].id))
        }
    }
}