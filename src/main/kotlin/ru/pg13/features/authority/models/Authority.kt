package ru.pg13.features.authority.models

import org.jetbrains.exposed.sql.Table


@kotlinx.serialization.Serializable
data class Authority(val id: Int, val title: String, val imageUrl: String)

object Authorities : Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 50)
    val imageUrl = varchar("imageUrl", 200)

    override val primaryKey = PrimaryKey(id)
}