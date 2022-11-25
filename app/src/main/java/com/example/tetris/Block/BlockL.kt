package com.example.tetris.Block

import com.example.tetris.Component.CompareArray

//import com.example.tetris.CompareArray

class BlockL(var row: Int, var col: Int): Block(row, col) {

    override var number = 6
    override val point1 = Point( row, col )
    override val point2 = Point(row - 1, col)
    override val point3 = Point(row + 1, col)
    override val point4 = Point(row + 1, col + 1)

    // 4번 회전하는 블럭들의 회전 확인을 위한 불리언 변수
    var isRotation1: Boolean = false
    var isRotation2: Boolean = false
    var isRotation3: Boolean = false

    override fun blockDown(arr: CompareArray) {
        if(!(arr.touchFloor(this))) { // 블럭들이 바닥에 안닿았으면 이동가능
            if(!touchBottomBlock(arr)) {// 다른블럭이랑 밑 부분이 안부딪히면 이동 가능
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
            if(!touchRightBlock(arr)) {// 다른블럭이랑 오른쪽 부분이 안부딪히면 이동 가능
                point1.right()
                point2.right()
                point3.right()
                point4.right()
            }
        }
    }
    override fun rotation(arr: CompareArray) {
        if (!isRotation1 && !isRotation2 && !isRotation3) { // 1번째 회전
            if(arr.touchLeft(this)) {
                point1.y++

                point2.x++
                point2.y += 2

                point3.x--

                point4.y--

                isRotation1 = true
            } else {
                point2.x++
                point2.y++

                point3.x--
                point3.y--

                point4.y -= 2 // x변화 없음

                isRotation1 = true

            }

        } else if (isRotation1 && !isRotation2 && !isRotation3) { // 2번째 회전
            point2.x++
            point2.y--

            point3.x--
            point3.y++

            point4.x -= 2 // y변화 없음

            isRotation2 = true

        } else if (isRotation1 && isRotation2 && !isRotation3) { // 3번째 회전
            if(arr.touchRight(this)) {
                point1.y--

                point2.x--
                point2.y -= 2

                point3.x++

                point4.y++

                isRotation3 = true
            } else {
                point2.x--
                point2.y--

                point3.x++
                point3.y++

                point4.y += 2 // x변화없음

                isRotation3 = true

            }

        } else  { // 4번째 회전 -> 처음 모양
            point2.x--
            point2.y++

            point3.x++
            point3.y--

            point4.x += 2 // y변화 없음

            isRotation1 = false
            isRotation2 = false
            isRotation3 = false
        }
    }

    override fun touchBottomBlock(arr: CompareArray): Boolean {
        if(!isRotation1 && !isRotation2 && !isRotation3) {

            return arr.arr[point3.x + 1][point3.y] > 0 || arr.arr[point4.x + 1][point4.y] > 0
        } else if(isRotation1 && !isRotation2 && !isRotation3) {

            return arr.arr[point1.x + 1][point1.y] > 0 || arr.arr[point2.x + 1][point2.y] > 0 ||
                    arr.arr[point4.x + 1][point4.y] > 0
        } else if(isRotation1 && isRotation2 && !isRotation3) {

            return arr.arr[point2.x + 1][point2.y] > 0 || arr.arr[point4.x + 1][point4.y] > 0
        } else {

            return arr.arr[point1.x + 1][point1.y] > 0 || arr.arr[point2.x + 1][point2.y] > 0 ||
                    arr.arr[point3.x + 1][point3.y] > 0
        }
    }

    override fun touchLeftBlock(arr: CompareArray): Boolean {

        if(!isRotation1 && !isRotation2 && !isRotation3) {

            return arr.arr[point1.x][point1.y - 1] > 0 || arr.arr[point2.x][point2.y - 1] > 0 ||
                    arr.arr[point3.x][point3.y - 1] > 0
        } else if(isRotation1 && !isRotation2 && !isRotation3) {

            return arr.arr[point3.x][point3.y - 1] > 0 || arr.arr[point4.x][point4.y - 1] > 0
        } else if(isRotation1 && isRotation2 && !isRotation3) {

            return arr.arr[point1.x][point1.y - 1] > 0 || arr.arr[point2.x][point2.y - 1] > 0 ||
                    arr.arr[point4.x][point4.y - 1] > 0
        } else {

            return arr.arr[point2.x][point2.y - 1] > 0 || arr.arr[point4.x][point4.y - 1] > 0
        }

    }

    override fun touchRightBlock(arr: CompareArray): Boolean {
        if(!isRotation1 && !isRotation2 && !isRotation3) {
            return arr.arr[point1.x][point1.y + 1] > 0  || arr.arr[point2.x][point2.y + 1] > 0 ||
                    arr.arr[point4.x][point4.y + 1] > 0
        } else if(isRotation1 && !isRotation2 && !isRotation3) {
            return arr.arr[point2.x][point2.y + 1] > 0 || arr.arr[point4.x][point4.y + 1] > 0
        } else if(isRotation1 && isRotation2 && !isRotation3) {
            return arr.arr[point1.x][point1.y + 1] > 0 || arr.arr[point2.x][point2.y + 1] > 0 ||
                    arr.arr[point3.x][point3.y + 1] > 0
        } else {
            return  arr.arr[point3.x][point3.y + 1] > 0 || arr.arr[point4.x][point4.y + 1] > 0
        }
    }

}