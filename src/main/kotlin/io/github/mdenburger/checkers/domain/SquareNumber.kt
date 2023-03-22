package io.github.mdenburger.checkers.domain

/**
 * One-based number for a playable square on a checkerboard.
 * Squares are numbered left to right, top to bottom, starting at one.
 * Example square numbers on a 10x10 board:
 * ```
 *     01  02  03  04  05
 *   06  07  08  09  10
 *     11  12  13  14  15
 *   16  17  18  19  20
 *     21  22  23  24  25
 *   26  27  28  29  30
 *     31  32  33  34  35
 *   36  37  38  39  40
 *     41  42  43  44  45
 *   46  47  48  49  50
 * ```
 */
@JvmInline
value class SquareNumber(private val value: Int) {
    init {
        require(value in 1..TOTAL_SQUARE_COUNT) {
            "Square number must be between 1 and $TOTAL_SQUARE_COUNT but was $value"
        }
    }

    /**
     * Zero-based index to look up a square number in a collection.
     */
    fun index(): Int =
        value - 1

    fun slideOptions(player: Color): Sequence<SquareNumber> =
        when (player) {
            Color.White -> slideUpOptions()
            Color.Black -> slideDownOptions()
        }

    private fun slideUpOptions(): Sequence<SquareNumber> =
        sequence {
            if (value > SQUARES_PER_ROW) {
                // square below kings row so we can slide up
                if (((value - SQUARES_PER_ROW - 1) % SIZE) != 0) {
                    // not the first square in a row that starts with a black square
                    yield(upLeft().square)
                }
                if (((value - SQUARES_PER_ROW) % SIZE) != 0) {
                    // last square in a row that ends with a white square
                    yield(upRight().square)
                }
            }
        }

    private fun slideDownOptions(): Sequence<SquareNumber> =
        rotateBoard().slideUpOptions().map { number -> number.rotateBoard() }

    fun rotateBoard(): SquareNumber =
        SquareNumber(TOTAL_SQUARE_COUNT - value + 1)

    private fun upLeft(): Int =
        if (rowStartsWithWhiteSquare()) {
            value - SQUARES_PER_ROW
        } else {
            value - SQUARES_PER_ROW - 1
        }

    private fun upRight(): Int = upLeft() + 1

    private fun rowStartsWithWhiteSquare(): Boolean =
        (value % SIZE) in 1..SQUARES_PER_ROW

    fun jumpOptions(): Sequence<JumpOption> =
        jumpUpOptions() + jumpDownOptions()

    private fun jumpUpOptions(): Sequence<JumpOption> =
        sequence {
            if (value > SQUARES_PER_ROW * 2) {
                // square below first two rows so we can jump up
                if (((value - 1) % SQUARES_PER_ROW) != 0) {
                    // not a square in the first two columns
                    val jumpUpLeft = JumpOption(to = upLeftTwice().square, captured = upLeft().square)
                    yield(jumpUpLeft)
                }
                if ((value % SQUARES_PER_ROW) != 0) {
                    // not a square in the last two rows
                    val jumpUpRight = JumpOption(to = upRightTwice().square, captured = upRight().square)
                    yield(jumpUpRight)
                }
            }
        }

    private fun jumpDownOptions(): Sequence<JumpOption> =
        rotateBoard().jumpUpOptions().map { option -> option.rotateBoard() }

    private fun upLeftTwice(): Int =
        this.upLeft().square.upLeft()

    private fun upRightTwice(): Int =
        this.upRight().square.upRight()

    override fun toString() = value.toString()
}

val Int.square: SquareNumber
    get() = SquareNumber(this)
