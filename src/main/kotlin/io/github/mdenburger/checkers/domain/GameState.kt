package io.github.mdenburger.checkers.domain

data class GameState(
    val board: GameBoard,
    val activePlayer: Color,
    val done: Boolean
) {
    fun isValidMove(move: Move): Boolean =
        when (move) {
            is Move.Slide -> board.isValidSlide(move, activePlayer)
            is Move.Jump -> board.isValidJump(move, activePlayer)
            Move.Quit -> true
        }

    fun applyMove(move: Move): GameState =
        when (move) {
            is Move.Slide -> this.copy(
                board = board.applySlide(move),
                activePlayer = activePlayer.opponent()
            )
            is Move.Jump -> this.copy(
                board = board.applyJump(move),
                activePlayer = activePlayer.opponent()
            )
            Move.Quit -> this.copy(done = true)
        }

    fun getWinner(): Color? =
        if (allValidMoves(Color.White).isEmpty()) {
            Color.Black
        } else if (allValidMoves(Color.Black).isEmpty()) {
            Color.White
        } else {
            null
        }

    fun allValidMoves(player: Color): List<Move> {
        val validMoves = mutableListOf<Move>()

        for (number in 1..TOTAL_SQUARE_COUNT) {
            val squareNumber = number.square

            validMoves += squareNumber.slideOptions(player)
                .map { Move.Slide(squareNumber, it) }
                .filter { board.isValidSlide(it, player) }

            validMoves += squareNumber.jumpOptions()
                .map { Move.Jump(squareNumber, listOf(it.to)) }
                .filter { board.isValidJump(it, player) }
        }

        return validMoves
    }

    companion object {
        fun initial() = GameState(
            board = GameBoard.initial(),
            activePlayer = Color.White,
            done = false
        )
    }
}
