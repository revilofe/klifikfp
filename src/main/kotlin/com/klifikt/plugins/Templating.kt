package com.klifikt.plugins

import freemarker.cache.*
import freemarker.core.HTMLOutputFormat
import io.ktor.server.freemarker.*
import io.ktor.server.response.*
import io.ktor.server.html.*
import kotlinx.html.*
import kotlinx.css.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureTemplating() {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
        outputFormat = HTMLOutputFormat.INSTANCE
    }


    routing {
        get("/html-freemarker") {
            call.respond(FreeMarkerContent("index-back.ftl", mapOf("data" to IndexData(listOf(1, 2, 3))), ""))
        }
        get("/html-dsl") {
            call.respondHtml {
                body {
                    h1 { +"HTML" }
                    ul {
                        for (n in 1..10) {
                            li { +"$n" }
                        }
                    }
                }
            }
        }
        get("/styles.css") {
            call.respondCss {
                body {
                    backgroundColor = Color.darkBlue
                    margin(0.px)
                }
                rule("h1.page-title") {
                    color = Color.white
                }
            }
        }

        get("/html-css-dsl") {
            call.respondHtml {
                head {
                    link(rel = "stylesheet", href = "/styles.css", type = "text/css")
                }
                body {
                    h1(classes = "page-title") {
                        +"Hello from Ktor!"
                    }
                }
            }
        }
    }
}

data class IndexData(val items: List<Int>)

suspend inline fun ApplicationCall.respondCss(builder: CSSBuilder.() -> Unit) {
    this.respondText(CSSBuilder().apply(builder).toString(), ContentType.Text.CSS)
}

