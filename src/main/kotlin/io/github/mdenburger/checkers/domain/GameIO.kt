package io.github.mdenburger.checkers.domain

interface GameIO {

    fun drawBoard(board: GameBoard)

    fun getMove(player: Color): Move

    fun reportIllegalMove(move: Move)

    fun showWinner(winner: Color?)

}
