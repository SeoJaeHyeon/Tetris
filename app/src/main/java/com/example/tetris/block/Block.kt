package com.example.tetris.block

import com.example.tetris.component.CompareArray

// 최상위 클래스인 Block을 상속하여 각각의 Block를 정의
abstract class Block(var x: Int, var y: Int) {
    abstract var number: Int // 각 블럭별로 상이한 숫자를 가져 서로 다른 블럭임을 나타냄

    abstract val point1: Point // 각 블럭들의 기준 블럭
    abstract val point2: Point
    abstract val point3: Point
    abstract val point4: Point

    abstract fun blockDown(arr: CompareArray) // 블럭 아래로 이동
    abstract fun blockLeft(arr: CompareArray) // 블럭 왼쪽으로 이동
    abstract fun blockRight(arr: CompareArray) // 블럭 오른쪽으로 이동
    abstract fun rotation(arr: CompareArray) // 블록 회전

    abstract fun touchBottomBlock(arr: CompareArray): Boolean // 블럭이 밑의 다른 블록과 닿았는 지 확인
    abstract fun touchLeftBlock(arr: CompareArray): Boolean // 블럭이 왼쪽 다른 블록과 닿았는 지 확인
    abstract fun touchRightBlock(arr: CompareArray): Boolean // 블럭이 오른쪽 다른 블록과 닿았는 지 확인
}