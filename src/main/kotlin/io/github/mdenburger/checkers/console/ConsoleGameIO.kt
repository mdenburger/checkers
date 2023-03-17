package io.github.mdenburger.checkers.console

import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.terminal.Terminal
import io.github.mdenburger.checkers.domain.*

class ConsoleGameIO : GameIO {

    private val terminal = Terminal()

    override fun drawBoard(board: GameBoard) {
        terminal.drawBoard(board)
    }

    override fun getMove(player: Color): Move {
        return terminal.readMove(player)
    }

    override fun reportIllegalMove(move: Move) {
        terminal.println(TextColors.brightRed("Illegal move: $move"))
    }

    override fun showWinner(winner: Color?) {
        if (winner == null) {
            println("It's a draw!")
        } else {
            println("$winner won!")
        }
    }
}
