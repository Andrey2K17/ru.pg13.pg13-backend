package ru.pg13.database.songs

import ru.pg13.features.songs.models.Song

interface DAOFacade {
    suspend fun allSongs(): List<Song>
    suspend fun song(id: Int): Song?
    suspend fun addNewSong(id: Int, title: String, singer: String): Song?
    suspend fun editSong(id: Int, title: String, singer: String): Boolean
    suspend fun deleteSong(id: Int): Boolean
}