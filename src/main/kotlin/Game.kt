interface GameState {
    fun printState()
    fun winner(): Player?
}

class TicTacToeState(val N: Int): GameState {
    private var lastMove = Triple<Player?, Int, Int>(null,-1, -1 )
    override fun winner(): Player? {
        if (lastMove.first == null) return null

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
        return if (colc == 3 || rowc == 3 || diac == 3 || rdiac == 3) lastMove.first else null
    }

    private val boards: Array<Array<String>> = Array(N, { _ -> Array(N, { _ ->"."}) })

    override fun printState() {
        val padding = N*N.toString().length+1
        for (i in 0 until boards.size) {
            for (j in 0 until boards[i].size) {
                if (boards[i][j] == ".") {
                    print((i*N+j+1).toString().padStart(padding,' '))
                } else {
                    print(boards[i][j].padStart(padding, ' '))
                }
            }
            println()
        }
    }

    fun move(player: Player, i: Int, j: Int) {
        lastMove = Triple(player, i, j)
        boards[i][j] = player.name
    }

    fun isEmpty(i: Int, j: Int): Boolean {
        return this.boards[i][j] == "."
    }
}

abstract class Game() {
    abstract fun state(): GameState
    abstract fun move(player: Player): GameState
    abstract fun winner(): Player?
    abstract fun gameLoop()

    fun start() {
        gameLoop()
    }
}

class Player(val name: String) {

}

class TicTacToe(N: Int) : Game() {
    private val playerA = Player("x")
    private val playerB = Player("o")
    private var nextPlayer = playerA

    private var state = TicTacToeState(N)

    override fun state(): GameState {
        return state
    }

    override fun move(player: Player): GameState {
        var coord: Int
        var x: Int
        var y: Int
        while (true) {
            print("Input coordinate: ")
            coord = readLine()?.toInt() ?: -1
            // convert to 0-based
            coord -= 1
            x = coord / state.N
            y = coord % state.N
            if (coord < 0 || coord >= state.N*state.N || !state.isEmpty(x, y))
                println("Invalid coordinate")
            else break
        }


        state.move(player, x, y)
        return state
    }

    override fun winner(): Player? {
        return state.winner()
    }

    override fun gameLoop() {
        while (winner() == null)  {
            state().printState()
            state = move(nextPlayer) as TicTacToeState
            nextPlayer = if (nextPlayer === playerA) playerB else playerA
        }
        state().printState()
        println("winner is: " + winner()?.name)
    }

}