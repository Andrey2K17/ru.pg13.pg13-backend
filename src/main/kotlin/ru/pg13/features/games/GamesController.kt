package ru.pg13.features.games

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.pg13.database.games.Games
import ru.pg13.database.games.mapToCreateGameResponse
import ru.pg13.database.games.mapToGameDTO
import ru.pg13.features.games.models.CreateGameRequest
import ru.pg13.features.games.models.FetchGamesRequest
import ru.pg13.utils.TokenCheck

class GamesController(private val call: ApplicationCall) {
    suspend fun performSearch() {
        val request = call.receive<FetchGamesRequest>()
        val token = call.request.headers["Bearer-Authorization"]

        if (TokenCheck.isTokenValid(token.orEmpty()) || TokenCheck.isTokenAdmin(token.orEmpty())) {
            call.respond(Games.fetchGames().filter{ it.title.contains(request.searchQuery, ignoreCase = true)})
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
        }
    }

    suspend fun createGame() {
        val token = call.request.headers["Bearer-Authorization"]
        if (TokenCheck.isTokenAdmin(token.orEmpty())) {
            val request = call.receive<CreateGameRequest>()
            val game = request.mapToGameDTO()
            Games.insert(game)
            call.respond(game.mapToCreateGameResponse())
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
        }
    }
}