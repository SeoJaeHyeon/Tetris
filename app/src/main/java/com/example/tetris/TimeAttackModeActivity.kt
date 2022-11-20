package com.example.tetris

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.gridlayout.widget.GridLayout
import com.example.tetris.Block.*
import com.example.tetris.ViewModel.ViewModelArray
import com.example.tetris.databinding.ActivityTimeattackmodeBinding
import java.util.*
import kotlin.concurrent.thread

class TimeAttackModeActivity: AppCompatActivity() {
    lateinit var binding: ActivityTimeattackmodeBinding

    val ROW = 20 // gameFrame의 row
    val COL = 10 // gameFrame의 col
    val gameFrame = Array(ROW) { // gameFrame의 이차원 배열
        // 각 행들의 배열의 타입은 ImageView ! 나중에 초기화하기 위해 arrayOfNulls 배열 사용
        arrayOfNulls<ImageView>(COL)
    }
    val nextBlockFrame = Array(4) {
        arrayOfNulls<ImageView>(3)
    }
    private val random = Random() //seed를 랜덤하게 나오기위해 seed 랜덤 저장
    // 블럭들을 랜덤하게 나오기 위해 블럭의 번호를 난수로 저장
    var randomNum: Int = random.nextInt(7) + 1 //boundary라 + 1 해줌
    // 랜덤하게 얻은 블럭을 Block에 저장(게임화면에서 움직일 블럭)
    var block: Block = randomBlockChoice(randomNum, 1, COL / 2)
    var run = true
    var erase: Int = 0

    val viewModelFrameT: ViewModelArray by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTimeattackmodeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.boardt.rowCount = ROW // gridLayout의 row
        binding.boardt.columnCount = COL // gridLayout의 col

        binding.nextblockt.rowCount = 4
        binding.nextblockt.columnCount = 3


        // 게임 시작 후 gridLayout에 게임화면 생성
        gameFrameSetting(gameFrame, binding.boardt, ROW, COL)
        gameFrameSetting(nextBlockFrame, binding.nextblockt, 4, 3)

        gameRun()


        binding.imgLeftt.setOnClickListener {
            removeBlock() // 블럭을 원래 gridLayout의 배경으로 다시 변경
            block.blockLeft(viewModelFrameT)
            printBlock() // 움직인 블럭을 다시 그림
        }
        binding.imgRightt.setOnClickListener {
            removeBlock() // 블럭을 원래 gridLayout의 배경으로 다시 변경
            block.blockRight(viewModelFrameT)
            printBlock() // 움직인 블럭을 다시 그림
        }
        binding.imgDownt.setOnClickListener {
            removeBlock() // 블럭을 원래 gridLayout의 배경으로 다시 변경
            block.blockDown(viewModelFrameT)
            printBlock() // 움직인 블럭을 다시 그림
            newBlockDown()
        }
        binding.imgChanget.setOnClickListener {
            removeBlock() // 블럭을 원래 gridLayout의 배경으로 다시 변경
            block.rotation(viewModelFrameT)
            printBlock() // 움직인 블럭을 다시 그림
        }
        binding.imgStopt.setOnClickListener {
            startActivity(Intent(this, StopActivity::class.java)) // 그만하기 버튼 누르면 StopActivity로 이동
        }
    }

    // 게임 화면 설정 -> 게임화면에 사용될 이차원배열, 이차원배열을 그려줄 gridLayout, row, col을 매개변수로 받음
    fun gameFrameSetting(arr: Array<Array<ImageView?>>, grid: GridLayout, row: Int, col: Int) {
        // 해당 context(환경에 대한 전역정보) LayoutInflater를 가져옴(xml을 View객체로 변환)
        val inflater = LayoutInflater.from(this)
        for (i in 0 until row) {
            for (j in 0 until col) {
                // 게임화면에서 사용되는 이차원 배열의 각 원소에 gameframe_image를 넣음
                arr!![i][j] = inflater.inflate(R.layout.gameframe_image, grid, false) as ImageView
                // 위의 배열을 gridLayout에 맞추어 이미지 삽입
                grid.addView(arr[i][j])
            }
        }
    }

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
        viewModelFrameT.changeZeroToBlockNumber(block)
        // 블럭이 지정하는 인덱스에 맞추어 블럭 출력 -> gameFrame의 배열 블럭은 항상 이미지가 있어 null이 될 수 없음
        gameFrame[block.point1.x][block.point1.y]!!.setImageResource(blockColor(block.number))
        gameFrame[block.point2.x][block.point2.y]!!.setImageResource(blockColor(block.number))
        gameFrame[block.point3.x][block.point3.y]!!.setImageResource(blockColor(block.number))
        gameFrame[block.point4.x][block.point4.y]!!.setImageResource(blockColor(block.number))
    }

    // 다음에 올 블럭을 nextBlockFrame에 보여주는 함수
    fun printNextBlock() {
        resetFrame(nextBlockFrame,4,3) //화면초기화 후
        val blockPreView = randomBlockChoice(randomNum, 1, 1) //새 블럭 객체
        nextBlockFrame[blockPreView.point1.x][blockPreView.point1.y]!!.setImageResource((blockColor(blockPreView.number)))
        nextBlockFrame[blockPreView.point2.x][blockPreView.point2.y]!!.setImageResource((blockColor(blockPreView.number)))
        nextBlockFrame[blockPreView.point3.x][blockPreView.point3.y]!!.setImageResource((blockColor(blockPreView.number)))
        nextBlockFrame[blockPreView.point4.x][blockPreView.point4.y]!!.setImageResource((blockColor(blockPreView.number)))
    }

    // gameFrame에서 블럭을 다시 원래 배경으로 바꾸는 함수(이동할 때 사용)
    fun removeBlock() {
        viewModelFrameT.changeBlockNumberToZero(block)
        gameFrame[block.point1.x][block.point1.y]!!.setImageResource(R.drawable.gameframe)
        gameFrame[block.point2.x][block.point2.y]!!.setImageResource(R.drawable.gameframe)
        gameFrame[block.point3.x][block.point3.y]!!.setImageResource(R.drawable.gameframe)
        gameFrame[block.point4.x][block.point4.y]!!.setImageResource(R.drawable.gameframe)
    }

    // Frame 초기화를 위해 만듦
    fun resetFrame(arr: Array<Array<ImageView?>>, row: Int, col: Int) {
        for(i in 0 until row)
            for(j in 0 until col)
                arr[i][j]!!.setImageResource(blockColor(R.drawable.gameframe))
    }

    fun newBlockDown() {
        if(block.point1.x == 1 && block.touchBottomBlock(viewModelFrameT)) {  //블럭이 생성되는 곳과 블럭이 닿았을떄 게임오버
            run = false
        } else {
            if(block.point1.x == 2){ //새 블럭생성 후 다음블럭 보여줌
                randomNum = random.nextInt(7) + 1
                printNextBlock()
            }
            if(viewModelFrameT.touchFloor(block) || block.touchBottomBlock(viewModelFrameT)) { // 블럭이 바닥에 닿으면 새로운 블럭 생성
                while(isDelete()) {
                    DeleteBlocksChecking()
                }

                block = randomBlockChoice(randomNum, 1, COL / 2)
                printBlock()  //printBlock이랑 block객체 순서 중요
            }
        }
    }

    fun DeleteBlocks(row: Int) {
        viewModelFrameT.destroy(row)
        printAllGameFrame()

        erase += 1 // 한 줄 지워질 때마다 erase 1씩 증가
        viewModelFrameT.setscore(erase) // erase수에 따라 스코어 계산
        viewModelFrameT.setlevel(viewModelFrameT.score.value?:1) // level 갱신
        var h: Int = 0
        var s: Int = 0
        viewModelFrameT.high.value?.let {
            h = it
        }
        viewModelFrameT.score.value?.let {
            s = it
        }?:0
        if(h < s) viewModelFrameT.setHigh(s)

    }

    fun DeleteBlocksChecking() {
        for(row in ROW-1 downTo 0) {
            for(col in 0 until COL) {
                var isBlock = false
                viewModelFrameT.arr.value?. let{
                    isBlock = it[row][col] == 0
                }
                if( isBlock ) break
                else{
                    if(col == COL - 1) {
                        DeleteBlocks(row)
                    }
                }
            }
        }
    }

    fun isDelete(): Boolean {
        for(i in ROW-1 downTo 0) {
            for(j in 0 until COL) {
                var isBlock = true
                viewModelFrameT.arr.value?. let{
                    isBlock = it[i][j] == 0
                }
                if( isBlock ) break
                else{
                    if(j == COL - 1) {
                        return true
                    }
                }
            }
        }
        return false
    }

    fun printAllGameFrame() {
        for(i in 0 until ROW) {
            for (j in 0 until  COL){
                var blockNum = 0
                viewModelFrameT.arr.value?. let{
                    blockNum = it[i][j]
                }
                printEachBlock(blockNum, gameFrame, i, j)
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


    // 나중에 게임 종료 되면 여기서 현재 자기 점수 값 게임오버 액티비티에 넘겨야 해서 함수로 만듦
    fun changeGameOverActivity() {

        val intent = Intent(this, GameOverActivity::class.java)
        intent.putExtra("score", viewModelFrameT.score.value.toString())

        startActivity(intent)
    }


    // 자동으로 내려가는 함수
    fun moveDownBlock() {
        removeBlock()
        block.blockDown(viewModelFrameT)
        printBlock()
    }

    fun gameRun() {
        resetFrame(nextBlockFrame,4,3)
        printNextBlock()
        thread(start = true) {
            while(run) {
                var millis = 1000L - (viewModelFrameT.level.value?.times(25) ?:0)
                Thread.sleep(millis)
                runOnUiThread {
                    moveDownBlock()
                    newBlockDown()
                }
            }

            changeGameOverActivity()
        }
    }

    override fun onPause() {
        super.onPause()

        saveState()
    }

    fun saveState() {
        val prefT = getSharedPreferences("prefT", Activity.MODE_PRIVATE)
        val editor = prefT.edit()
        editor.putInt("highT", (viewModelFrameT.high.value!!))
        editor.commit()
    }

    override fun onResume() {
        super.onResume()

        restoreState()
    }

    fun restoreState() {
        val prefT = getSharedPreferences("prefT", Activity.MODE_PRIVATE)
        if( prefT != null && prefT.contains("highT") ) {
            viewModelFrameT.setHigh(prefT.getInt("highT", 0))
        }
    }


}