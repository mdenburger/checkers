package io.github.mdenburger.checkers.domain

data class GameState(
    val board: GameBoard,
    val activePlayer: Color,
    val done: Boolean
) {
    fun isValidMove(move: Move): Boolean =
        when (move) {
            is Move.Slide -> board.isValidSlide(move, activePlayer) &&
                    this.allValidJumps(activePlayer).none()

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
        if (allValidMoves(Color.White).none()) {
            Color.Black
        } else if (allValidMoves(Color.Black).none()) {
            Color.White
        } else {
            null
        }

    private fun allValidMoves(player: Color): Sequence<Move> =
        allValidSlides(player) + allValidJumps(player)

    private fun allValidSlides(player: Color): Sequence<Move.Slide> =
        (1..TOTAL_SQUARE_COUNT).asSequence()
            .map { it.square }
            .flatMap { allValidSlidesFrom(it, player) }

    private fun allValidSlidesFrom(from: SquareNumber, player: Color): Sequence<Move.Slide> =
        from.slideOptions(player)
            .map { to -> Move.Slide(from, to) }
            .filter { slide -> board.isValidSlide(slide, player) }

    private fun allValidJumps(player: Color): Sequence<Move.Jump> =
        (1..TOTAL_SQUARE_COUNT).asSequence()
            .map { it.square }
            .flatMap { allValidJumpsFrom(it, player) }

    private fun allValidJumpsFrom(from: SquareNumber, player: Color): Sequence<Move.Jump> =
        from.jumpOptions()
            .map { jumpOption -> Move.Jump(from, listOf(jumpOption.to)) }
            .filter { jump -> board.isValidJump(jump, player) }

    companion object {
        fun initial() = GameState(
            board = GameBoard.initial(),
            activePlayer = Color.White,
            done = false
        )
    }
}
