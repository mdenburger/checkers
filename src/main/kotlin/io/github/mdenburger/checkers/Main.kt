package io.github.mdenburger.checkers

import io.github.mdenburger.checkers.console.ConsoleGameIO
import io.github.mdenburger.checkers.domain.GameEngine
import io.github.mdenburger.checkers.domain.GameState

fun main() {
    runConsoleCheckers()
}

fun runConsoleCheckers() {
    GameEngine(
        initialState = GameState.initial(),
        io = ConsoleGameIO()
    ).run()
}
