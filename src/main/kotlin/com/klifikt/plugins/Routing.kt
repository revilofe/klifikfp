package com.klifikt.plugins

import com.klifikt.dao.daoModule
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondRedirect("modules")
        }
        route("modules") {
            get {
                call.respond(FreeMarkerContent("index.ftl", mapOf("modules" to daoModule.allModules())))
            }
            get("new") {
                call.respond(FreeMarkerContent("new.ftl", model = null))
            }
            post {
                val formParameters = call.receiveParameters()
                val idModule = formParameters.getOrFail("idModule")
                val title = formParameters.getOrFail("title")
                val description = formParameters.getOrFail("description")
                val newEntry = daoModule.addNewModule(idModule, title, description)

                call.respondRedirect("/modules/${newEntry?.id}")
            }
            get("{id}") {
                val id = call.parameters.getOrFail<Int>("id").toInt()
                call.respond(FreeMarkerContent("show.ftl", mapOf("module" to daoModule.module(id))))
            }
            get("{id}/edit") {
                val id = call.parameters.getOrFail<Int>("id").toInt()
                call.respond(FreeMarkerContent("edit.ftl", mapOf("module" to daoModule.module(id))))
            }
            post("{id}") {
                val id = call.parameters.getOrFail<Int>("id").toInt()
                val formParameters = call.receiveParameters()
                when (formParameters.getOrFail("_action")) {
                    "update" -> {
                        val idModule = formParameters.getOrFail("idModule")
                        val title = formParameters.getOrFail("title")
                        val description = formParameters.getOrFail("description")
                        daoModule.editModule(id, idModule, title, description)
                        call.respondRedirect("/modules/$id")
                    }

                    "delete" -> {
                        daoModule.deleteModule(id)
                        call.respondRedirect("/modules")
                    }
                }
            }
        }
    }
}
