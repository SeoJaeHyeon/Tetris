package com.example.tetris.component


import android.util.Log
import android.widget.ImageView
import com.example.tetris.block.*
import com.example.tetris.R
import java.util.*

open class Tetris() {
    val gameState = GameState()

    val arr = CompareArray()

    private val random = Random() //seed를 랜덤하게 나오기위해 seed 랜덤 저장
    // 블럭들을 랜덤하게 나오기 위해 블럭의 번호를 난수로 저장
    var randomNum: Int = random.nextInt(7) + 1 //boundary라 + 1 해줌
    // 랜덤하게 얻은 블럭을 Block에 저장(게임화면에서 움직일 블럭)
    var block: Block = randomBlockChoice(randomNum, 1, gameState.COL / 2)

    val PLUS_SCORE: Int = 20 // 한번 줄 지울 때 마다 올라가는 점수
    var high: Int = 0 // 최고 점수
    var score: Int = 0 // 현재 점수


    // 다음에 나올 블럭 랜덤하게 지정(블럭의 number를 통해 지정)
    fun randomBlockChoice(number: Int, row: Int, col: Int): Block {
        val randomBlock: Block
        when(number) {
            1 -> randomBlock = BlockI(row, col)
            2 -> randomBlock = BlockO(row, col)
            3 -> randomBlock = BlockZ(row, col)
            4 -> randomBlock = BlockS(row, col)
            5 -> randomBlock = BlockJ(row, col)
            6 -> randomBlock = BlockL(row, col)
            else -> randomBlock = BlockT(row, col)
        }
        return randomBlock
    }

    // 블럭의 색 결정하는 함수
    fun blockColor(number: Int): Int {
        val color: Int
        when(number) {
            // 블럭의 number에 따라 각각 그려질 블럭 색깔 지정
            1 -> color = R.drawable.skyblueblockl
            2 -> color = R.drawable.yellowblocko
            3 -> color = R.drawable.redblockz
            4 -> color = R.drawable.greenblocks
            5 -> color = R.drawable.deepblueblockj
            6 -> color = R.drawable.orangeblockl
            7 -> color = R.drawable.purpleblockt
            else -> color = R.drawable.gameframe
        }
        return color
    }

    // 블럭을 gameFrame에 보여주는 함수
    fun printBlock() {
        arr.changeZeroToBlockNumber(block)
        // 블럭이 지정하는 인덱스에 맞추어 블럭 출력 -> gameFrame의 배열 블럭은 항상 이미지가 있어 null이 될 수 없음
        if (gameState.gameFrame[block.point1.x][block.point1.y] != null){
            gameState.gameFrame[block.point1.x][block.point1.y]?.setImageResource(blockColor(block.number))
        }
        else throw NullPointerException("Expression 'gameState.gameFrame[block.point1.x][block.point1.y]' must not be null")
        if (gameState.gameFrame[block.point2.x][block.point2.y] != null) {
            gameState.gameFrame[block.point2.x][block.point2.y]?.setImageResource(blockColor(block.number))
        }
        else throw NullPointerException("Expression 'gameState.gameFrame[block.point2.x][block.point2.y]' must not be null")
        if (gameState.gameFrame[block.point3.x][block.point3.y] != null) {
            gameState.gameFrame[block.point3.x][block.point3.y]?.setImageResource(blockColor(block.number))
        }
        else throw NullPointerException("Expression 'gameState.gameFrame[block.point3.x][block.point3.y]' must not be null")
        if (gameState.gameFrame[block.point4.x][block.point4.y] != null) {
            gameState.gameFrame[block.point4.x][block.point4.y]?.setImageResource(blockColor(block.number))
        }
        else throw NullPointerException("Expression 'gameState.gameFrame[block.point4.x][block.point4.y]' must not be null")
    }
    // gameFrame에서 블럭을 다시 원래 배경으로 바꾸는 함수(이동할 때 사용)
    fun removeBlock() {
        arr.changeBlockNumberToZero(block)
        if (gameState.gameFrame[block.point1.x][block.point1.y] != null) {
            gameState.gameFrame[block.point1.x][block.point1.y]?.setImageResource(R.drawable.gameframe)
        }
        else throw NullPointerException("Expression 'gameState.gameFrame[block.point1.x][block.point1.y]' must not be null")
        if (gameState.gameFrame[block.point2.x][block.point2.y] != null) {
            gameState.gameFrame[block.point2.x][block.point2.y]?.setImageResource(R.drawable.gameframe)
        }
        else throw NullPointerException("Expression 'gameState.gameFrame[block.point2.x][block.point2.y]' must not be null")
        if (gameState.gameFrame[block.point3.x][block.point3.y] != null) {
            gameState.gameFrame[block.point3.x][block.point3.y]?.setImageResource(R.drawable.gameframe)
        }
        else throw NullPointerException("Expression 'gameState.gameFrame[block.point3.x][block.point3.y]' must not be null")
        if (gameState.gameFrame[block.point4.x][block.point4.y] != null)
        {
            gameState.gameFrame[block.point4.x][block.point4.y]?.setImageResource(R.drawable.gameframe)
        }
        else throw NullPointerException("Expression 'gameState.gameFrame[block.point4.x][block.point4.y]' must not be null")
    }

    // 다음에 올 블럭을 nextBlockFrame에 보여주는 함수
    fun printNextBlock() {
        resetFrame(gameState.nextBlockFrame,gameState.NEXTROW,gameState.NEXTCOL) //화면초기화 후
        val blockPreView = randomBlockChoice(randomNum, 1, 1) //새 블럭 객체
        if (gameState.nextBlockFrame[blockPreView.point1.x][blockPreView.point1.y] != null) {
            gameState.nextBlockFrame[blockPreView.point1.x][blockPreView.point1.y]?.setImageResource((blockColor(blockPreView.number)))
        }
        else throw NullPointerException("Expression 'gameState.nextBlockFrame[blockPreView.point1.x][blockPreView.point1.y]' must not be null")
        if (gameState.nextBlockFrame[blockPreView.point2.x][blockPreView.point2.y] != null) {
            gameState.nextBlockFrame[blockPreView.point2.x][blockPreView.point2.y]?.setImageResource((blockColor(blockPreView.number)))
        }
        else throw NullPointerException("Expression 'gameState.nextBlockFrame[blockPreView.point2.x][blockPreView.point2.y]' must not be null")
        if (gameState.nextBlockFrame[blockPreView.point3.x][blockPreView.point3.y] != null) {
            gameState.nextBlockFrame[blockPreView.point3.x][blockPreView.point3.y]?.setImageResource((blockColor(blockPreView.number)))
        }
        else throw NullPointerException("Expression 'gameState.nextBlockFrame[blockPreView.point3.x][blockPreView.point3.y]' must not be null")
        if (gameState.nextBlockFrame[blockPreView.point4.x][blockPreView.point4.y] != null) {
            gameState.nextBlockFrame[blockPreView.point4.x][blockPreView.point4.y]?.setImageResource((blockColor(blockPreView.number)))
        }
        else throw NullPointerException("Expression 'gameState.nextBlockFrame[blockPreView.point4.x][blockPreView.point4.y]' must not be null")
    }

    fun imgDown() {
        try {
            removeBlock() // 블럭을 원래 gridLayout의 배경으로 다시 변경
            block.blockDown(arr)
            printBlock() // 움직인 블럭을 다시 그림
        } catch(e: NullPointerException) {
            Log.e("Remove for Down","NullError")
        }

        newBlockDown() // 바닥 or 밑의 다른 블럭 닿으면 새로운 블럭 생성
    }

    fun imgLeft() {
        try {
            removeBlock() // 블럭을 원래 gridLayout의 배경으로 다시 변경
            block.blockLeft(arr)
            printBlock() // 움직인 블럭을 다시 그림
        } catch(e: NullPointerException) {
            Log.e("Remove for Left","NullError")
        }

    }

    fun imgRight() {
        try {
            removeBlock() // 블럭을 원래 gridLayout의 배경으로 다시 변경
            block.blockRight(arr)
            printBlock() // 움직인 블럭을 다시 그림
        } catch(e: NullPointerException) {
            Log.e("Remove for Right", "NullPointerException")
        }

    }

    fun imgChange() {
        try {
            removeBlock() // 블럭을 원래 gridLayout의 배경으로 다시 변경
            block.rotation(arr)
            printBlock() // 움직인 블럭을 다시 그림
        } catch(e: NullPointerException) {
            Log.e("Remove for Rotation", "NullPointerException")
        }

    }

    // Frame 초기화를 위해 만듦
    fun resetFrame(arr: Array<Array<ImageView?>>, row: Int, col: Int) {
        for(i in 0 until row)
            for(j in 0 until col)
                if (arr[i][j] != null) arr[i][j]?.setImageResource(blockColor(R.drawable.gameframe))
                else throw NullPointerException("Expression 'arr[i][j]' must not be null")
    }

    fun isDelete(): Boolean {
        for(i in gameState.ROW - 1 downTo 0) {
            for(j in 0 until gameState.COL) {

                if( arr.arr[i][j] == 0 ) break
                else{
                    if(j == gameState.COL - 1) {
                        return true
                    }
                }
            }
        }
        return false
    }

    open fun DeleteBlocks(row: Int) {
        arr.destroy(row)
        printAllGameFrame()

        // 한 줄 지워질 때마다 점수 20씩 증가
        score += PLUS_SCORE
        if( high < score ) high = score
    }

    fun DeleteBlocksChecking() {
        for(row in gameState.ROW - 1 downTo 0) {
            for(col in 0 until gameState.COL) {

                if( arr.arr[row][col] == 0 ) break
                else{
                    if(col == gameState.COL - 1) {
                        DeleteBlocks(row)
                    }
                }
            }
        }
    }

    fun newBlockDown() {
        if(block.point1.x == 1 && block.touchBottomBlock(arr)) {  //블럭이 생성되는 곳과 블럭이 닿았을떄 게임오버
            gameState.run = false
        } else {
            if(block.point1.x == 2){ //새 블럭생성 후 다음블럭 보여줌
                randomNum = random.nextInt(7) + 1
                printNextBlock()
            }
            if(arr.touchFloor(block) || block.touchBottomBlock(arr)) { // 블럭이 바닥에 닿으면 새로운 블럭 생성
                while(isDelete()) {
                    DeleteBlocksChecking()
                }

                block = randomBlockChoice(randomNum, 1, gameState.COL / 2)
                printBlock()  //printBlock이랑 block객체 순서 중요
            }
        }
    }

    fun printAllGameFrame() {
        for(i in 0 until gameState.ROW) {
            for (j in 0 until  gameState.COL){
                printEachBlock(arr.arr[i][j], gameState.gameFrame, i, j)
            }
        }
    }

    fun printEachBlock(number: Int, arr: Array<Array<ImageView?>>, row: Int, col: Int) {
        when(number) {
            1 -> if (arr[row][col] != null) arr[row][col]?.setImageResource(R.drawable.skyblueblockl)
                else throw NullPointerException("Expression 'arr[row][col]' must not be null")
            2 -> if (arr[row][col] != null) arr[row][col]?.setImageResource(R.drawable.yellowblocko)
                else throw NullPointerException("Expression 'arr[row][col]' must not be null")
            3 -> if (arr[row][col] != null) arr[row][col]?.setImageResource(R.drawable.redblockz)
                else throw NullPointerException("Expression 'arr[row][col]' must not be null")
            4 -> if (arr[row][col] != null) arr[row][col]?.setImageResource(R.drawable.greenblocks)
                else throw NullPointerException("Expression 'arr[row][col]' must not be null")
            5 -> if (arr[row][col] != null) arr[row][col]?.setImageResource(R.drawable.deepblueblockj)
                else throw NullPointerException("Expression 'arr[row][col]' must not be null")
            6 -> if (arr[row][col] != null) arr[row][col]?.setImageResource(R.drawable.orangeblockl)
                else throw NullPointerException("Expression 'arr[row][col]' must not be null")
            7 -> if (arr[row][col] != null) arr[row][col]?.setImageResource(R.drawable.purpleblockt)
                else throw NullPointerException("Expression 'arr[row][col]' must not be null")
            else -> if (arr[row][col] != null) arr[row][col]?.setImageResource(R.drawable.gameframe)
                else throw NullPointerException("Expression 'arr[row][col]' must not be null")
        }
    }

    // 자동으로 내려가는 함수
    fun moveDownBlock() {
        removeBlock()
        block.blockDown(arr)
        printBlock()
    }


}