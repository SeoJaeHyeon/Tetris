package com.example.tetris.Block

import com.example.tetris.CompareArray

class BlockT(var row: Int, var col: Int): Block(row, col) {

    init {
        number = 6
        point2 = Point(row, col - 1 )
        point3 = Point(row, col + 1 )
        point4 = Point(row + 1, col )
    }
    override fun blockDown(arr: CompareArray) {
        if(!(arr.touchFloor(this))) {// 블럭들이 바닥에 안닿았으면 이동가능
            point1.down()
            point2.down()
            point3.down()
            point4.down()
        }
    }
    override fun blockLeft(arr: CompareArray) {
        if(!(arr.touchLeft(this))) { // 블럭들이 왼쪽 벽에 닿지 않았으면 이동가능
            point1.left()
            point2.left()
            point3.left()
            point4.left()
        }
    }
    override fun blockRight(arr: CompareArray) {
        if(!(arr.touchRight(this))) { // 블럭들이 오른쪽 벽에 닿지 않았으면 이동가능
            point1.right()
            point2.right()
            point3.right()
            point4.right()
        }
    }
    override fun rotation() {

    }

}