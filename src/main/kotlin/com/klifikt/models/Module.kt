package com.klifikt.models

import java.util.UUID
import java.util.concurrent.atomic.AtomicInteger

class Module private constructor(val id: Int, var idModule: String, var title: String, var description: String) {
    companion object {
        private val idCounter = AtomicInteger()

        fun newEntry(idModule: String, title: String, description: String) = Module(idCounter.getAndIncrement(), idModule, title, description)
    }
}

val modules = mutableListOf(Module.newEntry(
    "PR456",
    "Programación",
    "Programación, lenguajes de programación, etc."
))