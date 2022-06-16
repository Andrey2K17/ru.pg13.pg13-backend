package ru.pg13.features.games.models
import kotlinx.serialization.Serializable

@Serializable
data class FetchGamesRequest(
    val searchQuery: String
)