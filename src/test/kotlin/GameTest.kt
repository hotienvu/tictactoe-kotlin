import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
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

    fun TicTacToeState.getStateForTest(): Array<String> {
        return this.stateString().trim().split("\n").map { row: String ->
            row.split(" ").filter{ s -> s.isNotEmpty() }.map { x -> if (x == "x" || x == "o") x else "." }.joinToString("")
        }.toTypedArray()
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
        val expected = arrayOf(
            "xo.",
            "xo.",
            "x.."
        )
        val state = newStateFromStrings(ss)
        state.move(Player("x"), 2, 0)
        assertTrue(expected contentDeepEquals  state.getStateForTest())
    }

    @Test
    fun `TicTacToeState winner() should return winner in diagonal direction`() {
        val ss = arrayOf(
            "x.o",
            "...",
            "o.x"
        )
        var state = newStateFromStrings(ss)
        assertFalse(state.result().finished, "no winner yet")

        state.move(Player("x"), 1, 1)
        assertEquals(state.result().winner?.name?:"", "x", "x wins by diagonal")

        state = newStateFromStrings(ss)
        state.move(Player("o"), 1, 1)
        assertEquals(state.result().winner?.name?:"", "o", "o wins by reverse diagonal")
    }

    @Test
    fun `TicTacToeState winner() should return winner in vertical and horizontal direction`() {
        val ss = arrayOf(
                ".x.",
                "o.o",
                ".x."
        )
        var state = newStateFromStrings(ss)
        assertFalse(state.result().finished, "no winner yet")

        state.move(Player("x"), 1, 1)
        assertEquals(state.result().winner?.name?:"", "x", "x wins by vertical")

        state = newStateFromStrings(ss)
        state.move(Player("o"), 1, 1)
        assertEquals(state.result().winner?.name?:"", "o", "o wins by horizontal")
    }
}
