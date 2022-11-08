package com.example.tetris.Block

class BlockI(var row: Int, var col: Int): Block(row, col) {

    init {
        number = 0
        point2 = Point(row - 1, col )
        point3 = Point(row + 1, col )
        point4 = Point(row + 2, col )
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
        if(!isRotation) { // 한번도 로테이션 실행 x
            point2.x++
            point2.y++

            point3.x--
            point3.y--

            point4.x -= 2
            point4.y -= 2

            isRotation = true // 로테이션 한번 실행

        } else {
            point2.x--
            point2.y--

            point3.x++
            point3.y++

            point4.x += 2
            point4.y += 2

            isRotation = false // 로테이션 2번 실행 -> 원래 모양으로 돌아옴
        }
    }
}