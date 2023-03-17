package io.github.mdenburger.checkers.domain

import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import assertk.assertions.isSuccess
import org.junit.jupiter.api.Test

class SquareNumberTest {

    @Test
    fun `can only create valid square numbers`() {
        assertThat { SquareNumber(1) }.isSuccess()
        assertThat { SquareNumber(TOTAL_SQUARE_COUNT) }.isSuccess()
        assertThat { SquareNumber(0) }.isFailure()
        assertThat { SquareNumber(TOTAL_SQUARE_COUNT + 1) }.isFailure()
    }

    @Test
    fun `index is zero-based`() {
        assertThat(SquareNumber(1).index()).isEqualTo(0)
        assertThat(SquareNumber(TOTAL_SQUARE_COUNT).index()).isEqualTo(TOTAL_SQUARE_COUNT - 1)
    }

    @Test
    fun `zero slide destinations on kings row`() {
        assertThat(SquareNumber(1).slideDestinations(Color.White)).isEmpty()
        assertThat(SquareNumber(SQUARES_PER_ROW).slideDestinations(Color.White)).isEmpty()

        assertThat(SquareNumber(TOTAL_SQUARE_COUNT - SQUARES_PER_ROW + 1).slideDestinations(Color.Black)).isEmpty()
        assertThat(SquareNumber(TOTAL_SQUARE_COUNT).slideDestinations(Color.Black)).isEmpty()
    }

    @Test
    fun `one slide destination at edges`() {
        assertThat(SquareNumber(6).slideDestinations(Color.White)).isEqualTo(listOf(SquareNumber(1)))
        assertThat(SquareNumber(15).slideDestinations(Color.White)).isEqualTo(listOf(SquareNumber(10)))

        assertThat(SquareNumber(6).slideDestinations(Color.Black)).isEqualTo(listOf(SquareNumber(11)))
        assertThat(SquareNumber(15).slideDestinations(Color.Black)).isEqualTo(listOf(SquareNumber(20)))
    }

    @Test
    fun `two slide destinations in the middle`() {
        assertThat(SquareNumber(7).slideDestinations(Color.White)).isEqualTo(listOf(SquareNumber(1), SquareNumber(2)))
        assertThat(SquareNumber(11).slideDestinations(Color.White)).isEqualTo(listOf(SquareNumber(6), SquareNumber(7)))
        assertThat(SquareNumber(50).slideDestinations(Color.White)).isEqualTo(listOf(SquareNumber(44), SquareNumber(45)))

        assertThat(SquareNumber(1).slideDestinations(Color.Black)).isEqualTo(listOf(SquareNumber(7), SquareNumber(6)))
        assertThat(SquareNumber(7).slideDestinations(Color.Black)).isEqualTo(listOf(SquareNumber(12), SquareNumber(11)))
    }
}