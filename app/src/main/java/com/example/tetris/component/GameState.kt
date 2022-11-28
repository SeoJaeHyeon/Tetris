package com.example.tetris.component

import android.widget.ImageView

class GameState {
    val ROW = 20 // gameFrame의 row
    val COL = 10 // gameFrame의 col
    val NEXTROW = 4 // 다음 블럭을 보여줄 row
    val NEXTCOL = 3 // 다음 블럭을 보여줄 col

    val gameFrame = Array(ROW) { // gameFrame의 이차원 배열
        // 각 행들의 배열의 타입은 ImageView ! 나중에 초기화하기 위해 arrayOfNulls 배열 사용
        arrayOfNulls<ImageView?>(COL)
    }
    val nextBlockFrame = Array(NEXTROW) { // Next block Frame의 이차원 배열
        arrayOfNulls<ImageView?>(NEXTCOL)
    }

    var run = true
}