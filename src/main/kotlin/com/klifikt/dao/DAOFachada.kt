package com.klifikt.dao

import java.util.UUID

interface DAOFachada<T> {
    suspend fun all(): List<T>
    suspend fun get(uuid: UUID): T?
    suspend fun add(o: T): T?
    suspend fun update(o: T): Boolean
    suspend fun delete(uuid: UUID): Boolean
}
