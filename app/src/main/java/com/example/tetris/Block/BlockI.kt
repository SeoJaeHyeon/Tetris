package com.example.tetris.Block

class BlockI(var row: Int, var col: Int): Block(row, col) {

    init {
        number = 0
        point2 = Point(row + 1, col )
        point3 = Point(row + 2, col )
        point4 = Point(row + 3, col )
    }

    override fun blockDown() {
        point1.down()
        point2.down()
        point3.down()
        point4.down()
    }

    override fun blockLeft() {
        point1.left()
        point2.left()
        point3.left()
        point4.left()
    }

    override fun blockRight() {
        point1.right()
        point2.right()
        point3.right()
        point4.right()
    }

    override fun rotation() {

    }
}