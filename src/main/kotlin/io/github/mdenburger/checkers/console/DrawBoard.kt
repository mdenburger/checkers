package io.github.mdenburger.checkers.console

import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.terminal.Terminal
import io.github.mdenburger.checkers.domain.GameBoard
import io.github.mdenburger.checkers.domain.SQUARES_PER_ROW
import io.github.mdenburger.checkers.domain.Square

fun Terminal.drawBoard(board: GameBoard) {
    for ((rowIndex, row) in board.rows().withIndex()) {
        printFirstSquareNumber(rowIndex)
        for (square in row) {
            if (rowIndex % 2 == 0) {
                printWhiteSquare()
                printBlackSquare(square)
            } else {
                printBlackSquare(square)
                printWhiteSquare()
            }
        }
        println()
    }
}

fun printFirstSquareNumber(rowIndex: Int) {
    val number = (rowIndex * SQUARES_PER_ROW) + 1
    val paddedNumber = number.toString().padStart(2, ' ')
    print("$paddedNumber ")
}

fun Terminal.printWhiteSquare() {
    print((black on white)(Square.EMPTY.asString()))
}

fun Terminal.printBlackSquare(square: Square) {
    print((square.foregroundColor() on black)(square.asString()))
}

fun Square.foregroundColor(): TextColors =
    when (this) {
        Square.EMPTY -> black
        Square.BLACK_MAN -> white
        Square.WHITE_MAN -> brightWhite
    }

fun Square.asString(): String =
    when (this) {
        Square.EMPTY -> "   "
        Square.BLACK_MAN -> " B "
        Square.WHITE_MAN -> " W "
    }
