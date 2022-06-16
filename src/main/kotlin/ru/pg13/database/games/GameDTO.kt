package ru.pg13.database.games

import ru.pg13.features.games.models.CreateGameRequest
import ru.pg13.features.games.models.CreateGameResponse
import java.util.*

@kotlinx.serialization.Serializable
data class GameDTO(
    val gameID: String,
    val title: String,
    val description: String,
    val version: String,
    val size: String
)

fun CreateGameRequest.mapToGameDTO(): GameDTO =
    GameDTO(
        gameID = UUID.randomUUID().toString(),
        title = title,
        description = description,
        version = version,
        size = size
    )

fun GameDTO.mapToCreateGameResponse(): CreateGameResponse =
    CreateGameResponse(
        gameID = gameID,
        title = title,
        description = description,
        version = version,
        size = size
    )