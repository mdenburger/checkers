package io.github.mdenburger.checkers.domain

class GameEngine(
    initialState: GameState,
    private val io: GameIO
) {

    private var state: GameState = initialState

    fun run() {
        while (!state.done) {
            io.drawBoard(state.board)

            val move = io.getMove(state.activePlayer)

            if (state.isValidMove(move)) {
                state = state.applyMove(move)
            } else {
                io.reportIllegalMove(move)
            }
        }

        val winnerOrNull = state.getWinner()
        io.showWinner(winnerOrNull)
    }
}
