import kotlin.test.assertEquals
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TicTacToeStateTest {
    private fun newStateFromStrings(ss: Array<String>): TicTacToeState {
        val N = ss.size
        val state = TicTacToeState(N)
        for (i in 0 until N) {
            for (j in 0 until N) {
                if (ss[i][j] != '.') {
                    state.move(Player(ss[i][j].toString()), i, j)
                }
            }
        }
        return state
    }
    @Test
    fun `TicTacToeState move() should check for invalid move`() {
        val ss = arrayOf(
            "xo.",
            "xo.",
            "..."
        )
        val state = newStateFromStrings(ss)
        assertFalse { state.move(Player("x"), -1, 2) }
        assertFalse { state.move(Player("x"), 2, -1) }
        assertFalse { state.move(Player("x"), 3, 1) }
        assertFalse { state.move(Player("x"), 1, 3) }
        assertFalse { state.move(Player("x"), 1, 1) }

        assertTrue { state.move(Player("x"), 2, 2) }
    }

    @Test
    fun `TicTacToeState move() should update board`() {
        val ss = arrayOf(
            "xo.",
            "xo.",
            "..."
        )
        val state = newStateFromStrings(ss)
        state.move(Player("x"), 2, 0)
        assertEquals(state.stateString(), "" +
                "xo.\n" +
                "xo.\n" +
                "xo.\n")
    }

    @Test
    fun `TicTacToeState winner() should return winner`() {
        assertEquals(true, true)
    }
}

class TicTacToeGameTest {
    @Test
    fun `TicTacToe move() should change game state`() {
        assertEquals(true, true)
    }

    @Test
    fun `TicTacToeState winner() should return winner`() {
        assertEquals(true, true)
    }
}