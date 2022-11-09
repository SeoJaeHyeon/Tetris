package com.example.tetris.Block

class BlockJ(var row: Int, var col: Int): Block(row, col) {

    init {
        number = 4
        point2 = Point(row - 1 , col )
        point3 = Point(row + 1, col )
        point4 = Point(row + 1, col - 1)
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
        if(!isRotation1 && !isRotation2 && !isRotation3 ) { // 한번도 로테이션 실행한 적 없음
            point2.x++
            point2.y++

            point3.x--
            point3.y--

            point4.x -= 2
            // point4.y 변화 없음
            isRotation1 = true // 1번 로테이션 실행

        } else if(isRotation1 && !isRotation2 && !isRotation3 ) { // 로테이션 2번 실행
            point2.x++
            point2.y--

            point3.x--
            point3.y++

            // point4.x 변화 없음
            point4.y += 2

            isRotation2 = true // 로테이션 두번 실행

        } else if(isRotation1 && isRotation2 && !isRotation3 ) { // 로테이션 3번 실행
            point2.x--
            point2.y--

            point3.x++
            point3.y++

            point4.x += 2
            // point4.y 변화 없음

            isRotation3 = true // 로테이션 3번 실행

        } else { // 로테이션 4번 실행 (처음 모양으로)
            point2.x--
            point2.y++

            point3.x++
            point3.y--

            // point4.x
            point4.y -= 2

            isRotation1 = false
            isRotation2 = false
            isRotation3 = false // 로테이션 4번 실행으로 처음 모양으로 돌아옴

        }

    }
}