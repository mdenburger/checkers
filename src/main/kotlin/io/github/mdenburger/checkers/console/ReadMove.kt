package io.github.mdenburger.checkers.console

import com.github.ajalt.mordant.rendering.TextColors.brightRed
import com.github.ajalt.mordant.terminal.Terminal

import io.github.mdenburger.checkers.domain.Color
import io.github.mdenburger.checkers.domain.Move
import io.github.mdenburger.checkers.domain.SquareNumber

fun Terminal.readMove(player: Color): Move {
    while (true) {
        kotlin.io.print("$player move: ")
        val move = readMove()
        if (move == null) {
            println(brightRed("Illegal move"))
        } else {
            return move
        }
    }
}

private fun readMove(): Move? =
    try {
        val input = readln()
        parseMove(input)
    } catch (e: RuntimeException) {
        Move.Quit
    }

private fun parseMove(s: String): Move? =
    if (s.contains("-")) {
        val numbers = s.split("-")
        val from = numbers[0].toSquareNumber()
        val to = numbers[1].toSquareNumber()
        Move.Slide(from, to)
    } else {
        null
    }

fun String.toSquareNumber(): SquareNumber =
    SquareNumber(this.toInt())
