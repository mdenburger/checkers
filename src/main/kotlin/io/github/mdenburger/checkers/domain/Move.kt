package io.github.mdenburger.checkers.domain

sealed interface Move {

    data class Slide(
        val from: SquareNumber,
        val to: SquareNumber
    ) : Move {
        override fun toString() = "$from-$to"
    }

    data class Jump(
        val from: SquareNumber,
        val to: List<SquareNumber>
    ) : Move {
        fun firstStep(): Jump =
            Jump(from, listOf(to.first()))

        fun remainingSteps(): Jump =
            Jump(to.first(), to.drop(1))

        override fun toString() = "${from}x${to.joinToString("x")}"
    }

    object Quit: Move
}
