package com.buildreams.themovies.util

interface NetworkErrorInterpreter {
    fun interpret(status: Int): String
}