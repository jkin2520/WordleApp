package com.example.wordlegame.com.example.wordleapp
class GameUtils {


    fun checkGuess(wordToGuess: String, guess: String): String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O" // Correct letter and position
            } else if (guess[i] in wordToGuess) {
                result += "+" // Correct letter, wrong position
            } else {
                result += "X" // Letter not in the target word
            }
        }
        return result
    }

}