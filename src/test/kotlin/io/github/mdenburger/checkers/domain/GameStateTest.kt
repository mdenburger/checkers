package io.github.mdenburger.checkers.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import assertk.assertions.isTrue
import assertk.fail
import com.github.ajalt.mordant.terminal.Terminal
import io.github.mdenburger.checkers.console.drawBoard
import io.github.mdenburger.checkers.console.parseMove
import io.github.mdenburger.checkers.domain.Square.*
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
            17-21
            26x17
            19-23
            32-27
            12x21x32
            37x28x19
            """.trimIndent()

        val terminal = Terminal()

        moves.split("\n")
            .filter { line -> line.isNotBlank() }
            .forEach { line ->
                terminal.drawBoard(gameState.board)

                val move = parseMove(line) ?: fail("Invalid move: '$line'")
                terminal.println("${gameState.activePlayer}: $line")

                assertThat(gameState.isValidMove(move)).isTrue()
                gameState = gameState.applyMove(move)
            }

        terminal.drawBoard(gameState.board)
    }

    @Test
    fun `the initial state has no winner yet`() {
        println(GameState.initial().allValidMoves(Color.Black))


        assertThat(GameState.initial().getWinner()).isNull()
    }

    @Test
    fun `black is the winner`() {
        val board = listOf(
            W, x, W, x, x, x, x, x, x, x,
            x, x, x, x, x, x, x, x, x, x,
            x, x, x, x, x, x, x, x, x, x,
            x, x, x, x, x, x, x, x, x, x,
            x, x, x, x, x, x, x, x, x, x,
            x, x, x, x, x, x, x, x, x, x,
            x, x, x, x, x, x, x, x, x, x,
            x, x, x, x, x, x, x, x, x, x,
            x, B, x, x, x, x, x, x, x, x,
            x, x, B, x, x, x, x, x, x, x,
        )
        val gameState = GameState(GameBoard(board), Color.Black, false)

        assertThat(gameState.getWinner()).isEqualTo(Color.Black)
    }

    @Test
    fun `white is the winner`() {
        val board = listOf(
            x, x, x, x, x, x, x, x, x, x,
            W, x, x, x, x, x, x, x, x, x,
            x, x, x, x, x, x, x, x, x, x,
            x, x, x, x, x, x, x, x, x, x,
            x, x, x, x, x, x, x, x, x, x,
            x, x, x, x, x, x, x, x, x, x,
            x, x, x, x, x, x, x, x, x, x,
            x, x, x, x, x, x, x, x, x, x,
            x, B, x, x, x, x, x, x, x, x,
            B, B, B, x, x, x, x, x, x, x,
        )
        val gameState = GameState(GameBoard(board), Color.White, false)

        assertThat(gameState.getWinner()).isEqualTo(Color.White)
    }
}

