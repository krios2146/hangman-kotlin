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
        "P" -> {
            println("Starting game...")
            startGame()
        }

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
    val guesses = mutableSetOf<String>()
    var mistakes = 0

    while (mistakes < 6) {
        drawHangman(mistakes)
        println(wordMask)
        println("mistakes - $mistakes")
        println("previous guesses - $guesses")

        val guess = readUserInput()

        wordMask = updateWordMask(guess, word, wordMask)
        guesses.add(guess)

        if (guess !in word) {
            mistakes++
        }

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

    var updatedWordMask = wordMask

    for (i in word.indices) {
        if (word[i].lowercase() == guess.lowercase()) {
            updatedWordMask = updatedWordMask.replaceRange(i, i + 1, guess)
        }
    }

    return updatedWordMask
}

fun drawHangman(mistakes: Int) {
    when (mistakes) {
        0 -> println(
            """
                      +---+
                      |   |
                          |
                          |
                          |
                          |
                    =========
            """.trimIndent()
        )

        1 -> println(
            """
                      +---+
                      |   |
                      O   |
                          |
                          |
                          |
                    =========
            """.trimIndent()
        )

        2 -> println(
            """
                      +---+
                      |   |
                      0   |
                      |   |
                          |
                          |
                    =========
            """.trimIndent()
        )

        3 -> println(
            """
                      +---+
                      |   |
                      0   |
                     /|   |
                          |
                          |
                    =========
            """.trimIndent()
        )

        4 -> println(
            """
                      +---+
                      |   |
                      0   |
                     /|\  |
                          |
                          |
                    =========
            """.trimIndent()
        )

        5 -> println(
            """
                      +---+
                      |   |
                      0   |
                     /|\  |
                     /    |
                          |
                    =========
            """.trimIndent()
        )

        6 -> println(
            """
                      +---+
                      |   |
                      0   |
                     /|\  |
                     / \  |
                          |
                    =========
            """.trimIndent()
        )
    }
}

fun chooseWord(): String = File("src/main/resources/dictionary.txt").readLines().random()

fun isAlphaNumeric(string: String): Boolean = string.lowercase() in "a".."z" || string.lowercase() in "0".."9"