package com.example.tetris.Component

import android.widget.ImageView

class GameState {
    val ROW = 20 // gameFrame의 row
    val COL = 10 // gameFrame의 col
    val NEXTROW = 4
    val NEXTCOL = 3

    val gameFrame = Array(ROW) { // gameFrame의 이차원 배열
        // 각 행들의 배열의 타입은 ImageView ! 나중에 초기화하기 위해 arrayOfNulls 배열 사용
        arrayOfNulls<ImageView>(COL)
    }
    val nextBlockFrame = Array(NEXTROW) {
        arrayOfNulls<ImageView>(NEXTCOL)
    }

    var run = true
}