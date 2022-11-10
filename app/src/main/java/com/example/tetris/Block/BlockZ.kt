package com.example.tetris.Block

import com.example.tetris.CompareArray

class BlockZ(var row: Int, var col: Int): Block(row, col) {

    init {
        number = 2
        point2 = Point(row, col - 1 )
        point3 = Point(row + 1, col)
        point4 = Point(row + 1, col + 1)
    }
    override fun blockDown(arr: CompareArray) {
        if(!(arr.touchFloor(this))) { // 블럭들이 바닥에 안닿았으면 이동가능
            if(!touchBottomBlock(arr)) { // 다른블럭이랑 밑 부분이 안부딪히면 이동 가능
                point1.down()
                point2.down()
                point3.down()
                point4.down()
            }
        }
    }

    override fun blockLeft(arr: CompareArray) {
        if(!(arr.touchLeft(this))) { // 블럭들이 왼쪽 벽에 닿지 않았으면 이동가능
            if(!touchLeftBlock(arr)) {// 다른블럭이랑 왼쪽 부분이 안부딪히면 이동 가능
                point1.left()
                point2.left()
                point3.left()
                point4.left()
            }
        }
    }
    override fun blockRight(arr: CompareArray) {
        if(!(arr.touchRight(this))) { // 블럭들이 오른쪽 벽에 닿지 않았으면 이동가능
            if(!touchRightBlock(arr)) { // 다른블럭이랑 오른쪽 부분이 안부딪히면 이동 가능
                point1.right()
                point2.right()
                point3.right()
                point4.right()
            }
        }
    }
    override fun rotation(arr: CompareArray) {
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

    override fun touchBottomBlock(arr: CompareArray): Boolean {
        if(!isRotation) {
            return arr.arr[point2.x + 1][point2.y] == 1 || arr.arr[point3.x + 1][point3.y] == 1 ||
                    arr.arr[point4.x + 1][point4.y] == 1
        } else {
            return arr.arr[point1.x + 1][point1.y] == 1 || arr.arr[point4.x + 1][point4.y] == 1
        }
    }

    override fun touchLeftBlock(arr: CompareArray): Boolean {
        if(!isRotation) {
            return arr.arr[point2.x][point2.y - 1] == 1 || arr.arr[point3.x][point3.y - 1] == 1
        } else {
            return arr.arr[point1.x][point1.y - 1] == 1 || arr.arr[point2.x][point2.y - 1] == 1 ||
                    arr.arr[point4.x][point4.y - 1] == 1
        }
    }

    override fun touchRightBlock(arr: CompareArray): Boolean {
        if(!isRotation) {
            return arr.arr[point1.x][point1.y + 1] == 1 || arr.arr[point4.x][point4.y + 1] == 1
        } else {
            return arr.arr[point1.x][point1.y + 1] == 1 || arr.arr[point2.x][point2.y + 1] == 1 ||
                    arr.arr[point4.x][point4.y + 1] == 1
        }
    }

}