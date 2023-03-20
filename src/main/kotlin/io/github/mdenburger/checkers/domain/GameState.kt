package io.github.mdenburger.checkers.domain

data class GameState(
    val board: GameBoard,
    val activePlayer: Color,
    val done: Boolean
) {
    fun getWinner(): Color? {
        // TODO determine winner
        return null
    }

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

    companion object {
        fun initial() = GameState(
            board = GameBoard.initial(),
            activePlayer = Color.White,
            done = false
        )
    }
}
