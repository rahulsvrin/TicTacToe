package com.example.myapp2

import android.os.Bundle
import android.widget.GridLayout
import android.widget.Button
import androidx.activity.ComponentActivity
import android.view.View
import android.widget.Toast

class MainActivity : ComponentActivity() {
    private lateinit var gridLayout: GridLayout
    private var board = Array(3) { Array(3) { '_' } }
    private var currentPlayer = 'X'

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridLayout = findViewById(R.id.grid_layout)

        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            button.setOnClickListener {
                onButtonClick(it)
            }
        }

        val resetButton: Button = findViewById(R.id.reset_button)
        resetButton.setOnClickListener {
            resetGame()
        }
    }

    private fun onButtonClick(view: View) {
        val button = view as Button
        val row =  getRowIndex(button)
        val col = getColumnIndex(button)

        if (board[row][col] == '_') {
            button.text = currentPlayer.toString()
            board[row][col] = currentPlayer

            if (checkForWin()) {
                Toast.makeText(this, "Player $currentPlayer has won!", Toast.LENGTH_SHORT).show()
                disableButtons()
            } else if (checkForDraw()) {
                Toast.makeText(this, "It's a draw!", Toast.LENGTH_SHORT).show()
                disableButtons()
            } else {
                currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
            }
        }
    }

    private fun checkForWin(): Boolean {
        // Check rows for win
        for (i in 0..2) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != '_') {
                return true
            }
        }

        // Check columns for win
        for (i in 0..2) {
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != '_') {
                return true
            }
        }

        // Check diagonals for win
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '_') {
            return true
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != '_') {
            return true
        }

        return false
    }

    private fun checkForDraw(): Boolean {
        for (row in board) {
            for (cell in row) {
                if (cell == '_') {
                    return false
                }
            }
        }
        return true
    }

    private fun resetGame() {
        for (i in 0 until gridLayout.childCount) {

            val button = gridLayout.getChildAt(i) as Button
            if(button.text == "Reset")
                button.text = "Reset"
            else
                button.text = ""
            button.isEnabled = true
        }
        board = Array(3) { Array(3) { '_' } }
        currentPlayer = 'X'
    }

    private fun disableButtons() {
        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            if(button.text == "Reset")
                button.isEnabled = true
            else
                button.isEnabled = false
        }
    }

    private fun getColumnIndex(button: Button): Int {
        val parentLayout = button.parent as GridLayout

        for (i in 0 until parentLayout.childCount) {
            if (parentLayout.getChildAt(i) == button) {

                return when (i.toString()) {
                    "0", "3", "6" -> 0
                    "1", "4", "7" -> 1
                    "2", "5", "8" -> 2
                    else -> -1 // Return -1 if the button text is invalid
                }
            }
        }
        return -1 // Return -1 if the button is not found in the GridLayout
    }

    private fun getRowIndex(button: Button): Int {
        val parentLayout = button.parent as GridLayout
        for (i in 0 until parentLayout.childCount) {
            if (parentLayout.getChildAt(i) == button) {
                return i / parentLayout.columnCount
            }
        }
        return -1 // Return -1 if the button is not found in the GridLayout
    }





}