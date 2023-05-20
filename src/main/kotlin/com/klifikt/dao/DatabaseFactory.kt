package com.klifikt.dao

import com.klifikt.models.Modules
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import org.jetbrains.exposed.sql.transactions.experimental.*

object DatabaseFactory {
    fun init() {
        val driverClassName = "org.h2.Driver"
        val jdbcURL = "jdbc:h2:file:./build/db"
        // val user = "user"
        // val pass = "user"
        val database = Database.connect(jdbcURL, driverClassName)
        // val database = Database.connect(jdbcURL, driverClassName, user = user, password = pass)
        transaction(database) {
            SchemaUtils.create(Modules)
            // SchemaUtils.create(RstdosAprendizajeTable)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) {
            // addLogger(StdOutSqlLogger)
            block()
        }
}
