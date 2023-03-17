package io.github.mdenburger.checkers.domain

enum class Color {
    Black, White;

    fun opponent(): Color =
        when (this) {
            Black -> White
            White -> Black
        }
}
