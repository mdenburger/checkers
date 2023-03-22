package io.github.mdenburger.checkers.domain

import assertk.Assert
import assertk.assertions.support.expected
import assertk.assertions.support.show

/**
 * Asserts that a sequence does not contain any elements
 */
fun <T> Assert<Sequence<T>>.isEmpty() = given { actual ->
    if (actual.none()) {
        return
    }
    expected("empty sequence but first item was ${show(actual.first())}")
}