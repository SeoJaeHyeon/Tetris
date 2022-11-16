package com.example.tetris.Block

import com.example.tetris.ViewModel.ViewModelArray


class BlockS(var row: Int, var col: Int): Block(row, col) {

    init {
        number = 4
        point2 = Point(row , col + 1)
        point3 = Point(row + 1, col )
        point4 = Point(row + 1, col - 1)
    }

    override fun blockDown(arr: ViewModelArray) {
        if(!(arr.touchFloor(this))) { // 블럭들이 바닥에 안닿았으면 이동가능
            if(!touchBottomBlock(arr)) { // 다른블럭이랑 밑 부분이 안부딪히면 이동 가능
                point1.down()
                point2.down()
                point3.down()
                point4.down()
            }
        }
    }

    override fun blockLeft(arr: ViewModelArray) {
        if(!(arr.touchLeft(this))) { // 블럭들이 왼쪽 벽에 닿지 않았으면 이동가능
            if(!touchLeftBlock(arr)) {// 다른블럭이랑 왼쪽 부분이 안부딪히면 이동 가능
                point1.left()
                point2.left()
                point3.left()
                point4.left()
            }
        }
    }
    override fun blockRight(arr: ViewModelArray) {
        if(!(arr.touchRight(this))) { // 블럭들이 오른쪽 벽에 닿지 않았으면 이동가능
            if(!touchRightBlock(arr)) { // 다른블럭이랑 오른쪽 부분이 안부딪히면 이동 가능
                point1.right()
                point2.right()
                point3.right()
                point4.right()
            }
        }
    }
    override fun rotation(arr: ViewModelArray) {
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
    override fun touchBottomBlock(arr: ViewModelArray): Boolean {
        var istouch = false
        if(!isRotation) {
            arr.arr.value?.let {
                istouch = ( it[point2.x + 1][point2.y] > 0 || it[point3.x + 1][point3.y] > 0 ||
                        it[point4.x + 1][point4.y] > 0 )
            }
            return istouch
        } else {
            arr.arr.value?.let {
                istouch = ( it[point2.x + 1][point2.y] > 0 || it[point3.x + 1][point3.y] > 0 )
            }
            return istouch
        }
    }
    override fun touchLeftBlock(arr: ViewModelArray): Boolean {
        var istouch: Boolean = false
        if(!isRotation) {
            arr.arr.value?.let {
                istouch = ( it[point1.x][point1.y - 1] > 0 || it[point4.x][point4.y - 1] > 0 )
            }
            return istouch
        } else {
            arr.arr.value?.let {
                istouch = ( it[point2.x][point2.y - 1] > 0 || it[point3.x][point3.y - 1] > 0 ||
                        it[point4.x][point4.y - 1] > 0 )
            }
            return istouch
        }
    }

    override fun touchRightBlock(arr: ViewModelArray): Boolean {
        var istouch: Boolean = false
        if(!isRotation) {
            arr.arr.value?.let {
                istouch = ( it[point2.x][point2.y + 1] > 0 || it[point3.x][point3.y + 1] > 0 )
            }
            return istouch
        } else {
            arr.arr.value?.let {
                istouch = ( it[point1.x][point1.y + 1] > 0 || it[point2.x][point2.y + 1] > 0 ||
                        it[point4.x][point4.y + 1] > 0 )
            }
            return istouch
        }
    }
}