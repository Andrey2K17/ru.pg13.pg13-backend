package ru.pg13.features.songs

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import kotlinx.coroutines.runBlocking
import org.h2.mvstore.FreeSpaceBitSet
import ru.pg13.database.songs.DAOFacade
import ru.pg13.database.songs.DAOFacadeImpl
import ru.pg13.features.songs.models.Song
import java.lang.reflect.Array.get

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
        get("/songs/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            call.respond(dao.song(id) ?: error("Песня с указанным id не найдена"))
        }
        post("/songs/create") {
            val song = call.receive<Song>()
            call.respond(dao.addNewSong(song.id, song.title, song.singer) ?: error("Не удалось создать"))
        }
        post("/songs/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val formParameters = call.receiveParameters()
            print("parameters123: $formParameters")
            when(formParameters.getOrFail("action")) {
                "update" -> {
                    val title = formParameters.getOrFail("title")
                    val singer = formParameters.getOrFail("singer")
                    dao.editSong(id, title, singer)
                    call.respondRedirect("/songs/$id")
                }
                "delete" -> {
                    dao.deleteSong(id)
                    call.respondRedirect("/songs")
                }
            }
        }
    }
}