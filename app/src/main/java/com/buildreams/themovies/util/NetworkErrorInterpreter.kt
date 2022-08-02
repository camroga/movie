package com.buildreams.themortal.util

interface NetworkErrorInterpreter {
    fun interpret(status: Int): String
}