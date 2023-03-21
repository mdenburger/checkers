package io.github.mdenburger.checkers.console

import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.terminal.Terminal
import io.github.mdenburger.checkers.domain.*

class ConsoleGameIO : GameIO {

    private val terminal = Terminal()
    private var instructionsPrinted = false

    override fun drawBoard(board: GameBoard) {
        if (!instructionsPrinted) {
            printInstructions()
            instructionsPrinted = true
        }

        println()
        terminal.drawBoard(board)
    }

    private fun printInstructions() {
        terminal.println("Play checkers with two people!")
        terminal.println()
        terminal.println("The black squares are numbered 1 to $TOTAL_SQUARE_COUNT.")
        terminal.println("The first square number of each row is shown left of the board.")
        terminal.println()
        terminal.println("Use these square numbers to specify moves. For example:")
        terminal.println("  31-26  slides from square 31 to square 26")
        terminal.println("  26x17  jumps from square 26 to square 17 and captures the piece at square 21")
        terminal.println()
        terminal.println("Jumps can consist of multiple steps, e.g. 26x17x28")
        terminal.println("Use 'q' (or Ctrl-C or Ctrl-D) to quit")
    }

    override fun getMove(player: Color): Move {
        return terminal.readMove(player)
    }

    override fun reportIllegalMove(move: Move) {
        terminal.println(TextColors.brightRed("Illegal move: $move"))
    }

    override fun showWinner(winner: Color?) {
        println()
        if (winner == null) {
            println("It's a draw!")
        } else {
            println("$winner won!")
        }
    }
}
