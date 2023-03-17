package io.github.mdenburger.checkers.domain

sealed interface Move {

    data class Slide(
        val from: SquareNumber,
        val to: SquareNumber
    ) : Move

    data class Jump(
        val from: SquareNumber,
        val to: List<SquareNumber>
    ) : Move

    object Quit: Move
}
