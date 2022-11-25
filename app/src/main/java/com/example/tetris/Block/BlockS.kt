package com.example.tetris.Block

import com.example.tetris.Component.CompareArray

class BlockS(var row: Int, var col: Int): Block(row, col) {

    override var number = 4
    override val point1 = Point( row , col )
    override val point2 = Point(row , col + 1)
    override val point3 = Point(row + 1, col )
    override val point4 = Point(row + 1, col - 1)

    // 2개만 로테이션 하면 되는 것들의 회전 확인을 위한 불리언 변수
    var isRotation = false


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
            point2.x++
            point2.y--

            point3.x--
            point3.y--

            point4.x -= 2 // y는 변하지 않음

            isRotation = true
        }else { // 2번 회전 -> 처음 모양
            // 1번 rotation이 이루어 지면 왼/오른쪽 벽면에 붙었을 때 로테이션에 문제가 발생

            if(arr.touchLeft(this)) {
                point2.x--
                point2.y++

                point3.x++
                point3.y++

                point4.x += 2

                isRotation = false
            } else if(arr.touchRight(this)) {
                point1.y--

                point2.x--

                point3.x++

                point4.x += 2
                point4.y--

                isRotation = false
            } else {
                point2.x--
                point2.y++

                point3.x++
                point3.y++

                point4.x += 2 // y는 변하지 않음

                isRotation = false

            }

        }
    }

    override fun touchBottomBlock(arr: CompareArray): Boolean {
        if(!isRotation) {

            return arr.arr[point2.x + 1][point2.y] > 0 || arr.arr[point3.x + 1][point3.y] > 0 ||
                    arr.arr[point4.x + 1][point4.y] > 0
        } else {

            return arr.arr[point2.x + 1][point2.y] > 0 || arr.arr[point3.x + 1][point3.y] > 0
        }
    }

    override fun touchLeftBlock(arr: CompareArray): Boolean {
        if(!isRotation) {

            return arr.arr[point1.x][point1.y - 1] > 0 || arr.arr[point4.x][point4.y - 1] > 0
        } else {

            return arr.arr[point2.x][point2.y - 1] > 0 || arr.arr[point3.x][point3.y - 1] > 0 ||
                    arr.arr[point4.x][point4.y - 1] > 0
        }
    }

    override fun touchRightBlock(arr: CompareArray): Boolean {
        if(!isRotation) {

            return arr.arr[point2.x][point2.y + 1] > 0 || arr.arr[point3.x][point3.y + 1] > 0
        } else {

            return arr.arr[point1.x][point1.y + 1] > 0 || arr.arr[point2.x][point2.y + 1] > 0 ||
                    arr.arr[point4.x][point4.y + 1] > 0
        }
    }
}