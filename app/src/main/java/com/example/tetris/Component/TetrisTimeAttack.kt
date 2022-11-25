package com.example.tetris.Component

import java.util.*

class TetrisTimeAttack : Tetris() {
    // 타임어택 모드에서 사용
    var time = 3000 //처음 시간 60초
    var timerTask: Timer? = null
    var sec = 0
    var milli = 0

    override fun DeleteBlocks(row: Int) {
        arr.destroy(row)
        printAllGameFrame()

        // 한 줄 지워질 때마다 점수 20씩 증가
        score += PLUS_SCORE
        time += 1000
    }
}