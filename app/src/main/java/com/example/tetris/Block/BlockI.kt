package com.example.tetris.Block

//import com.example.tetris.CompareArray
import com.example.tetris.ViewModel.ViewModelArray

class BlockI(var row: Int, var col: Int): Block(row, col) {

    init {
        number = 1
        point2 = Point(row - 1, col )
        point3 = Point(row + 1, col )
        point4 = Point(row + 2, col )
    }

    override fun blockDown(arr: ViewModelArray) {
        if(!(arr.touchFloor(this))) { // 블럭들이 바닥에 안닿았으면 이동가능
            if (!touchBottomBlock(arr)) { // 다른블럭이랑 밑 부분이 안부딪히면 이동 가능
                point1.down()
                point2.down()
                point3.down()
                point4.down()
            }

        }
    }
    override fun blockLeft(arr: ViewModelArray) {
        if(!(arr.touchLeft(this))) { // 블럭들이 왼쪽 벽에 닿지 않았으면 이동가능
            if(!(touchLeftBlock(arr))) { // 다른블럭이랑 왼쪽 부분이 안부딪히면 이동 가능
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

        // 왼쪽 혹은 오른쪽 벽에 부딪혔는지 확인 먼저 -> rotation 실행

        if(!isRotation) { // 한번도 로테이션 실행 x
            if(arr.touchLeft(this)) {
                point1.y += 2 // point1.x 변화 x

                point2.x++
                point2.y += 3

                point3.x--
                point3.y++

                point4.x -= 2 // point4.x 변화 x

                isRotation = true
            } else if(arr.touchRight(this)){
                point1.y--

                point2.x++

                point3.x--
                point3.y -= 2

                point4.x -= 2
                point4.y -= 3

                isRotation = true
            } else {
                point2.x++
                point2.y++

                point3.x--
                point3.y--

                point4.x -= 2
                point4.y -= 2

                isRotation = true // 로테이션 한번 실행
            }
        } else {
            if(arr.touchLeft(this)) {
                point1.y -= 2

                point2.x--
                point2.y -= 3

                point3.x++
                point3.y--

                point4.x += 2

                isRotation = false
            } else if(arr.touchRight(this)) {
                point1.y++

                point2.x --

                point3.x++
                point3.y += 2

                point4.x += 2
                point4.y += 3

                isRotation = false
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

    override fun touchBottomBlock(arr: ViewModelArray): Boolean {
        var istouch = false
        if(!isRotation) {
             arr.arr.value?.let {
                istouch = ( it[point4.x + 1][point4.y] > 0 )
            }
            return istouch
        } else {
            arr.arr.value?.let {
                istouch = ( it[point1.x + 1][point1.y] > 0 || it[point2.x + 1][point2.y] > 0 ||
                        it[point3.x + 1][point3.y] > 0 || it[point4.x + 1][point4.y] > 0 )
            }
            return istouch
        }
    }
    override fun touchLeftBlock(arr: ViewModelArray): Boolean {
        var istouch: Boolean = false
        if(!isRotation) {
            arr.arr.value?.let {
                istouch = ( it[point1.x][point1.y - 1] > 0 || it[point2.x][point2.y - 1] > 0 ||
                        it[point3.x][point3.y - 1] > 0 || it[point4.x][point4.y - 1] > 0)
            }
            return istouch
        } else {
            arr.arr.value?.let {
                istouch = ( it[point4.x][point4.y - 1] > 0 )
            }
            return istouch
        }
    }

    override fun touchRightBlock(arr: ViewModelArray): Boolean {
        var istouch: Boolean = false
        if(!isRotation) {
            arr.arr.value?.let {
                istouch = ( it[point1.x][point1.y + 1] > 0 || it[point2.x][point2.y + 1] > 0 ||
                        it[point3.x][point3.y + 1] > 0 || it[point4.x][point4.y + 1] > 0)
            }
            return istouch
        } else {
            arr.arr.value?.let {
                istouch = ( it[point2.x][point2.y + 1] > 0 )
            }
            return istouch
        }
    }
}