package io.github.mdenburger.checkers.domain

/**
 * Possible states of squares on a checkerboard.
 */
enum class Square {
    EMPTY,
    BLACK_MAN,
    WHITE_MAN;

    fun color(): Color? =
        when (this) {
            EMPTY -> null
            BLACK_MAN -> Color.Black
            WHITE_MAN -> Color.White
        }
}
