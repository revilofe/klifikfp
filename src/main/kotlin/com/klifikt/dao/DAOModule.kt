package com.klifikt.dao

import com.klifikt.dao.DatabaseFactory.dbQuery
import com.klifikt.models.Module
import com.klifikt.models.Modules
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOModule : DAOFacade {
    private fun resultRowToObject(row: ResultRow) = Module(
        id = row[Modules.id],
        idModule = row[Modules.idModule],
        title = row[Modules.title],
        description = row[Modules.description],
    )

    override suspend fun allModules(): List<Module> = dbQuery {
        Modules.selectAll().map(::resultRowToObject)
    }

    override suspend fun module(id: Int): Module? = dbQuery {
        Modules
            .select { Modules.id eq id }
            .map(::resultRowToObject)
            .singleOrNull()
    }

    override suspend fun addNewModule(idModule: String, title: String, description: String): Module? = dbQuery {
        val insertStatement = Modules.insert {
            it[Modules.idModule] = idModule
            it[Modules.title] = title
            it[Modules.description] = description
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToObject)
    }

    override suspend fun editModule(id: Int, idModule: String, title: String, description: String): Boolean = dbQuery {
        Modules.update({ Modules.id eq id }) {
            it[Modules.idModule] = idModule
            it[Modules.title] = title
            it[Modules.description] = description
        } > 0
    }

    override suspend fun deleteModule(id: Int): Boolean = dbQuery {
        Modules.deleteWhere { Modules.id eq id } > 0
    }
}

val daoModule: DAOFacade = DAOModule().apply {
    runBlocking {
        if (allModules().isEmpty()) {
            addNewModule(
                "ED540",
                "Entornos de desarrollo",
                "Entornos de desarrollo y programación",
            )
        }
    }
}
