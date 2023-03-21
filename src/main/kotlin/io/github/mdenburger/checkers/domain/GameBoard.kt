package io.github.mdenburger.checkers.domain

/**
 * Width and height of the game board in squares.
 * International checkers uses a 10x10 board.
 * Set to 8 to play American checkers.
 */
const val SIZE = 10

const val SQUARES_PER_ROW = SIZE / 2
const val TOTAL_SQUARE_COUNT = SIZE * SQUARES_PER_ROW

class GameBoard(private val squares: List<Square>) {

    init {
        require(squares.size == TOTAL_SQUARE_COUNT)
    }

    fun rows(): List<List<Square>> =
        squares.chunked(SQUARES_PER_ROW)

    fun isValidSlide(slide: Move.Slide, activePlayer: Color): Boolean {
        return squares[slide.from.index()].color() == activePlayer &&
                squares[slide.to.index()] == Square.EMPTY &&
                slide.from.slideOptions(activePlayer).contains(slide.to)
    }

    fun applySlide(slide: Move.Slide): GameBoard {
        val nextSquares = squares.toMutableList()
        nextSquares[slide.to.index()] = squares[slide.from.index()]
        nextSquares[slide.from.index()] = Square.EMPTY
        return GameBoard(nextSquares)
    }

    fun isValidJump(jump: Move.Jump, activePlayer: Color): Boolean =
        squares[jump.from.index()].color() == activePlayer &&
        this.isValidJumpPath(jump, activePlayer)

    private fun isValidJumpPath(jump: Move.Jump, activePlayer: Color): Boolean {
        if (jump.to.isEmpty()) {
            return true
        }

        val jumpOptions = jump.from.jumpOptions()

        return jumpOptions.any { option ->
            option.to == jump.to.first() &&
            squares[option.to.index()] == Square.EMPTY &&
            squares[option.captured.index()].color() == activePlayer.opponent()
        } && this.applyJump(jump.firstStep()).isValidJumpPath(jump.remainingSteps(), activePlayer)
    }

    fun applyJump(jump: Move.Jump): GameBoard =
        if (jump.to.isEmpty()) {
            this
        } else {
            this.applyFirstStep(jump).applyJump(jump.remainingSteps())
        }

    private fun applyFirstStep(jump: Move.Jump): GameBoard {
        val nextSquares = squares.toMutableList()
        nextSquares[jump.to.first().index()] = squares[jump.from.index()]
        nextSquares[jump.from.index()] = Square.EMPTY

        val jumpOption = jump.from.jumpOptions().find { option -> option.to == jump.to.first() }
            ?: error("Cannot find jump options from $jump")
        nextSquares[jumpOption.captured.index()] = Square.EMPTY

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