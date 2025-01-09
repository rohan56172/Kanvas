package org.github.kanvas

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform