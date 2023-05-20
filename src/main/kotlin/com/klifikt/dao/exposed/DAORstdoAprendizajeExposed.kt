package com.klifikt.dao.exposed

import com.klifikt.dao.DAOFachada
import com.klifikt.dao.DatabaseFactory.dbQuery
import com.klifikt.models.RstdoAprendizaje
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class DAORstdoAprendizajeExposed : DAOFachada<RstdoAprendizaje> {

    private fun entityToModel(entity: EntityRstdoAprendizaje) = RstdoAprendizaje(
        id = entity.id.value,
        code = entity.code,
        description = entity.description,
    )

    override suspend fun all(): List<RstdoAprendizaje> = dbQuery {
        EntityRstdoAprendizaje
            .all()
            .map(::entityToModel)
    }

    override suspend fun get(uuid: UUID): RstdoAprendizaje? = dbQuery {
        EntityRstdoAprendizaje
            .find { RstdosAprendizajeTable.id eq uuid }
            .map(::entityToModel)
            .firstOrNull()
    }

    override suspend fun add(o: RstdoAprendizaje): RstdoAprendizaje? = dbQuery {
        EntityRstdoAprendizaje
            .new {
                code = o.code
                description = o.description
            }.let(::entityToModel)
    }

    override suspend fun update(o: RstdoAprendizaje): Boolean = dbQuery {
        EntityRstdoAprendizaje
            .find { RstdosAprendizajeTable.id eq o.id }
            .firstOrNull()
            ?.let {
                it.description = o.description
                it.code = o.code
            } != null
    }

    override suspend fun delete(uuid: UUID): Boolean = dbQuery {
        EntityRstdoAprendizaje
            .find { RstdosAprendizajeTable.id eq uuid }
            .firstOrNull()
            ?.let {
                it.delete()
            } != null
    }
}

object RstdosAprendizajeTable : IdTable<UUID>() {
    override val id = uuid("id").entityId()
    val code = varchar("code", 12).uniqueIndex("unique_code")
    val description = varchar("description", 1024)

    // override val primaryKey = PrimaryKey(id)
}

class EntityRstdoAprendizaje(id: EntityID<UUID>) : Entity<UUID>(id) {
    companion object : EntityClass<UUID, EntityRstdoAprendizaje>(RstdosAprendizajeTable)

    var code by RstdosAprendizajeTable.code
    var description by RstdosAprendizajeTable.description
}
/*
val daoRstdosApdzje = DAORstdoAprendizajeExposed().apply {
    runBlocking {
        if (all().isEmpty()) {
            add(
                RstdoAprendizaje(
                    code = "34",
                    description = "description ole",
                ),
            )

        }
    }
}
*/
