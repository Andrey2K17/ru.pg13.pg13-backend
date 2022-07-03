package ru.pg13.features.authority.models

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import kotlinx.coroutines.runBlocking
import ru.pg13.database.authorities.DAOAuthoFacade
import ru.pg13.database.authorities.DAOFacadeImpl
import ru.pg13.features.songs.models.Song

fun Application.configureAuthorityRouting() {
    val dao: DAOAuthoFacade = DAOFacadeImpl().apply {
        runBlocking {
            if(allAuthorities().isEmpty()) {
                addAuthority(12,"Cash in cash out", "https://sun9-85.userapi.com/impf/34oJ5p6sTg1E4utat-k6kGC1EV-J00yytv9S_A/6GNoQcK4TbM.jpg?size=1200x1600&quality=95&sign=22916e52a1e414dbfe42392c05629b1a&type=album")
            }
        }
    }
    routing {
        get("/authorities") {
            call.respond(dao.allAuthorities())
            //call.respond(mapOf("songs" to DAOFacadeImpl().dao.allSongs()[0].id))
        }
        get("/authorities/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            call.respond(dao.authority(id) ?: error("Авторитет с указанным id не найдена"))
        }
        post("/authorities/create") {
            val authority = call.receive<Song>()
            call.respond(dao.addAuthority(authority.id, authority.title, authority.singer) ?: error("Не удалось создать"))
        }
        post("/authorities/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val formParameters = call.receiveParameters()
            print("parameters123: $formParameters")
            when(formParameters.getOrFail("action")) {
                "update" -> {
                    val title = formParameters.getOrFail("title")
                    val imageURL = formParameters.getOrFail("imageUrl")
                    dao.editAuthority(id, title, imageURL)
                    call.respondRedirect("/authorities/$id")
                }
                "delete" -> {
                    dao.deleteAuthority(id)
                    call.respondRedirect("/authorities")
                }
            }
        }
    }
}