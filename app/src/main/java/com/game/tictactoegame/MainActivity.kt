package com.game.tictactoegame

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.game.tictactoegame.ui.theme.TicTacToeGameTheme
import androidx.compose.foundation.clickable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeGameTheme {
                TicTacToeApp()
            }
        }
    }
}
@Composable
fun TicTacToeApp() {
    var board by remember { mutableStateOf(Array(3) { Array(3) { "" } }) }
    var currentPlayer by remember { mutableStateOf("X") }
    var status by remember { mutableStateOf("Current Player: X") }
    var gameOver by remember { mutableStateOf(false) }
    var backgroundColor by remember { mutableStateOf(Color.White)
    }
    val colors = listOf(
        Color(0xC3EFE0C4),
        Color(0xFFF2F5F1),
        Color(0xFFF1CECE),
        Color(0xFFE2F5DB),
        Color(0xFFF1F7ED),
        Color(0xFFF7F4F2),
        Color(0xFFE8F5E9),
        Color(0xCDF8ECE3),
        Color(0xD3D5D5B7),
        Color(0xFFE9F7FC),
        Color(0xFFF7F6F1),
        Color(0xFFF5F8FA)
    )

    var currentColorIndex by remember { mutableStateOf(0) }

    // Function to check for a winner
    fun checkWinner(): Boolean {
        for (i in 0..2) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0].isNotEmpty()) return true
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i].isNotEmpty()) return true
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0].isNotEmpty()) return true
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2].isNotEmpty()) return true
        return false
    }

    // Function to check if the board is full
    fun isBoardFull(): Boolean = board.all { row -> row.all { it.isNotEmpty() } }

    // Restart the game after 5 seconds if game ends
    LaunchedEffect(gameOver) {
        if (gameOver) {
            delay(5000)
            board = Array(3) { Array(3) { "" } }
            currentPlayer = "X"
            status = "Current Player: X"
            gameOver = false
        }
    }
    LaunchedEffect(Unit) {
        while (true) {
            delay(8000) // 8 seconds delay
            currentColorIndex = (currentColorIndex + 1) % colors.size
            backgroundColor = colors[currentColorIndex]
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Title
            Text(
                text = "Tic Tac Toe",
                fontSize = 36.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp),
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )

            // Status
            Text(
                text = status,
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Game board
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (row in 0..2) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        for (col in 0..2) {
                            Button(
                                onClick = {
                                    if (board[row][col].isEmpty() && !gameOver) {
                                        board[row][col] = currentPlayer
                                        if (checkWinner()) {
                                            status = "Player $currentPlayer Wins!"
                                            gameOver = true
                                        } else if (isBoardFull()) {
                                            status = "It's a Draw!"
                                            gameOver = true
                                        } else {
                                            currentPlayer = if (currentPlayer == "X") "O" else "X"
                                            status = "Current Player: $currentPlayer"
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(4.dp)
                            ) {
                                Text(text = board[row][col], fontSize = 32.sp)
                            }
                        }
                    }
                }
            }
        }

        // Restart button at the bottom-left corner
        Button(
            onClick = {
                board = Array(3) { Array(3) { "" } }
                currentPlayer = "X"
                status = "Current Player: X"
                gameOver = false
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text("Restart", fontSize = 18.sp)
        }
    }
}



@Preview(showBackground = true)
@Composable
fun TicTacToeAppPreview() {
    TicTacToeGameTheme {
        TicTacToeApp()
    }
}