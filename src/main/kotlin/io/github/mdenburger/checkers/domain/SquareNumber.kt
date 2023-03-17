package io.github.mdenburger.checkers.domain

/**
 * One-based number for a playable square on a checkers board.
 * Squares are numbered left to right, top to bottom, starting at one.
 */
@JvmInline
value class SquareNumber(private val value: Int) {
    init {
        require(value in 1..TOTAL_SQUARE_COUNT) {
            "Square number must be between 1 and $TOTAL_SQUARE_COUNT but was $value"
        }
    }

    /**
     * Zero-based index to look up a square number in a list or array
     */
    fun index(): Int =
        value - 1

    fun slideDestinations(player: Color): List<SquareNumber> =
        when (player) {
            Color.White -> slideUpDestinations()
            Color.Black -> slideDownDestinations()
        }

    private fun slideUpDestinations(): List<SquareNumber> {
        val destinations = mutableListOf<SquareNumber>()

        if (value > SQUARES_PER_ROW) {
            // square below kings row so we can move up
            if (((value - SQUARES_PER_ROW - 1) % SIZE) != 0) {
                // not the first square in a row that starts with a black square;
                // we can move up-left
                destinations.add(SquareNumber(upLeft()))
            }
            if (((value - SQUARES_PER_ROW) % SIZE) != 0) {
                // last square in a row that ends with a white square;
                // we can move up-right
                destinations.add(SquareNumber(upRight()))
            }
        }

        return destinations
    }

    private fun upLeft(): Int =
        if (rowStartsWithWhiteSquare()) {
            value - SQUARES_PER_ROW
        } else {
            value - SQUARES_PER_ROW - 1
        }

    private fun rowStartsWithWhiteSquare(): Boolean =
        (value % SIZE) in 1..SQUARES_PER_ROW

    private fun upRight(): Int =
        upLeft() + 1

    private fun slideDownDestinations(): List<SquareNumber> =
        rotateBoard().slideUpDestinations().map { number -> number.rotateBoard() }

    private fun rotateBoard(): SquareNumber =
        SquareNumber(TOTAL_SQUARE_COUNT - value + 1)

}
