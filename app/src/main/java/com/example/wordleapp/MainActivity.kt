package com.example.wordleapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.wordleapp.R
// import com.example.wordlegame.checkGuess



class MainActivity : AppCompatActivity() {

    private lateinit var wordToGuess: String
    private var guessCount = 0
    private val maxGuesses = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputGuess = findViewById<EditText>(R.id.inputGuess)
        val submitButton = findViewById<Button>(R.id.submitButton)
        val resetButton = findViewById<Button>(R.id.resetButton)
        val guessResults = findViewById<TextView>(R.id.guessResults)

        // Generate random word
        wordToGuess = FourLetterWordList.getRandomFourLetterWord()
        Toast.makeText(this, "Target word generated!", Toast.LENGTH_SHORT).show()

        resetButton.isEnabled = false // Disable reset button initially

        submitButton.setOnClickListener {
            val userGuess = inputGuess.text.toString().uppercase()

            // Validate guess
            if (userGuess.length != 4) {
                Toast.makeText(this, "Please enter a 4-letter word!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            guessCount++
            val result = checkGuess(wordToGuess, userGuess)
            guessResults.append("Guess #$guessCount: $userGuess\n")
            guessResults.append("Result: $result\n")

            // Check win or lose condition
            if (userGuess == wordToGuess) {
                Toast.makeText(this, "Congratulations! You guessed the word!", Toast.LENGTH_LONG).show()
                disableSubmit(submitButton, resetButton)
            } else if (guessCount == maxGuesses) {
                Toast.makeText(this, "No more guesses! The word was $wordToGuess", Toast.LENGTH_LONG).show()
                disableSubmit(submitButton, resetButton)
            }

            inputGuess.text.clear()
        }

        resetButton.setOnClickListener {
            resetGame(inputGuess, guessResults, submitButton, resetButton)
        }
    }

    private fun disableSubmit(submitButton: Button, resetButton: Button) {
        submitButton.isEnabled = false
        resetButton.isEnabled = true
    }

    private fun resetGame(inputGuess: EditText, guessResults: TextView, submitButton: Button, resetButton: Button) {
        wordToGuess = FourLetterWordList.getRandomFourLetterWord()
        guessCount = 0
        inputGuess.text.clear()
        guessResults.text = ""
        submitButton.isEnabled = true
        resetButton.isEnabled = false
        Toast.makeText(this, "Game reset! New word generated.", Toast.LENGTH_SHORT).show()
    }
}
private fun checkGuess(wordToGuess: String, guess: String): String {
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
