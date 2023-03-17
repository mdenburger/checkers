package io.github.mdenburger.checkers.domain

/**
 * Width and height of the game board in squares.
 * International checkers uses a 10x10 board.
 */
const val SIZE = 10

const val SQUARES_PER_ROW = SIZE / 2
const val TOTAL_SQUARE_COUNT = SIZE * SQUARES_PER_ROW

class GameBoard(private val squares: List<Square>) {

    fun rows(): List<List<Square>> =
        squares.chunked(SQUARES_PER_ROW)

    fun isValidSlide(slide: Move.Slide, activePlayer: Color): Boolean {
        return squares[slide.from.index()].color() == activePlayer &&
                squares[slide.to.index()] == Square.EMPTY &&
                slide.from.slideDestinations(activePlayer).contains(slide.to)
    }

    fun applySlide(slide: Move.Slide): GameBoard {
        val nextSquares = squares.toMutableList()
        nextSquares[slide.to.index()] = squares[slide.from.index()]
        nextSquares[slide.from.index()] = Square.EMPTY
        return GameBoard(nextSquares)
    }

    companion object {
        fun initial(): GameBoard {
            val piecesPerPlayer = (SQUARES_PER_ROW - 1) * SQUARES_PER_ROW
            val emptySquares = SIZE

            return GameBoard(
                List(piecesPerPlayer) { Square.BLACK_MAN } +
                        List(emptySquares) { Square.EMPTY } +
                        List(piecesPerPlayer) { Square.WHITE_MAN },
            )
        }
    }
}