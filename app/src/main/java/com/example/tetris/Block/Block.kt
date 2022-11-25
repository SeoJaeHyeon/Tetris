package com.example.tetris.Block


import com.example.tetris.Component.CompareArray

// 최상위 클래스인 Block을 상속하여 각각의 Block를 정의
abstract class Block(var x: Int, var y: Int) {
    abstract var number: Int // 각 블럭별로 상이한 숫자를 가져 서로 다른 블럭임을 나타냄

    abstract val point1: Point
    abstract val point2: Point
    abstract val point3: Point
    abstract val point4: Point

    abstract fun blockDown(arr: CompareArray)
    abstract fun blockLeft(arr: CompareArray)
    abstract fun blockRight(arr: CompareArray)
    abstract fun rotation(arr: CompareArray)

    abstract fun touchBottomBlock(arr: CompareArray): Boolean
    abstract fun touchLeftBlock(arr: CompareArray): Boolean
    abstract fun touchRightBlock(arr: CompareArray): Boolean
}