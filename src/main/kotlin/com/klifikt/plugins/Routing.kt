package com.klifikt.plugins

import com.klifikt.models.Module
import com.klifikt.models.modules
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
                call.respond(FreeMarkerContent("index.ftl", mapOf("modules" to modules)))
            }
            get("new") {
                call.respond(FreeMarkerContent("new.ftl", model = null))
            }
            post {
                val formParameters = call.receiveParameters()
                val idModule = formParameters.getOrFail("idModule")
                val title = formParameters.getOrFail("title")
                val description = formParameters.getOrFail("description")
                val newEntry = Module.newEntry(idModule, title, description)

                modules.add(newEntry)
                call.respondRedirect("/modules/${newEntry.id}")
            }
            get("{id}") {
                val id = call.parameters.getOrFail<Int>("id").toInt()
                call.respond(FreeMarkerContent("show.ftl", mapOf("module" to modules.find { it.id == id })))
            }
            get("{id}/edit") {
                val id = call.parameters.getOrFail<Int>("id").toInt()
                call.respond(FreeMarkerContent("edit.ftl", mapOf("module" to modules.find { it.id == id })))
            }
            post("{id}") {
                val id = call.parameters.getOrFail<Int>("id").toInt()
                val formParameters = call.receiveParameters()
                when (formParameters.getOrFail("_action")) {
                    "update" -> {
                        val index = modules.indexOf(modules.find { it.id == id })
                        val idModule = formParameters.getOrFail("idModule")
                        val title = formParameters.getOrFail("title")
                        val body = formParameters.getOrFail("description")
                        modules[index].idModule = idModule
                        modules[index].title = title
                        modules[index].description = body
                        call.respondRedirect("/modules/$id")
                    }

                    "delete" -> {
                        modules.removeIf { it.id == id }
                        call.respondRedirect("/modules")
                    }
                }
            }
        }
    }
}
