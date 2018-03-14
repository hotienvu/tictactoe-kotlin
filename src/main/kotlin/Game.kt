interface GameState {
    fun stateString(): String
    fun result(): Result
}

class TicTacToeState(val N: Int): GameState {
    private var lastMove = Triple<Player?, Int, Int>(null,-1, -1 )
    private var moveCount = 0
    override fun result(): Result {
        if (lastMove.first == null) return Result(null, false)
        val x = lastMove.second
        val y = lastMove.third
        var rowc = 0
        var colc = 0
        var diac = 0
        var rdiac = 0
        var i = x
        while (i >=0 && boards[i--][y] == boards[x][y]) colc++
        i = x+1
        while (i <N && boards[i++][y] == boards[x][y]) colc++
        var j = y
        while (j >=0 && boards[x][j--] == boards[x][y]) rowc++
        j = y+1
        while (j <N && boards[x][j++] == boards[x][y]) rowc++
        i = x
        j = y
        while (i >=0 && j>=0&& boards[i--][j--] == boards[x][y]) diac++
        i = x+1
        j = y+1
        while (i <N && j<N&& boards[i++][j++] == boards[x][y]) diac++
        i = x
        j = y
        while (i >=0 && j<N&& boards[i--][j++] == boards[x][y]) rdiac++
        i = x+1
        j = y-1
        while (i <N && j>=0&& boards[i++][j--] == boards[x][y]) rdiac++
        return if (colc == 3 || rowc == 3 || diac == 3 || rdiac == 3) Result(lastMove.first, true) else {
            if (moveCount == N*N) Result(null, true) else Result(null, false)
        }
    }

    private val boards: Array<Array<String>> = Array(N, { _ -> Array(N, { _ ->"."}) })

    override fun stateString(): String {
        var s = ""
        val padding = N*N.toString().length+1
        for (i in 0 until boards.size) {
            for (j in 0 until boards[i].size) {
                if (boards[i][j] == ".") {
                    s += ((i*N+j+1).toString().padStart(padding,' '))
                } else {
                    s += boards[i][j].padStart(padding, ' ')
                }
            }
            s += "\n"
        }
        return s
    }

    fun move(player: Player, i: Int, j: Int): Boolean {
        if (i < 0 || j < 0 || i >= N || j >= N || !isEmpty(i, j))
            return false

        moveCount++
        lastMove = Triple(player, i, j)
        boards[i][j] = player.name
        return true
    }

    private fun isEmpty(i: Int, j: Int): Boolean {
        return this.boards[i][j] == "."
    }
}

abstract class Game() {
    abstract fun move(player: Player): GameState
    abstract fun winner(): Result
    abstract fun gameLoop()

    fun start() {
        gameLoop()
    }
}

data class Player(val name: String)
data class Result(val winner: Player?, val finished: Boolean)

abstract class Turnbased(var state: GameState): Game() {
    private val playerA = Player("x")
    private val playerB = Player("o")
    private var nextPlayer = playerA

    override fun gameLoop() {
        while (!winner().finished)  {
            println(state.stateString())
            state = move(nextPlayer) as TicTacToeState
            nextPlayer = if (nextPlayer === playerA) playerB else playerA
        }
        println(state.stateString())
        when (winner().winner) {
            null -> println("Draw!")
            else -> println("winner is: " + winner().winner!!.name)
        }
    }
}

class TicTacToe(state: TicTacToeState) : Turnbased(state) {
    companion object {
        fun create(N: Int): TicTacToe = TicTacToe(TicTacToeState(N))
    }

    private var _state = state

    override fun move(player: Player): GameState {
        var coord: Int
        var x: Int
        var y: Int
        while (true) {
            print("Input coordinate: ")
            coord = readLine()?.toInt() ?: -1
            // convert to 0-based
            coord -= 1
            x = coord / _state.N
            y = coord % _state.N
            if (!_state.move(player, x, y))
                println("Invalid coordinate")
            else break
        }

        return _state
    }

    override fun winner(): Result {
        return _state.result()
    }
}