package com.example.tetris.block

// 블럭들의 좌표를 나타내는 클래스
class Point(var x: Int, var y: Int) {

    fun down() {
        x++
    }

    fun left() {
        y--
    }

    fun right() {
        y++
    }
}