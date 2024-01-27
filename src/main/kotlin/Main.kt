package dev.krios2146

import java.io.File

fun main() {
    println()
    println("Welcome to the hangman game!")
    startHub()
}

fun startHub() {
    println()
    println("[P]lay   [R]ules    [E]xit") // maybe results & settings later

    val input = readUserInput()

    when (input.uppercase()) {
        "P" -> println("Game is started")
        "R" -> println("Rules")
        "E" -> return
        else -> {
            println("There is no such option")
            startHub()
        }
    }
}

fun readUserInput(): String {
    while (true) {
        val input = readln()

        when {
            input.isBlank() -> println("Please, enter some letter or digit")
            input.length > 1 -> println("Please, enter exactly one letter or digit")
            !isAlphaNumeric(input) -> println("Please, enter letter or digit, no other symbols are allowed")
            else -> return input
        }
    }
}

fun chooseWord(): String = File("dictionary.txt").readLines().random()

fun isAlphaNumeric(string: String): Boolean = string.lowercase() in "a".."z" || string.lowercase() in "0".."9"