package com.example.tetris.Block

import com.example.tetris.CompareArray

class BlockO(var row: Int, var col: Int): Block(row, col) {

    init {
        number = 1
        point2 = Point(row , col + 1 )
        point3 = Point(row + 1, col )
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
    override fun blockDownTest(arr: CompareArray) {
        if(!(arr.touchFloor(this))) {// 블럭들이 바닥에 안닿았으면 이동가능
            point1.down()
            point2.down()
            point3.down()
            point4.down()
        }
    }
    override fun blockLeftTest(arr: CompareArray) {
        if(!(arr.touchLeft(this))) { // 블럭들이 왼쪽 벽에 닿지 않았으면 이동가능
            point1.left()
            point2.left()
            point3.left()
            point4.left()
        }
    }
    override fun blockRightTest(arr: CompareArray) {
        if(!(arr.touchRight(this))) { // 블럭들이 오른쪽 벽에 닿지 않았으면 이동가능
            point1.right()
            point2.right()
            point3.right()
            point4.right()
        }

    }
}