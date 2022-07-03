package ru.pg13.database.authorities

import org.jetbrains.exposed.sql.*
import ru.pg13.features.authority.models.Authorities
import ru.pg13.features.authority.models.Authority
import ru.pg13.features.songs.models.Song
import ru.pg13.features.songs.models.Songs
import ru.pg13.utils.dbQuery

class DAOFacadeImpl : DAOAuthoFacade {

    private fun resultRowToAuthority(row: ResultRow) = Authority(
        id = row[Authorities.id],
        title = row[Authorities.title],
        imageUrl = row[Authorities.imageUrl]
    )
    override suspend fun allAuthorities(): List<Authority> = dbQuery {
        Authorities.selectAll().map(::resultRowToAuthority)
    }

    override suspend fun authority(id: Int): Authority? = dbQuery {
        Authorities
            .select { Authorities.id eq id }
            .map(::resultRowToAuthority)
            .singleOrNull()
    }

    override suspend fun addAuthority(id: Int, title: String, imageUrl: String): Authority? = dbQuery {
        val insertAuthority = Authorities.insert {
            it[Authorities.id] = id
            it[Authorities.title] = title
            it[Authorities.imageUrl] = imageUrl
        }
        insertAuthority.resultedValues?.singleOrNull()?.let(::resultRowToAuthority)
    }

    override suspend fun editAuthority(id: Int, title: String, imageUrl: String): Boolean = dbQuery {
        Authorities.update({ Authorities.id eq id }) {
            it[Authorities.title] = title
            it[Authorities.imageUrl] = imageUrl
        } > 0
    }

    override suspend fun deleteAuthority(id: Int): Boolean = dbQuery {
        Authorities.deleteWhere { Authorities.id eq id } > 0
    }
}