package io.github.mdenburger.checkers.domain

import assertk.assertThat
import assertk.assertions.isTrue
import assertk.fail
import com.github.ajalt.mordant.terminal.Terminal
import io.github.mdenburger.checkers.console.drawBoard
import io.github.mdenburger.checkers.console.parseMove
import org.junit.jupiter.api.Test

class GameStateTest {

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
}