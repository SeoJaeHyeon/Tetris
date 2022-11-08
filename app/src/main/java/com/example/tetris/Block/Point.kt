package com.example.tetris.Block

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