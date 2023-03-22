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
    fun `can use square builder shorthand`() {
        assertThat(1.square).isEqualTo(SquareNumber(1))
    }

    @Test
    fun `zero slide destinations on kings row`() {
        assertThat(SquareNumber(1).slideOptions(Color.White)).isEmpty()
        assertThat(SquareNumber(SQUARES_PER_ROW).slideOptions(Color.White)).isEmpty()

        assertThat(SquareNumber(TOTAL_SQUARE_COUNT - SQUARES_PER_ROW + 1).slideOptions(Color.Black)).isEmpty()
        assertThat(SquareNumber(TOTAL_SQUARE_COUNT).slideOptions(Color.Black)).isEmpty()
    }

    @Test
    fun `one slide destination at edges`() {
        assertThat(6.square.slideOptions(Color.White).toList()).isEqualTo(listOf(1.square))
        assertThat(15.square.slideOptions(Color.White).toList()).isEqualTo(listOf(10.square))

        assertThat(6.square.slideOptions(Color.Black).toList()).isEqualTo(listOf(11.square))
        assertThat(15.square.slideOptions(Color.Black).toList()).isEqualTo(listOf(20.square))
    }

    @Test
    fun `two slide destinations in the middle`() {
        assertThat(7.square.slideOptions(Color.White).toList()).isEqualTo(listOf(1.square, 2.square))
        assertThat(11.square.slideOptions(Color.White).toList()).isEqualTo(listOf(6.square, 7.square))
        assertThat(50.square.slideOptions(Color.White).toList()).isEqualTo(listOf(44.square, 45.square))

        assertThat(1.square.slideOptions(Color.Black).toList()).isEqualTo(listOf(7.square, 6.square))
        assertThat(7.square.slideOptions(Color.Black).toList()).isEqualTo(listOf(12.square, 11.square))
    }

    @Test
    fun `one jump option`() {
        assertThat(50.square.jumpOptions().toList())
            .isEqualTo(listOf(JumpOption(to = 39.square, captured = 44.square)))
        assertThat(1.square.jumpOptions().toList())
            .isEqualTo(listOf(JumpOption(to = 12.square, captured = 7.square)))
        assertThat(5.square.jumpOptions().toList())
            .isEqualTo(listOf(JumpOption(to = 14.square, captured = 10.square)))
        assertThat(41.square.jumpOptions().toList())
            .isEqualTo(listOf(JumpOption(to = 32.square, captured = 37.square)))
    }

    @Test
    fun `two jump options`() {
        assertThat(2.square.jumpOptions().toList()).isEqualTo(
            listOf(
                JumpOption(to = 13.square, captured = 8.square),
                JumpOption(to = 11.square, captured = 7.square)
            )
        )
        assertThat(16.square.jumpOptions().toList()).isEqualTo(
            listOf(
                JumpOption(to = 7.square, 11.square),
                JumpOption(to = 27.square, 21.square)
            )
        )
        assertThat(40.square.jumpOptions().toList()).isEqualTo(
            listOf(
                JumpOption(to = 29.square, captured = 34.square),
                JumpOption(to = 49.square, captured = 44.square)
            )
        )
    }

    @Test
    fun `four jump options`() {
        assertThat(12.square.jumpOptions().toList()).isEqualTo(
            listOf(
                JumpOption(to = 1.square, captured = 7.square),
                JumpOption(to = 3.square, captured = 8.square),
                JumpOption(to = 23.square, captured = 18.square),
                JumpOption(to = 21.square, captured = 17.square)
            )
        )
    }
}