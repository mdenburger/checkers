package io.github.mdenburger.checkers.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import assertk.fail
import com.github.ajalt.mordant.terminal.Terminal
import io.github.mdenburger.checkers.console.drawBoard
import io.github.mdenburger.checkers.console.parseMove
import io.github.mdenburger.checkers.domain.Square.*
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class GameStateTest {

    private val B = BLACK_MAN
    private val W = WHITE_MAN
    private val x = EMPTY

    @Test
    fun `play checkers`() {
        var gameState = GameState.initial()
        val moves = """
            31-26
            20-24
            32-27
            19-23
            34-30
            17-22
            30x19x28x17
            11x22x31
            36x27
            12-17
            26-21
            """.trimIndent()

        val terminal = Terminal()

        moves.split("\n")
            .filter { line -> line.isNotBlank() }
            .forEach { line ->
                terminal.drawBoard(gameState.board)

                val move = parseMove(line) ?: fail("Invalid move: '$line'")
                terminal.println("${gameState.activePlayer}: $line")

                assertTrue(gameState.isValidMove(move), "Should be valid but isn't: $move")
                gameState = gameState.applyMove(move)
            }

        terminal.drawBoard(gameState.board)
    }

    @Test
    fun `the initial state has no winner yet`() {
        assertThat(GameState.initial().getWinner()).isNull()
    }

    @Test
    fun `black is the winner`() {
        val squares = listOf(
            W, x, W, x, x,
            x, x, x, x, x,
            x, x, x, x, x,
            x, x, x, x, x,
            x, x, x, x, x,
            x, x, x, x, x,
            x, x, x, x, x,
            x, x, x, x, x,
            x, B, x, x, x,
            x, x, B, x, x,
        )
        val gameState = GameState(GameBoard(squares), Color.Black, false)

        assertThat(gameState.getWinner()).isEqualTo(Color.Black)
    }

    @Test
    fun `white is the winner`() {
        val squares = listOf(
            x, x, x, x, x,
            W, x, x, x, x,
            x, x, x, x, x,
            x, x, x, x, x,
            x, x, x, x, x,
            x, x, x, x, x,
            x, x, x, x, x,
            x, x, x, x, x,
            x, B, x, x, x,
            B, B, B, x, x,
        )
        val gameState = GameState(GameBoard(squares), Color.White, false)

        assertThat(gameState.getWinner()).isEqualTo(Color.White)
    }

    @Test
    fun `only forward slides are allowed`() {
        val squares = listOf(
              B, B, B, B, B,
            B, B, B, B, B,
              B, B, B, B, B,
            B, B, x, B, B,
              x, B, x, x, x,
            W, x, x, x, x,
              x, W, W, W, W,
            W, W, W, W, W,
              W, W, W, W, W,
            W, W, W, W, W,
        )

        val whitesTurn = GameState(GameBoard(squares), Color.White, false)
        val blacksTurn = GameState(GameBoard(squares), Color.Black, false)

        assertTrue(whitesTurn.isValidMove(Move.Slide(36.square, 31.square)))
        assertTrue(blacksTurn.isValidMove(Move.Slide(16.square, 21.square)))

        assertFalse(whitesTurn.isValidMove(Move.Slide(26.square, 32.square)))
        assertFalse(blacksTurn.isValidMove(Move.Slide(22.square, 18.square)))
    }

    @Test
    fun `capturing is mandatory`() {
        val squares = listOf(
            B, B, B, B, B,
            B, B, B, B, B,
            B, B, B, B, B,
            B, x, B, B, B,
            B, x, x, x, x,
            W, x, x, x, x,
            x, W, W, W, W,
            W, W, W, W, W,
            W, W, W, W, W,
            W, W, W, W, W,
        )
        val gameState = GameState(GameBoard(squares), Color.White, false)
        val invalidSlide = Move.Slide(36.square, 31.square)

        assertFalse(
            gameState.isValidMove(invalidSlide),
            "the move $invalidSlide is invalid because white must do 26x17 and capture 21"
        )
    }
}

