package com.klifikt.models

import org.jetbrains.exposed.sql.*

data class Module(val id: Int, val idModule: String, val title: String, val description: String)

object Modules : Table() {
    val id = integer("id").autoIncrement()
    val idModule = varchar("idModule", 12)
    val title = varchar("title", 128)
    val description = varchar("description", 1024)

    override val primaryKey = PrimaryKey(id)
}
