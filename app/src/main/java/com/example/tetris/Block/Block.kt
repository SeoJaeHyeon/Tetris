package com.example.tetris.Block

//import com.example.tetris.CompareArray
import com.example.tetris.ViewModel.ViewModelArray

// 최상위 클래스인 Block을 상속하여 각각의 Block를 정의
open class Block(var x: Int, var y: Int) {
    var number = 0 // 각 블럭별로 상이한 숫자를 가져 서로 다른 블럭임을 나타냄

    // 한 모양에는 4 가지의 블럭이 존재함으로 최상위 클래스에서 블럭하나는 초기화
    // 나머지 블럭은 point1을 기준으로 서로다른 점(인덱스)를 가짐
    var point1 = Point(x, y)
    lateinit var point2: Point
    lateinit var point3: Point
    lateinit var point4: Point

    // 2개만 로테이션 하면 되는 것들의 회전 확인을 위한 불리언 변수
    var isRotation: Boolean = false

    // 4번 회전하는 블럭들의 회전 확인을 위한 불리언 변수
    var isRotation1: Boolean = false
    var isRotation2: Boolean = false
    var isRotation3: Boolean = false


    open fun rotation(arr: ViewModelArray) {

    }

    open fun blockDown(arr: ViewModelArray) {

    }

    open fun blockLeft(arr: ViewModelArray) {

    }

    open fun blockRight(arr: ViewModelArray) {

    }

    open fun touchBottomBlock(arr: ViewModelArray) = true
    open fun touchLeftBlock(arr: ViewModelArray) = true
    open fun touchRightBlock(arr: ViewModelArray) = true
}