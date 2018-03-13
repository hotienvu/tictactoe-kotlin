fun main(args: Array<String>) {
    print("Enter board size:")
    val N = readLine()?.toInt()?: 3
    val g = TicTacToe.create(N)
    g.start()
}

