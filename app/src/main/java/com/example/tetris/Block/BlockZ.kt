package com.example.tetris.Block

class BlockZ(var row: Int, var col: Int): Block(row, col) {

    init {
        number = 2
        point2 = Point(row, col - 1 )
        point3 = Point(row + 1, col)
        point4 = Point(row + 1, col + 1)
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
        if(!isRotation) { // 1번째 회전
            point2.x--
            point2.y++

            point3.x--
            point3.y--

            point4.y -= 2 // x는 변하지 않음

            isRotation = true
        }else { // 2번 회전 -> 처음 모양
            point2.x++
            point2.y--

            point3.x++
            point3.y++

            point4.y += 2

            isRotation = false
        }
    }
}