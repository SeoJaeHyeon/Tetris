package com.example.tetris.Component


import android.app.Activity
import android.widget.ImageView
import com.example.tetris.Block.*
import com.example.tetris.R
import java.util.*

class Tetris() {
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
    val arr = CompareArray()
    private val random = Random() //seed를 랜덤하게 나오기위해 seed 랜덤 저장
    // 블럭들을 랜덤하게 나오기 위해 블럭의 번호를 난수로 저장
    var randomNum: Int = random.nextInt(7) + 1 //boundary라 + 1 해줌
    // 랜덤하게 얻은 블럭을 Block에 저장(게임화면에서 움직일 블럭)
    var block: Block = randomBlockChoice(randomNum, 1, COL / 2)
    var run = true
    var erase: Int = 1

    var high: Int = 0
    var score: Int = 0

    // 타임어택 모드에서 사용
    var time = 3000 //처음 시간 60초
    var timerTask: Timer? = null
    var sec = 0
    var milli = 0

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
        gameFrame[block.point1.x][block.point1.y]!!.setImageResource(blockColor(block.number))
        gameFrame[block.point2.x][block.point2.y]!!.setImageResource(blockColor(block.number))
        gameFrame[block.point3.x][block.point3.y]!!.setImageResource(blockColor(block.number))
        gameFrame[block.point4.x][block.point4.y]!!.setImageResource(blockColor(block.number))
    }
    // gameFrame에서 블럭을 다시 원래 배경으로 바꾸는 함수(이동할 때 사용)
    fun removeBlock() {
        arr.changeBlockNumberToZero(block)
        gameFrame[block.point1.x][block.point1.y]!!.setImageResource(R.drawable.gameframe)
        gameFrame[block.point2.x][block.point2.y]!!.setImageResource(R.drawable.gameframe)
        gameFrame[block.point3.x][block.point3.y]!!.setImageResource(R.drawable.gameframe)
        gameFrame[block.point4.x][block.point4.y]!!.setImageResource(R.drawable.gameframe)
    }

    // 다음에 올 블럭을 nextBlockFrame에 보여주는 함수
    fun printNextBlock() {
        resetFrame(nextBlockFrame,NEXTROW,NEXTCOL) //화면초기화 후
        val blockPreView = randomBlockChoice(randomNum, 1, 1) //새 블럭 객체
        nextBlockFrame[blockPreView.point1.x][blockPreView.point1.y]!!.setImageResource((blockColor(blockPreView.number)))
        nextBlockFrame[blockPreView.point2.x][blockPreView.point2.y]!!.setImageResource((blockColor(blockPreView.number)))
        nextBlockFrame[blockPreView.point3.x][blockPreView.point3.y]!!.setImageResource((blockColor(blockPreView.number)))
        nextBlockFrame[blockPreView.point4.x][blockPreView.point4.y]!!.setImageResource((blockColor(blockPreView.number)))
    }

    fun imgDown() {
        removeBlock() // 블럭을 원래 gridLayout의 배경으로 다시 변경
        block.blockDown(arr)
        printBlock() // 움직인 블럭을 다시 그림
        newBlockDown()
    }

    fun imgLeft() {
        removeBlock() // 블럭을 원래 gridLayout의 배경으로 다시 변경
        block.blockLeft(arr)
        printBlock() // 움직인 블럭을 다시 그림
    }

    fun imgRight() {
        removeBlock() // 블럭을 원래 gridLayout의 배경으로 다시 변경
        block.blockRight(arr)
        printBlock() // 움직인 블럭을 다시 그림
    }

    fun imgChange() {
        removeBlock() // 블럭을 원래 gridLayout의 배경으로 다시 변경
        block.rotation(arr)
        printBlock() // 움직인 블럭을 다시 그림
    }

    // Frame 초기화를 위해 만듦
    fun resetFrame(arr: Array<Array<ImageView?>>, row: Int, col: Int) {
        for(i in 0 until row)
            for(j in 0 until col)
                arr[i][j]!!.setImageResource(blockColor(R.drawable.gameframe))
    }

    fun isDelete(): Boolean {
        for(i in ROW-1 downTo 0) {
            for(j in 0 until COL) {

                if( arr.arr[i][j] == 0 ) break
                else{
                    if(j == COL - 1) {
                        return true
                    }
                }
            }
        }
        return false
    }
    fun DeleteBlocks(row: Int) {
        arr.destroy(row)
        printAllGameFrame()

        // 한 줄 지워질 때마다 점수 20씩 증가
        score += erase * 20
        if( high < score ) high = score
        time += 1000


    }
    fun DeleteBlocksChecking() {
        for(row in ROW-1 downTo 0) {
            for(col in 0 until COL) {

                if( arr.arr[row][col] == 0 ) break
                else{
                    if(col == COL - 1) {
                        DeleteBlocks(row)
                    }
                }
            }
        }
    }

    fun newBlockDown() {
        if(block.point1.x == 1 && block.touchBottomBlock(arr)) {  //블럭이 생성되는 곳과 블럭이 닿았을떄 게임오버
            run = false
        } else {
            if(block.point1.x == 2){ //새 블럭생성 후 다음블럭 보여줌
                randomNum = random.nextInt(7) + 1
                printNextBlock()
            }
            if(arr.touchFloor(block) || block.touchBottomBlock(arr)) { // 블럭이 바닥에 닿으면 새로운 블럭 생성
                while(isDelete()) {
                    DeleteBlocksChecking()
                }

                block = randomBlockChoice(randomNum, 1, COL / 2)
                printBlock()  //printBlock이랑 block객체 순서 중요
            }
        }
    }
    fun printAllGameFrame() {
        for(i in 0 until ROW) {
            for (j in 0 until  COL){
                printEachBlock(arr.arr[i][j], gameFrame, i, j)
            }
        }
    }
    fun printEachBlock(number: Int, arr: Array<Array<ImageView?>>, row: Int, col: Int) {
        when(number) {
            1 -> arr[row][col]!!.setImageResource(R.drawable.skyblueblockl)
            2 -> arr[row][col]!!.setImageResource(R.drawable.yellowblocko)
            3 -> arr[row][col]!!.setImageResource(R.drawable.redblockz)
            4 -> arr[row][col]!!.setImageResource(R.drawable.greenblocks)
            5 -> arr[row][col]!!.setImageResource(R.drawable.deepblueblockj)
            6 -> arr[row][col]!!.setImageResource(R.drawable.orangeblockl)
            7 -> arr[row][col]!!.setImageResource(R.drawable.purpleblockt)
            else -> arr[row][col]!!.setImageResource(R.drawable.gameframe)
        }
    }

    // 자동으로 내려가는 함수
    fun moveDownBlock() {
        removeBlock()
        block.blockDown(arr)
        printBlock()
    }




}