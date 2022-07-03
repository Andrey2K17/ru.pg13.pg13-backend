package ru.pg13.database.authorities

import ru.pg13.features.authority.models.Authority
import ru.pg13.features.songs.models.Song

interface DAOAuthoFacade {
    suspend fun allAuthorities(): List<Authority>
    suspend fun authority(id: Int): Authority?
    suspend fun addAuthority(id: Int, title: String, imageUrl: String): Authority?
    suspend fun editAuthority(id: Int, title: String, imageUrl: String): Boolean
    suspend fun deleteAuthority(id: Int): Boolean
}