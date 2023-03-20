package io.github.mdenburger.checkers.domain

sealed interface Move {

    data class Slide(
        val from: SquareNumber,
        val to: SquareNumber
    ) : Move

    data class Jump(
        val from: SquareNumber,
        val to: List<SquareNumber>
    ) : Move {
        fun firstStep(): Jump =
            Jump(from, listOf(to.first()))

        fun remainingSteps(): Jump =
            Jump(to.first(), to.drop(1))
    }

    object Quit: Move
}
