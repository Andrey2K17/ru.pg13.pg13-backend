package ru.pg13.database.songs

import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import ru.pg13.features.songs.models.Song
import ru.pg13.features.songs.models.Songs
import ru.pg13.utils.dbQuery

class DAOFacadeImpl : DAOFacade {

    private fun resultRowToSong(row: ResultRow) = Song(
        id = row[Songs.id],
        title = row[Songs.title],
        singer = row[Songs.singer]
    )
    override suspend fun allSongs(): List<Song> = dbQuery {
        Songs.selectAll().map(::resultRowToSong)
    }

    override suspend fun song(id: Int): Song? = dbQuery {
        Songs
            .select { Songs.id eq id }
            .map(::resultRowToSong)
            .singleOrNull()
    }

    override suspend fun addNewSong(title: String, singer: String): Song? = dbQuery {
        val insertSong = Songs.insert {
            it[Songs.title] = title
            it[Songs.singer] = singer
        }
        insertSong.resultedValues?.singleOrNull()?.let(::resultRowToSong)
    }

    override suspend fun editSong(id: Int, title: String, singer: String): Boolean = dbQuery {
        Songs.update({ Songs.id eq id }) {
            it[Songs.title] = title
            it[Songs.singer] = singer
        } > 0
    }

    override suspend fun deleteSong(id: Int): Boolean = dbQuery {
        Songs.deleteWhere { Songs.id eq id } > 0
    }

    val dao: DAOFacade = DAOFacadeImpl().apply {
        runBlocking {
            if(allSongs().isEmpty()) {
                addNewSong("Cash in cash out", "21 Savage")
            }
        }
    }
}