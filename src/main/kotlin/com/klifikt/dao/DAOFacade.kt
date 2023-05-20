package com.klifikt.dao

import com.klifikt.models.Module

interface DAOFacade {
    suspend fun allModules(): List<Module>
    suspend fun module(id: Int): Module?
    suspend fun addNewModule(idModule: String, title: String, description: String): Module?
    suspend fun editModule(id: Int, idModule: String, title: String, description: String): Boolean
    suspend fun deleteModule(id: Int): Boolean
}
