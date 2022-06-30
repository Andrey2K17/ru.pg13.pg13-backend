package ru.pg13.features.songs.models

import org.jetbrains.exposed.sql.Table

data class Song(val id: Int, val title: String, val singer: String)

object Songs : Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 50)
    val singer = varchar("singer", 50)

    override val primaryKey = PrimaryKey(id)
}