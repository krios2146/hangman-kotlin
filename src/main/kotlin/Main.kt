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
        "E" -> println("Exiting...")
        else -> {
            println("There is no such option")
            startHub()
        }
    }
}

fun startGame() {
    val word = chooseWord()
    var wordMask = "*".repeat(word.length)

    var mistakes = 0

    while (mistakes < 6) {
        val guess = readUserInput()

        wordMask = updateWordMask(guess, word, wordMask)

        if (guess !in word) {
            mistakes++
        }

        drawHangman(mistakes)
        println(wordMask)

        if (!wordMask.contains("*")) {
            break
        }
    }

    when {
        mistakes == 6 -> println("You lost. The word was - $word")
        mistakes < 6 -> println("You won, congrats!")
    }

    startHub()
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

fun updateWordMask(guess: String, word: String, wordMask: String): String {
    if (guess !in word) {
        return wordMask
    }

    for (i in 0..word.length) {
        if (word[i].lowercase() == guess.lowercase()) {
            wordMask.replaceRange(i, i, guess)
        }
    }

    return wordMask
}

fun chooseWord(): String = File("dictionary.txt").readLines().random()

fun isAlphaNumeric(string: String): Boolean = string.lowercase() in "a".."z" || string.lowercase() in "0".."9"