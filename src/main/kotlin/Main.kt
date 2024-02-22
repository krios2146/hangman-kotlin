package dev.krios2146

var dictionary: List<String> = listOf()

fun main() {
    println()
    println("Welcome to the hangman game!")
    startHub()
}

fun startHub() {
    println()
    println("[P]lay | [R]ules | [E]xit") // maybe results & settings later

    val input = readUserInput()

    when (input.uppercase()) {
        "P" -> {
            println("Starting game...")
            startGame()
        }

        "R" -> {
            showRules()
            startHub()
        }

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
        mistakes < 6 -> println("You won, congrats! The word - $word")
    }

    startHub()
}

fun showRules() {
    println("Rules:")
    println("1. Guess the hidden word by picking letters.")
    println("2. If you guess a letter right, it shows up in the word.")
    println("3. If you guess wrong, the part of a hangman will be drawn.")
    println("4. Try to guess the word before the hangman is fully drawn.")
    println("5. If you guess it right, you win! If not, you lose.")
    println("6. You can play again if you want.")
}

fun readUserInput(): String {
    while (true) {
        val input = readln()

        when {
            input.isBlank() -> println("Please, enter some letter")
            input.length > 1 -> println("Please, enter exactly one letter")
            !isAlphabetic(input) -> println("Please, enter letter, no other symbols are allowed")
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

fun chooseWord(): String {
    if (dictionary.isEmpty()) {
        val classLoader = ClassLoader.getSystemClassLoader()
        dictionary = classLoader.getResourceAsStream("dictionary.txt")
            ?.bufferedReader()
            ?.readLines()
            .orEmpty()
    }
    return if (dictionary.isNotEmpty()) dictionary.random() else "development"
}

fun isAlphabetic(string: String): Boolean = string.lowercase() in "a".."z"