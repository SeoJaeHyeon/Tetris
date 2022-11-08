package com.example.tetris.Block

// 최상위 클래스인 Block을 상속하여 각각의 Block를 정의
open class Block(var x: Int, var y: Int) {
    var number = 0 // 각 블럭별로 상이한 숫자를 가져 서로 다른 블럭임을 나타냄

    // 한 모양에는 4 가지의 블럭이 존재함으로 최상위 클래스에서 블럭하나는 초기화
    // 나머지 블럭은 point1을 기준으로 서로다른 점(인덱스)를 가짐
    var point1 = Point(x, y)
    lateinit var point2: Point
    lateinit var point3: Point
    lateinit var point4: Point

    open fun blockDown() {

    }

    open fun blockLeft() {

    }

    open fun blockRight() {

    }

    open fun rotation() {

    }
}