package io.github.mdenburger.checkers.domain

data class JumpOption(
    val to: SquareNumber,
    val captured: SquareNumber
) {
    fun rotateBoard() = JumpOption(
        to = to.rotateBoard(),
        captured = captured.rotateBoard()
    )
}
